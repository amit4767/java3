package com.db.awmd.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferDetails {

    @NotNull
    @NotEmpty
    private final String accountFromId;

    @NotNull
    @NotEmpty
    private final String accountToId;

    @NotNull
    @Min(value = 0, message = "Transfer amount must be positive.")
    private BigDecimal  transferAmount;

    @JsonCreator
    public TransferDetails(@JsonProperty("accountFromId") String accountFromId,
                           @JsonProperty("accountToId") String accountToId,
                   @JsonProperty("transferAmount") BigDecimal transferAmount) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.transferAmount = transferAmount;
    }



}
