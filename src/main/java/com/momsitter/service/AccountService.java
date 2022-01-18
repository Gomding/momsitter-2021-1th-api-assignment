package com.momsitter.service;

import com.momsitter.domain.*;
import com.momsitter.exception.DuplicateException;
import com.momsitter.exception.InvalidArgumentException;
import com.momsitter.exception.InvalidStateException;
import com.momsitter.repository.AccountRepository;
import com.momsitter.ui.dto.account.*;
import com.momsitter.ui.dto.account.parent.*;
import com.momsitter.ui.dto.account.sitter.SitterCreateRequest;
import com.momsitter.ui.dto.account.sitter.SitterCreateResponse;
import com.momsitter.ui.dto.account.sitter.SitterInfoResponse;
import com.momsitter.ui.dto.account.sitter.SitterUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public SitterCreateResponse createSitterAccount(SitterCreateRequest request) {
        Account account = request.getAccount().toEntity();

        validateAccountId(account.getAccountId());
        validateAccountEmail(account.getEmail());

        account.registerSitter(request.getSitterInfo().toEntity());
        return SitterCreateResponse.of(accountRepository.save(account));
    }

    @Transactional
    public ParentCreateResponse createParentAccount(ParentCreateRequest request) {
        Account account = request.toAccount();

        validateAccountId(account.getAccountId());
        validateAccountEmail(account.getEmail());

        ParentInfo parentInfo = request.toParentInfo();
        account.registerParent(parentInfo);
        return ParentCreateResponse.of(accountRepository.save(account));
    }

    public AccountInfoResponse findAccountById(Long id) {
        return AccountInfoResponse.of(findById(id));
    }

    @Transactional
    public AccountResponse updateAccountInfo(Long id, AccountUpdateRequest request) {
        Account account = findById(id);
        if (account.isDifferentEmail(request.getEmail())) {
            validateAccountEmail(new Email(request.getEmail()));
        }
        account.updateInfo(Gender.fromGenderName(request.getGender()),
                new Password(request.getPassword()),
                new Email(request.getEmail())
        );
        return AccountResponse.of(account);
    }

    public SitterInfoResponse updateSitterInfo(Long id, SitterUpdateRequest request) {
        Account account = findById(id);
        validateSitterAccount(account);
        account.updateSitterInfo(request.getMinCareAge(), request.getMaxCareAge(), request.getAboutMe());
        return SitterInfoResponse.of(account.getSitterInfo());
    }

    private void validateSitterAccount(Account account) {
        if (!account.isSitter())
            throw new InvalidStateException("시터회원으로 등록하지 않으면 시터회원 정보를 수정할 수 없습니다.");
    }

    public ParentInfoResponse updateParentInfo(Long id, ParentUpdateRequest request) {
        Account account = findById(id);
        validateParentAccount(account);

        Set<Child> updateChildren = request.getChildren().stream()
                .map(child -> new Child(child.getId(), new DateOfBirth(child.getDateOfBirth()), Gender.fromGenderName(child.getGender())))
                .collect(Collectors.toSet());

        validateChildren(account.getParentInfo().getChildren(), updateChildren);

        account.updateParentInfo(new ArrayList<>(updateChildren), request.getCareRequestInfo());
        return ParentInfoResponse.of(account.getParentInfo());
    }

    private void validateChildren(List<Child> children, Set<Child> updateChildren) {
        if (children.size() != updateChildren.size() || isDifferentChildren(children, updateChildren))
            throw new InvalidArgumentException("등록된 아이들과 수정을 원하는 아이들이 일치하지 않습니다.");
    }

    private boolean isDifferentChildren(List<Child> children, Set<Child> childSet) {
        List<Long> childIds = children.stream()
                .map(Child::getId)
                .collect(Collectors.toList());
        for (Child child : childSet) {
            if (!childIds.contains(child.getId()))
                return true;
        }
        return false;
    }

    private void validateParentAccount(Account account) {
        if (!account.isParent()) {
            throw new InvalidStateException("부모회원으로 등록하지 않으면 부모회원 정보를 수정할 수 없습니다.");
        }
    }

    private Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new InvalidArgumentException("존재하지 않는 회원입니다."));
    }

    private void validateAccountId(AccountId accountId) {
        if (accountRepository.existsByAccountId(accountId))
            throw new DuplicateException("이미 존재하는 계정 ID 입니다. 다른 계정 ID를 입력해주세요.");
    }

    private void validateAccountEmail(Email email) {
        if (accountRepository.existsByEmail(email))
            throw new DuplicateException("입력하신 이메일로 가입한 계정이 이미 존재합니다.");
    }
}
