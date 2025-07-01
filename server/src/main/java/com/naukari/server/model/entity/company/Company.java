package com.naukari.server.model.entity.company;

import com.naukari.server.model.dto.*;
import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.enums.*;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One relationship with User for the owner detail
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

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

    @Size(max = 500)
    @Column(name = "website_url", nullable = false)
    private String websiteUrl;

    @Size(max = 500)
    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    @Size(max = 500)
    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", length = 20)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Lob
    @Column(name = "verification_notes", nullable = false)
    private String verificationNotes;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyDocument> documents = new ArrayList<>();

    @Embedded
    private SocialLink social;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
