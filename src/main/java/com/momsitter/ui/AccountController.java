package com.momsitter.ui;

import com.momsitter.domain.Account;
import com.momsitter.service.AccountService;
import com.momsitter.ui.dto.account.*;
import com.momsitter.ui.webconfig.AccountAuthenticationPrinciple;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/sitter")
    public ResponseEntity<Void> createSitterAccount(@RequestBody SitterCreateRequest request) {
        SitterCreateResponse response = accountService.createSitterAccount(request);
        return ResponseEntity
                .created(URI.create("/accounts/" + response.getAccount().getId()))
                .build();
    }

    @PostMapping("/parent")
    public ResponseEntity<Void> createParentAccount(@RequestBody ParentCreateRequest request) {
        ParentCreateResponse response = accountService.createParentAccount(request);
        return ResponseEntity
                .created(URI.create("/accounts/" + response.getAccount().getId()))
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<AccountInfoResponse> findAccountInfoOfMine(@AccountAuthenticationPrinciple Account account) {
        AccountInfoResponse response = accountService.findAccountById(account.getId());
        return ResponseEntity.ok(response);
    }
}
