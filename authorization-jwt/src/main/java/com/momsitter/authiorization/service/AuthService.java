package com.momsitter.authiorization.service;

import com.momsitter.authiorization.exception.InvalidAuthTokenException;
import com.momsitter.authiorization.exception.LoginFailException;
import com.momsitter.authiorization.infrastructure.JwtTokenProvider;
import com.momsitter.authiorization.ui.dto.auth.TokenRequest;
import com.momsitter.authiorization.ui.dto.auth.TokenResponse;
import com.momsitter.domain.Account;
import com.momsitter.domain.AccountId;
import com.momsitter.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
        if (account.isInvalidPassword(password))
            throw new LoginFailException("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
    }

    public Account findAccountByToken(String token) {
        validateTokenIsNull(token);
        validateValidToken(token);

        String payload = jwtTokenProvider.getPayload(token);
        return accountRepository.findByAccountId(new AccountId(payload))
                .orElseThrow(() -> new InvalidAuthTokenException("존재하지 않는 회원입니다."));
    }

    private void validateTokenIsNull(String token) {
        if (Objects.isNull(token))
            throw new InvalidAuthTokenException("로그인이 필요한 기능입니다. 로그인 후 다시 시도해주세요.");
    }

    private void validateValidToken(String token) {
        if (!jwtTokenProvider.validateToken(token))
            throw new InvalidAuthTokenException("로그인이 만료되거나 유효하지 않습니다. 로그인 후 다시 시도해주세요.");
    }
}
