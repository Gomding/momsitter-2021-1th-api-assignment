package com.momsitter.api.ui;

import com.momsitter.api.webconfig.AccountAuthenticationPrinciple;
import com.momsitter.domain.Account;
import com.momsitter.service.AccountService;
import com.momsitter.service.dto.AccountInfoResponse;
import com.momsitter.service.dto.AccountResponse;
import com.momsitter.service.dto.AccountUpdateRequest;
import com.momsitter.service.dto.parent.*;
import com.momsitter.service.dto.sitter.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @PutMapping("/me")
    public ResponseEntity<AccountResponse> updateAccountInfoOfMine(@RequestBody AccountUpdateRequest request,
                                                                   @AccountAuthenticationPrinciple Account account) {
        AccountResponse response = accountService.updateAccountInfo(account.getId(), request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/sitter")
    public ResponseEntity<SitterInfoResponse> updateSitterInfoOfMine(@RequestBody SitterUpdateRequest request,
                                                                     @AccountAuthenticationPrinciple Account account) {
        SitterInfoResponse response = accountService.updateSitterInfo(account.getId(), request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/parent")
    public ResponseEntity<ParentInfoResponse> updateParentInfoOfMine(@RequestBody ParentUpdateRequest request,
                                                                     @AccountAuthenticationPrinciple Account account) {
        ParentInfoResponse response = accountService.updateParentInfo(account.getId(), request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/me/sitter")
    public ResponseEntity<SitterInfoResponse> addActivitySitterInfo(@RequestBody SitterInfoRequest request,
                                                                    @AccountAuthenticationPrinciple Account account) {
        SitterInfoResponse response = accountService.addActivitySitter(account.getId(), request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/me/parent")
    public ResponseEntity<ParentInfoResponse> addActivityParentInfo(@RequestBody ParentInfoRequest request,
                                                                    @AccountAuthenticationPrinciple Account account) {
        ParentInfoResponse response = accountService.addActivityParent(account.getId(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountInfoResponse>> findAllAccounts(@AccountAuthenticationPrinciple Account account) {
        List<AccountInfoResponse> accounts = accountService.findAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}
