package com.momsitter.api.ui;

import com.momsitter.service.AccountService;
import com.momsitter.service.dto.AccountInfoResponse;
import com.momsitter.service.dto.parent.ChildResponse;
import com.momsitter.service.dto.sitter.SitterAccountUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/sitters")
    public ResponseEntity<List<AccountInfoResponse>> findAllSitterAccounts() {
        List<AccountInfoResponse> accounts = accountService.findAllSitterAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/parents")
    public ResponseEntity<List<AccountInfoResponse>> findAllParentAccounts() {
        List<AccountInfoResponse> accounts = accountService.findAllParentAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/parents/children")
    public ResponseEntity<List<ChildResponse>> findAllChildrenByParent(@RequestParam Long parentId) {
        List<ChildResponse> children = accountService.findAllChildrenByParentId(parentId);
        return ResponseEntity.ok(children);
    }

    @PutMapping("/sitters/{id}")
    public ResponseEntity<AccountInfoResponse> updateSitterInfo(@PathVariable Long id,
                                                                @RequestBody SitterAccountUpdateRequest request) {
        AccountInfoResponse account = accountService.updateSitterAccountInfo(id ,request);
        return ResponseEntity.ok(account);
    }
}
