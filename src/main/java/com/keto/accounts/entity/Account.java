package com.keto.accounts.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer",schema = "account")
public class Account extends BaseEntity{

    @Column(name = "customer_id")
    private Long customerId;

    @Id
    @Column(name = "account_number")
    private Long accountNumber;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "branch_address")
    private String branchAddress;

}
