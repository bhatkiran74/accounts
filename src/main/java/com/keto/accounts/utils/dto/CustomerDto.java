package com.keto.accounts.utils.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {
    @NotEmpty(message = "The Name field cannot be left empty. Please enter your name to proceed.")
    @Size(min = 5,message = "The Name field must contain more than 5 characters. Please enter a longer name to proceed.")
    private String name;

    @NotEmpty(message = "The email field cannot be left empty. Please enter email to proceed.")
    @Email(message = "Please enter a valid email address to proceed.")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})",message = "The mobile number must contain exactly 10 digits. Please enter a valid mobile number to proceed.")
    private String mobileNumber;
    private AccountsDto accountsDto;
}
