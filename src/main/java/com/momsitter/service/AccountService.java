package com.momsitter.service;

import com.momsitter.domain.Account;
import com.momsitter.domain.AccountId;
import com.momsitter.domain.Email;
import com.momsitter.repository.AccountRepository;
import com.momsitter.repository.ParentInfoRepository;
import com.momsitter.repository.SitterInfoRepository;
import com.momsitter.ui.account.dto.SitterCreateRequest;
import com.momsitter.ui.account.dto.SitterCreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final SitterInfoRepository sitterInfoRepository;
    private final ParentInfoRepository parentInfoRepository;

    public AccountService(AccountRepository accountRepository, SitterInfoRepository sitterInfoRepository,
                          ParentInfoRepository parentInfoRepository) {
        this.accountRepository = accountRepository;
        this.sitterInfoRepository = sitterInfoRepository;
        this.parentInfoRepository = parentInfoRepository;
    }

    public SitterCreateResponse createSitterAccount(SitterCreateRequest request) {
        Account account = request.toEntity();
        validateAccountId(account.getAccountId());
        validateAccountEmail(account.getEmail());
        account.registerSitter(request.getSitterInfo().toEntity());
        return SitterCreateResponse.of(accountRepository.save(account));
    }

    private void validateAccountId(AccountId accountId) {
        if (accountRepository.existsByAccountId(accountId))
            throw new IllegalArgumentException("이미 존재하는 계정 ID 입니다. 다른 계정 ID를 입력해주세요.");
    }

    private void validateAccountEmail(Email email) {
        if (accountRepository.existsByEmail(email))
            throw new IllegalArgumentException("입력하신 이메일로 가입한 계정이 이미 존재합니다.");
    }
}
