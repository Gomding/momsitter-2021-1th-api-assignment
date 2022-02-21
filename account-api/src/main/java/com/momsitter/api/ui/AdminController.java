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

    @GetMapping("/sitters")
    public ResponseEntity<List<AccountInfoResponse>> findAllSitterAccounts() {
        List<AccountInfoResponse> accounts = accountService.findAllSitterAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/parents")
    public ResponseEntity<List<AccountInfoResponse>> findAllParentAccounts() {
        List<AccountInfoResponse> accounts = accountService.findAllSitterAccounts();
        return ResponseEntity.ok(accounts);
    }
}
