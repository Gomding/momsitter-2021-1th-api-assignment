package com.momsitter.ui;

import com.momsitter.service.AccountService;
import com.momsitter.ui.dto.account.ParentCreateRequest;
import com.momsitter.ui.dto.account.ParentCreateResponse;
import com.momsitter.ui.dto.account.SitterCreateRequest;
import com.momsitter.ui.dto.account.SitterCreateResponse;
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

    @PostMapping("/parent")
    public ResponseEntity<Void> createParentAccount(@RequestBody ParentCreateRequest request) {
        ParentCreateResponse response = accountService.createParentAccount(request);
        return ResponseEntity
                .created(URI.create("/accounts/" + response.getAccount().getId()))
                .build();
    }
}
