package com.keto.accounts.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.keto.accounts.entity.Account;
import com.keto.accounts.entity.Customer;
import com.keto.accounts.exception.CustomerAlreadyExistException;
import com.keto.accounts.exception.ResourseNotFoundException;
import com.keto.accounts.repository.AccountRepository;
import com.keto.accounts.repository.CustomerRepository;

import java.time.LocalDate;
import java.util.Optional;

import com.keto.accounts.utils.dto.AccountsDto;
import com.keto.accounts.utils.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountsService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AccountsServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountsService accountsService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testDeleteAccount() {
        // Arrange
        doNothing().when(accountRepository).deleteByCustomerId(Mockito.<Long>any());

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        Optional<Customer> ofResult = Optional.of(customer);
        doNothing().when(customerRepository).deleteById(Mockito.<Long>any());
        when(customerRepository.findByMobileNumber(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        boolean actualDeleteAccountResult = accountsService.deleteAccount("42");

        // Assert
        verify(accountRepository).deleteByCustomerId(Mockito.<Long>any());
        verify(customerRepository).findByMobileNumber(eq("42"));
        verify(customerRepository).deleteById(Mockito.<Long>any());
        assertTrue(actualDeleteAccountResult);
    }
    @Test
    void testDeleteAccount2() {
        // Arrange
        doNothing().when(accountRepository).deleteByCustomerId(Mockito.<Long>any());

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        Optional<Customer> ofResult = Optional.of(customer);
        doThrow(new CustomerAlreadyExistException("An error occurred")).when(customerRepository)
                .deleteById(Mockito.<Long>any());
        when(customerRepository.findByMobileNumber(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(CustomerAlreadyExistException.class, () -> accountsService.deleteAccount("42"));
        verify(accountRepository).deleteByCustomerId(Mockito.<Long>any());
        verify(customerRepository).findByMobileNumber(eq("42"));
        verify(customerRepository).deleteById(Mockito.<Long>any());
    }
    @Test
    void testDeleteAccount3() {
        // Arrange
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findByMobileNumber(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () -> accountsService.deleteAccount("42"));
        verify(customerRepository).findByMobileNumber(eq("42"));
    }
    @Test
    void testUpdateAccount() {
        // Arrange
        Account account = new Account();
        account.setAccountNumber(1234567890L);
        account.setAccountType("3");
        account.setBranchAddress("42 Main St");
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        account.setCustomerId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setUpdatedBy("2020-03-01");
        Optional<Account> ofResult = Optional.of(account);

        Account account2 = new Account();
        account2.setAccountNumber(1234567890L);
        account2.setAccountType("3");
        account2.setBranchAddress("42 Main St");
        account2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        account2.setCustomerId(1L);
        account2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account2.setUpdatedBy("2020-03-01");
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account2);
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        Optional<Customer> ofResult2 = Optional.of(customer);

        Customer customer2 = new Customer();
        customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer2.setCustomerId(1L);
        customer2.setEmail("jane.doe@example.org");
        customer2.setMobileNumber("42");
        customer2.setName("Name");
        customer2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer2.setUpdatedBy("2020-03-01");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");

        // Act
        boolean actualUpdateAccountResult = accountsService.updateAccount(customerDto);

        // Assert
        verify(accountRepository).findById(Mockito.<Long>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(accountRepository).save(Mockito.<Account>any());
        verify(customerRepository).save(Mockito.<Customer>any());
        assertTrue(actualUpdateAccountResult);
    }
    @Test
    void testUpdateAccount2() {
        // Arrange
        Account account = new Account();
        account.setAccountNumber(1234567890L);
        account.setAccountType("3");
        account.setBranchAddress("42 Main St");
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        account.setCustomerId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setUpdatedBy("2020-03-01");
        Optional<Account> ofResult = Optional.of(account);

        Account account2 = new Account();
        account2.setAccountNumber(1234567890L);
        account2.setAccountType("3");
        account2.setBranchAddress("42 Main St");
        account2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        account2.setCustomerId(1L);
        account2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account2.setUpdatedBy("2020-03-01");
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account2);
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.save(Mockito.<Customer>any()))
                .thenThrow(new CustomerAlreadyExistException("An error occurred"));
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");

        // Act and Assert
        assertThrows(CustomerAlreadyExistException.class, () -> accountsService.updateAccount(customerDto));
        verify(accountRepository).findById(Mockito.<Long>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(accountRepository).save(Mockito.<Account>any());
        verify(customerRepository).save(Mockito.<Customer>any());
    }
    @Test
    void testUpdateAccount3() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () -> accountsService.updateAccount(customerDto));
        verify(accountRepository).findById(Mockito.<Long>any());
    }
    @Test
    void testUpdateAccount4() {
        // Arrange
        Account account = new Account();
        account.setAccountNumber(1234567890L);
        account.setAccountType("3");
        account.setBranchAddress("42 Main St");
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        account.setCustomerId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setUpdatedBy("2020-03-01");
        Optional<Account> ofResult = Optional.of(account);

        Account account2 = new Account();
        account2.setAccountNumber(1234567890L);
        account2.setAccountType("3");
        account2.setBranchAddress("42 Main St");
        account2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        account2.setCustomerId(1L);
        account2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account2.setUpdatedBy("2020-03-01");
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account2);
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () -> accountsService.updateAccount(customerDto));
        verify(accountRepository).findById(Mockito.<Long>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(accountRepository).save(Mockito.<Account>any());
    }
    @Test
    void testFindAccountDetails() {
        // Arrange
        Account account = new Account();
        account.setAccountNumber(1234567890L);
        account.setAccountType("3");
        account.setBranchAddress("42 Main St");
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        account.setCustomerId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setUpdatedBy("2020-03-01");
        Optional<Account> ofResult = Optional.of(account);
        when(accountRepository.findByCustomerId(Mockito.<Long>any())).thenReturn(ofResult);

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findByMobileNumber(Mockito.<String>any())).thenReturn(ofResult2);

        // Act
        CustomerDto actualFindAccountDetailsResult = accountsService.findAccountDetails("42");

        // Assert
        verify(accountRepository).findByCustomerId(Mockito.<Long>any());
        verify(customerRepository).findByMobileNumber(eq("42"));
        AccountsDto accountsDto = actualFindAccountDetailsResult.getAccountsDto();
        assertEquals("3", accountsDto.getAccountType());
        assertEquals("42 Main St", accountsDto.getBranchAddress());
        assertEquals("42", actualFindAccountDetailsResult.getMobileNumber());
        assertEquals("Name", actualFindAccountDetailsResult.getName());
        assertEquals("jane.doe@example.org", actualFindAccountDetailsResult.getEmail());
        assertEquals(1234567890L, accountsDto.getAccountNumber().longValue());
    }
    @Test
    void testFindAccountDetails2() {
        // Arrange
        when(accountRepository.findByCustomerId(Mockito.<Long>any()))
                .thenThrow(new CustomerAlreadyExistException("An error occurred"));

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findByMobileNumber(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(CustomerAlreadyExistException.class, () -> accountsService.findAccountDetails("42"));
        verify(accountRepository).findByCustomerId(Mockito.<Long>any());
        verify(customerRepository).findByMobileNumber(eq("42"));
    }
    @Test
    void testFindAccountDetails3() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findByCustomerId(Mockito.<Long>any())).thenReturn(emptyResult);

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findByMobileNumber(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () -> accountsService.findAccountDetails("42"));
        verify(accountRepository).findByCustomerId(Mockito.<Long>any());
        verify(customerRepository).findByMobileNumber(eq("42"));
    }
    @Test
    void testFindAccountDetails4() {
        // Arrange
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findByMobileNumber(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourseNotFoundException.class, () -> accountsService.findAccountDetails("42"));
        verify(customerRepository).findByMobileNumber(eq("42"));
    }
    @Test
    void testCreateAccount() {
        // Arrange
        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findByMobileNumber(Mockito.<String>any())).thenReturn(ofResult);

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");

        // Act and Assert
        assertThrows(CustomerAlreadyExistException.class, () -> accountsService.createAccount(customerDto));
        verify(customerRepository).findByMobileNumber(eq("42"));
    }
    @Test
    void testCreateAccount2() {
        // Arrange
        Account account = new Account();
        account.setAccountNumber(1234567890L);
        account.setAccountType("3");
        account.setBranchAddress("42 Main St");
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        account.setCustomerId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setUpdatedBy("2020-03-01");
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account);

        Customer customer = new Customer();
        customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        customer.setCustomerId(1L);
        customer.setEmail("jane.doe@example.org");
        customer.setMobileNumber("42");
        customer.setName("Name");
        customer.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        customer.setUpdatedBy("2020-03-01");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findByMobileNumber(Mockito.<String>any())).thenReturn(emptyResult);

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");

        // Act
        accountsService.createAccount(customerDto);

        // Assert
        verify(customerRepository).findByMobileNumber(eq("42"));
        verify(accountRepository).save(Mockito.<Account>any());
        verify(customerRepository).save(Mockito.<Customer>any());
    }
}
