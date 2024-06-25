package com.keto.accounts.utils.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {
    @NotEmpty(message = "The accountNumber field cannot be left empty. Please enter accountNumber to proceed.")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "The accountNumber number must contain exactly 10 digits. Please enter a valid accountNumber to proceed.")
    private Long accountNumber;
    @NotEmpty(message = "The accountType field cannot be left empty. Please enter accountType to proceed.")
    private String accountType;
    @NotEmpty(message = "The branchAddress field cannot be left empty. Please enter branchAddress to proceed.")
    private String branchAddress;
}
