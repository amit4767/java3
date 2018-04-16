package com.db.awmd.challenge.dao;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.repository.AccountsRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class TransferMoneyImpl implements TransferMoney {

    @Autowired
    AccountsRepositoryInMemory accountsRepositoryInMemory;

    @Override
    public  synchronized void transferMoney(Account accountfrom, Account accountto, BigDecimal transferAmount){

        try {
            accountsRepositoryInMemory.updateAccount(accountfrom.debitMoney(transferAmount));
            accountsRepositoryInMemory.updateAccount(accountto.creditMoney(transferAmount));
        } catch (Exception e) {

            accountsRepositoryInMemory.updateAccount(accountfrom);
            accountsRepositoryInMemory.updateAccount(accountto);
            e.printStackTrace();
        }


    }
}
