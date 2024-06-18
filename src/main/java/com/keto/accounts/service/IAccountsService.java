package com.keto.accounts.service;

import com.keto.accounts.utils.dto.CustomerDto;

public interface IAccountsService {

    void createAccount(CustomerDto customerDto);

    CustomerDto findAccountDetails(String mobileNumber);
}
