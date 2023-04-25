package com.bank.customer.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Unsigned;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer customerId;
   // @NotNull(message = "Customer name cannot be Null")
    private String customerName;
   // @NotNull(message = "Account Number cannot be Null")
    private String accountNumber;
   // @NotNull(message = "Currency cannot be Null")
    private String currency ;
   // @NotNull(message = "Bank Balance cannot be Null")
    private BigDecimal bankBalance;
   // @NotNull(message = "Sort Code cannot be Null")
    private String sortcode;
    @Column(unique = true)
    @NotNull
    private String loginId;
    @NotNull
    private String password;

}
