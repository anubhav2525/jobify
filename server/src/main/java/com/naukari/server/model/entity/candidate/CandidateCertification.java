package com.naukari.server.model.entity.candidate;

import com.naukari.server.model.enums.VerificationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_certifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateCertification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "certification_name", nullable = false)
    @NotBlank
    @Size(max = 255,message = "Certification name is required")
    private String certificationName;

    @Column(name = "issuing_organization", nullable = false)
    @NotBlank
    @Size(max = 255)
    private String issuingOrganization;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "credential_id")
    @Size(max = 255)
    private String credentialId;

    @Column(name = "credential_url")
    @Size(max = 500)
    private String credentialUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status",nullable = false,length = 20)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
