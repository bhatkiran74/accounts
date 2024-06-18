package com.keto.accounts.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keto.accounts.service.IAccountsService;
import com.keto.accounts.utils.dto.AccountsDto;
import com.keto.accounts.utils.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AccountsController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
/**
 * AccountsControllerTest.java
 * Author: Kiransing bhat
 * Description: This class implements testcases for AccountsController
 **/
class AccountsControllerTest {
    @Autowired
    private AccountsController accountsController;

    @MockBean
    private IAccountsService iAccountsService;

    @Test
    void testCreateAccount() throws Exception {
        // Arrange
        doNothing().when(iAccountsService).createAccount(Mockito.<CustomerDto>any());

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/accounts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(accountsController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"statusCode\":\"201\",\"statusMsg\":\"Account created successfully\"}"));
    }

    @Test
    void testFindAccountDetails() throws Exception {
        // Arrange
        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");
        when(iAccountsService.findAccountDetails(Mockito.<String>any())).thenReturn(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/accounts/fetch")
                .param("mobileNumber", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(accountsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"mobileNumber\":\"42\",\"accountsDto\":{\"accountNumber\""
                                        + ":1234567890,\"accountType\":\"3\",\"branchAddress\":\"42 Main St\"}}"));
    }


    @Test
    void testUpdateAccountDetails() throws Exception {
        // Arrange
        when(iAccountsService.updateAccount(Mockito.<CustomerDto>any())).thenReturn(true);

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/accounts/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(accountsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"statusCode\":\"200\",\"statusMsg\":\"Request processed successfully\"}"));
    }

    @Test
    void testUpdateAccountDetails2() throws Exception {
        // Arrange
        when(iAccountsService.updateAccount(Mockito.<CustomerDto>any())).thenReturn(false);

        AccountsDto accountsDto = new AccountsDto();
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("3");
        accountsDto.setBranchAddress("42 Main St");

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccountsDto(accountsDto);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setMobileNumber("42");
        customerDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/accounts/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(accountsController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"statusCode\":\"500\",\"statusMsg\":\"An error occurred. Please try again or contact Dev team\"}"));
    }
}
