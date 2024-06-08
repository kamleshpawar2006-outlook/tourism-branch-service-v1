package com.tm.company_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TouristCompanyDto {
    private Long branchId;
    @NotEmpty(message = "Branch name cannot be empty")
    private String branchName;

    @NotEmpty(message = "Place cannot be empty")
    private String place;

    @NotEmpty(message = "Website cannot be empty")
    @Pattern(regexp = "^(www\\.).*$", message = "Website should contain 'www'")
    private String website;

    @NotEmpty(message = "Contact cannot be empty")
    @Size(min = 10, max = 10, message = "Contact number must be 10 digits")
    private String contact;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Valid
    private List<TariffDto> tariffs;
}
