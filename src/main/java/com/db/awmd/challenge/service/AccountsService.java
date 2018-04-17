package com.db.awmd.challenge.service;

import com.db.awmd.challenge.dao.TransferMoneyImpl;
import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferDetails;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.exception.InvalidAccountDetailsException;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountsService {

    @Getter
    private final AccountsRepository accountsRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private TransferMoneyImpl transferMoneyImpl;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public void createAccount(Account account) {
        this.accountsRepository.createAccount(account);
    }

    public Account getAccount(String accountId) {
        return this.accountsRepository.getAccount(accountId);
    }

    public synchronized void transferMoney(TransferDetails transferDetails) {
        Account accountFrom = this.accountsRepository.getAccount(transferDetails.getAccountFromId());
        Account accountTo = this.accountsRepository.getAccount(transferDetails.getAccountToId());
        BigDecimal transferAmount = transferDetails.getTransferAmount();
        verifyTransferDetails(accountFrom, accountTo, transferAmount);
        transferMoneyImpl.transferMoney(accountFrom, accountTo, transferAmount);
        emailNotificationService.notifyAboutTransfer(accountFrom, "Your account has been debited with " + transferAmount);
        emailNotificationService.notifyAboutTransfer(accountTo, "Your account has been credited with " + transferAmount);

    }

    private void verifyTransferDetails(Account accountFrom, Account accountTo, BigDecimal transferAmount) {

        if (null != accountFrom) {
            if (null != accountTo) {
                if (!accountFrom.getAccountId().equalsIgnoreCase(accountTo.getAccountId())) {
                    checkValidAmount(accountFrom.getBalance(), transferAmount);
                } else {
                    log.error(" From and to account id value is same");
                    throw new InvalidAccountDetailsException("Can't transfer money between same account id ");
                }

            } else {
                log.error(" Not able to find account to Id information in database  of id =" + accountTo.getAccountId());
                throw new InvalidAccountDetailsException("This " + accountTo + " Account To  id  not present ");
            }
        } else {
            log.error(" Not able to find account From Id information in database  of id =" + accountFrom.getAccountId());
            throw new InvalidAccountDetailsException("This " + accountFrom + " Account From  id  not present ");
        }


    }

    private void checkValidAmount(BigDecimal accountFromAmount, BigDecimal transferAmount) throws InsufficientBalanceException {
        if (transferAmount.compareTo(accountFromAmount) == -1)
            throw new InsufficientBalanceException("Balance cant go negative");

    }

}
