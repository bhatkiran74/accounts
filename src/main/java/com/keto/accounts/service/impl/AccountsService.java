package com.keto.accounts.service.impl;

import com.keto.accounts.entity.Account;
import com.keto.accounts.entity.Customer;
import com.keto.accounts.exception.CustomerAlreadyExistException;
import com.keto.accounts.exception.ResourseNotFoundException;
import com.keto.accounts.repository.AccountRepository;
import com.keto.accounts.repository.CustomerRepository;
import com.keto.accounts.service.IAccountsService;
import com.keto.accounts.utils.constants.AccountsConstants;
import com.keto.accounts.utils.dto.AccountsDto;
import com.keto.accounts.utils.dto.CustomerDto;
import com.keto.accounts.utils.mapper.AccountMapper;
import com.keto.accounts.utils.mapper.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/**
 * AccountsServiceImpl.java
 * Author: Kiransing bhat
 * Description: This class implements IAccountsService
 **/
@Service
@Slf4j
public class AccountsService implements IAccountsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Creates a new account for the customer.
     *
     * @param customerDto The CustomerDto containing customer information.
     * @throws CustomerAlreadyExistException if a customer with the given mobile number exists.
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> byMobileNumber = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (byMobileNumber.isPresent()){
            throw new CustomerAlreadyExistException("Customer already exist with given mobile number "+customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Annoumous");
        Customer savedCustomer = customerRepository.save(customer);
        System.out.println(createNewAccount(savedCustomer).getBranchAddress());
        accountRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * Finds account details for a customer by mobile number.
     *
     * @param mobileNumber The mobile number of the customer.
     * @return The CustomerDto containing customer and account details.
     * @throws ResourseNotFoundException if the customer or account is not found.
     */
    @Override
    public CustomerDto findAccountDetails(String mobileNumber) {
        Customer custBymobNum = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->
                new ResourseNotFoundException("Customer","Mobile Number",mobileNumber));

        Account account = accountRepository.findByCustomerId(custBymobNum.getCustomerId()).orElseThrow(() ->
                new ResourseNotFoundException("Account", "Customer Id", custBymobNum.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(custBymobNum,new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(account,new AccountsDto()));
        return customerDto;
    }

    /**
     * Creates a new account for the given customer.
     *
     * @param customer The Customer entity.
     * @return The newly created Account entity.
     */
    private Account createNewAccount(Customer customer){
        log.info("start acct");
        Account account=new Account();
        account.setCustomerId(customer.getCustomerId());
        Long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
        System.out.println("=>>>>>>"+randomAccountNumber);
        account.setAccountNumber(randomAccountNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("Annoumous");
        return account;
    }
}
