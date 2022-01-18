package com.momsitter.service;

import com.momsitter.domain.Account;
import com.momsitter.domain.AccountId;
import com.momsitter.exception.LoginFailException;
import com.momsitter.infrastructure.JwtTokenProvider;
import com.momsitter.repository.AccountRepository;
import com.momsitter.ui.auth.dto.TokenRequest;
import com.momsitter.ui.auth.dto.TokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;

    public AuthService(JwtTokenProvider jwtTokenProvider, AccountRepository accountRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountRepository = accountRepository;
    }

    public TokenResponse login(TokenRequest request) {
        Account findAccount = accountRepository.findByAccountId(new AccountId(request.getAccountId()))
                .orElseThrow(() -> new LoginFailException("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요."));

        validateMatchPassword(findAccount, request.getPassword());

        return new TokenResponse(jwtTokenProvider.createToken(findAccount.getAccountId().getValue()));
    }

    private void validateMatchPassword(Account account, String password) {
        if (account.isInvalidPassword(password)) {
            throw new LoginFailException("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
        }
    }
}
