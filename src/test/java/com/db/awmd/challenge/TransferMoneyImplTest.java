package com.db.awmd.challenge;

import com.db.awmd.challenge.dao.TransferMoneyImpl;
import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.repository.AccountsRepositoryInMemory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferMoneyImplTest {

    @Autowired
    TransferMoneyImpl transferMoneyImpl;

    @Autowired
    private AccountsRepositoryInMemory accountsRepositoryInMemory;

    @Before
    public void prepareMockMvc() {
        this.accountsRepositoryInMemory.clearAccounts();
    }

    @Test
    public void TansferAmountbetweenAccountWhenBalanceDontGotNegative() throws Exception {
        Account accountFrom = new Account("Id-123", new BigDecimal(1000));
        Account accountTo = new Account("Id-124", new BigDecimal(1000));
        accountsRepositoryInMemory.createAccount(accountFrom);
        accountsRepositoryInMemory.createAccount(accountTo);

        transferMoneyImpl.transferMoney(accountFrom, accountTo, new BigDecimal(200));
        assertThat(this.accountsRepositoryInMemory.getAccount("Id-123").getBalance())
                .isEqualByComparingTo(new BigDecimal(800));
        assertThat(this.accountsRepositoryInMemory.getAccount("Id-124").getBalance())
                .isEqualByComparingTo(new BigDecimal(1200));
    }


    @Test
    public void ThrowsInsufficientBalanceExceptionWhentransferAmountIsMoreThanMinimumBalance()  {

        try {
            Account accountFrom = new Account("Id-123", new BigDecimal(1000));
            Account accountTo = new Account("Id-124", new BigDecimal(1000));
            accountsRepositoryInMemory.createAccount(accountFrom);
            accountsRepositoryInMemory.createAccount(accountTo);
            transferMoneyImpl.transferMoney(accountFrom, accountTo, new BigDecimal(1200));
        } catch (InsufficientBalanceException ex) {

            assertThat(ex.getMessage().contains("Account Id-123 does not have sufficient amount to debit from."));

        }

    }
}
