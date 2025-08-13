package com.naukari.server.model.dto.company;
import com.naukari.server.model.dto.Address;
import com.naukari.server.model.dto.SocialLink;
import com.naukari.server.model.enums.CompanySize;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Size(max = 255)
    @Column(name = "legal_name", nullable = false)
    private String legalName;

    @Size(max = 255)
    @Column(unique = true, name = "company_slug", nullable = false)
    private String companySlug;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @URL(message = "Please provide a valid website URL")
    @Size(max = 500)
    @Column(name = "website_url", nullable = false)
    private String websiteUrl;

    @Size(max = 100)
    @Column(name = "industry", nullable = false)
    private String industry;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_size", length = 50)
    private CompanySize companySize;

    @Column(name = "founded_year", nullable = false)
    private Integer foundedYear;

    @Size(max = 100)
    @Column(name = "tax_id", nullable = false)
    private String taxId;

    @Size(max = 100)
    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Valid
    @NotNull(message = "Address is required")
    private Address address;

    @Valid
    private SocialLink social;
}
