package com.keto.accounts.controller;

import com.keto.accounts.service.IAccountsService;
import com.keto.accounts.utils.constants.AccountsConstants;
import com.keto.accounts.utils.dto.CustomerDto;
import com.keto.accounts.utils.dto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
/**
 * AccountsController.java
 * Author: Kiransing bhat
 * Description: This class implements AccountsController
 **/
@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    @Autowired
    private IAccountsService iAccountsService;
    /**
     * Endpoint to create a new account based on the provided CustomerDto.
     *
     * @param customerDto The CustomerDto containing customer information.
     * @return ResponseEntity with status 201 (Created) and a response body indicating success.
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED) //header
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));//body
    }
    /**
     * Endpoint to fetch account details for a customer by mobile number.
     *
     * @param mobileNumber The mobile number of the customer.
     * @return ResponseEntity with status 200 (OK) and the CustomerDto containing customer and account details.
     */
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> findAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",message = "The Mobile number must contain exactly 10 digits. Please enter a valid Mobile Number to proceed.") String mobileNumber){
        CustomerDto customerDto= iAccountsService.findAccountDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }
    /**
     * Endpoint to update account details based on the provided CustomerDto.
     *
     * @param customerDto The CustomerDto containing updated account and customer information.
     * @return ResponseEntity with status 200 (OK) and a success response if update is successful,
     *         or status 500 (Internal Server Error) and an error response if update fails.
     */
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if (isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
        }
    }
    /**
     * Endpoint to delete an account by mobile number.
     *
     * @param mobileNumber The mobile number of the customer whose account is to be deleted.
     * @return ResponseEntity with status 200 (OK) and a success response if deletion is successful,
     *         or status 417 (Expectation Failed) and an error response if deletion fails.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam     @Pattern(regexp = "(^$|[0-9]{10})",message = "The Mobile number must contain exactly 10 digits. Please enter a valid Mobile Number to proceed.")
                                                         String mobileNumber){
       boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));
        }
    }


}
