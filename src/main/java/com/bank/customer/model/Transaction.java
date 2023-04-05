package com.bank.customer.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TRANSACTION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer transactionId;
    @NotNull(message = "Transaction Name cannot be Null")
    private String transactionName;
    @NotNull(message = "Payee cannot be Null")
    private String payee;
    @NotNull(message = "Payee Account Number cannot be Null")
    private String accountnumber;
    @NotNull(message = "Currency cannot be Null")
    private String currency;
    @NotNull(message = "Amount cannot be Null")
    private BigDecimal amount ;

}
