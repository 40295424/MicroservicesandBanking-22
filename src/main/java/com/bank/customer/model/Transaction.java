package com.bank.customer.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

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
   // @NotNull(message = "Transaction Name cannot be Null")
    private String transactionDescription;
    //@NotNull(message = "Payee cannot be Null")
    private String currency;
    //@NotNull(message = "Amount cannot be Null")
    private BigDecimal amount ;

    private BigDecimal payeeBalance;

    private BigDecimal payerBalance;
    private Date transactionTimestamp;

    private String transactionType;

    private String paymentType;

    @ManyToOne
    @JoinColumn(name = "payee_customer_id")
    private Customer payee;

    @ManyToOne
    @JoinColumn(name = "payer_customer_id")
    private Customer payer;

    @PrePersist
    public void prePersist() {
        this.transactionTimestamp = new Date();
    }

}
