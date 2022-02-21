package com.momsitter.api.ui;

import com.momsitter.service.AccountService;
import com.momsitter.service.dto.AccountInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AccountService accountService;

    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountInfoResponse>> findAllAccounts() {
        List<AccountInfoResponse> accounts = accountService.findAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}
