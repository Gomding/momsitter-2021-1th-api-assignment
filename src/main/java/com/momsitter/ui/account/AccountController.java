package com.momsitter.ui.account;

import com.momsitter.service.AccountService;
import com.momsitter.ui.account.dto.SitterCreateRequest;
import com.momsitter.ui.account.dto.SitterCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
