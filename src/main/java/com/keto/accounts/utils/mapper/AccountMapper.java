package com.keto.accounts.utils.mapper;

import com.keto.accounts.entity.Account;
import com.keto.accounts.utils.dto.AccountsDto;

public class AccountMapper {





    public static Account mapToAccounts(AccountsDto dto, Account account) {
        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setBranchAddress(dto.getBranchAddress());
        return account;
    }

    public static AccountsDto mapToAccountsDto(Account account, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(account.getAccountNumber());
        accountsDto.setAccountType(account.getAccountType());
        accountsDto.setBranchAddress(accountsDto.getBranchAddress());
        return accountsDto;
    }
}
