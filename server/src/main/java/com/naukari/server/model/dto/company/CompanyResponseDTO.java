package com.naukari.server.model.dto.company;

import com.naukari.server.model.dto.Address;
import com.naukari.server.model.dto.SocialLink;
import com.naukari.server.model.enums.CompanySize;
import com.naukari.server.model.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDTO {
    private Long id;
    private Long userId;
    private String companyName;
    private String legalName;
    private String companySlug;
    private String description;
    private String websiteUrl;
    private String logoUrl;
    private String thumbnailUrl;
    private String industry;
    private CompanySize companySize;
    private Integer foundedYear;
    private String taxId;
    private String registrationNumber;
    private VerificationStatus verificationStatus;
    private String verificationNotes;
    private boolean isActive;
    private Address address;
    private SocialLink social;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

