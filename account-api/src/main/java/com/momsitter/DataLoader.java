package com.momsitter;

import com.momsitter.domain.*;
import com.momsitter.domain.repository.AccountRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("!test")
@Transactional
@Component
public class DataLoader implements ApplicationRunner {

    private final AccountRepository accountRepository;

    public DataLoader(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account.Builder()
                .name(new Name("찰리"))
                .dateOfBirth(DateOfBirth.of("19920530"))
                .gender(Gender.MALE)
                .accountId(new AccountId("charlie1992"))
                .password(new Password("abcd1234!@"))
                .email(new Email("test@test.com"))
                .build();
        SitterInfo sitterInfo = new SitterInfo(new CareAgeRange(3, 6), "돌봄왕");
        account.registerSitter(sitterInfo);
        accountRepository.save(account);

        Account account2 = new Account.Builder()
                .name(new Name("찰리브라운"))
                .dateOfBirth(DateOfBirth.of("19920501"))
                .gender(Gender.FEMALE)
                .accountId(new AccountId("brown1234"))
                .password(new Password("basd1234!"))
                .email(new Email("brown@test.com"))
                .build();

        Child child1 = new Child(DateOfBirth.of("20190212"), Gender.FEMALE);
        Child child2 = new Child(DateOfBirth.of("20201003"), Gender.MALE);
        ParentInfo parentInfo = new ParentInfo("아이 둘과 함께 놀아주실 분 구합니다!");
        child1.addParentInfo(parentInfo);
        child2.addParentInfo(parentInfo);
        account2.registerParent(parentInfo);
        accountRepository.save(account2);
    }
}
