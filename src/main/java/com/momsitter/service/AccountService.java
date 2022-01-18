package com.momsitter.service;

import com.momsitter.domain.*;
import com.momsitter.exception.DuplicateException;
import com.momsitter.repository.AccountRepository;
import com.momsitter.ui.account.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Account account = request.getAccount().toEntity();

        validateAccountId(account.getAccountId());
        validateAccountEmail(account.getEmail());

        ParentInfo parentInfo = extractParentInfoFrom(request);
        account.registerParent(parentInfo);
        return ParentCreateResponse.of(accountRepository.save(account));
    }

    private ParentInfo extractParentInfoFrom(ParentCreateRequest request) {
        ParentInfo parentInfo = request.getParentInfo().toEntity();
        List<Child> children = request.getParentInfo().getChildren().stream()
                .map(ChildRequest::toEntity)
                .collect(Collectors.toList());
        for (Child child : children) {
            child.addParentInfo(parentInfo);
        }
        return parentInfo;
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
