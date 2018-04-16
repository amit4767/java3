package com.db.awmd.challenge.domain;

import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class Account {

  @NotNull
  @NotEmpty
  private final String accountId;

  @NotNull
  @Min(value = 0, message = "Initial balance must be positive.")
  private BigDecimal balance;

  public Account(String accountId) {
    this.accountId = accountId;
    this.balance = BigDecimal.ZERO;
  }

  @JsonCreator
  public Account(@JsonProperty("accountId") String accountId,
    @JsonProperty("balance") BigDecimal balance) {
    this.accountId = accountId;
    this.balance = balance;
  }

  public Account debitMoney(BigDecimal debitAmout){
     if (this.balance.compareTo(debitAmout) >= 0){
      this.balance = this.balance.subtract(debitAmout);
    }else {
       throw new InsufficientBalanceException(
               "Account " + accountId + " does not have sufficient amount to debit from.");
     }
     return  this;
  }

  public Account creditMoney(BigDecimal creditMoney){
     this.balance = this.balance.add(creditMoney);
      return  this;
  }

}
