package com.momsitter;

import com.momsitter.domain.*;
import com.momsitter.repository.AccountRepository;
import com.momsitter.repository.ChildRepository;
import com.momsitter.repository.ParentInfoRepository;
import com.momsitter.repository.SitterInfoRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class DataLoader implements ApplicationRunner {

    private final AccountRepository accountRepository;
    private final SitterInfoRepository sitterInfoRepository;
    private final ParentInfoRepository parentInfoRepository;
    private final ChildRepository childRepository;

    public DataLoader(AccountRepository accountRepository, SitterInfoRepository sitterInfoRepository,
                      ParentInfoRepository parentInfoRepository, ChildRepository childRepository) {
        this.accountRepository = accountRepository;
        this.sitterInfoRepository = sitterInfoRepository;
        this.parentInfoRepository = parentInfoRepository;
        this.childRepository = childRepository;
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
                .email(new Email("test@test.com"))
                .build();

        Child child1 = new Child(DateOfBirth.of("20190212"), Gender.FEMALE);
        Child child2 = new Child(DateOfBirth.of("20201003"), Gender.MALE);
        childRepository.save(child1);
        childRepository.save(child2);
        ParentInfo parentInfo = new ParentInfo("아이 둘과 함께 놀아주실 분 구합니다!");
        parentInfo.addChild(child1);
        parentInfo.addChild(child2);
        account2.registerParent(parentInfo);
        accountRepository.save(account2);
    }
}
