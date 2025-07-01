package com.naukari.server.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SocialLink {

    @URL
    @Column(name = "linkedin", length = 500)
    private String linkedin;

    @URL
    @Column(name = "facebook", length = 500)
    private String facebook;

    @URL
    @Column(name = "indeed", length = 500)
    private String indeed;

    @URL
    @Column(name = "github", length = 500)
    private String github;

    @URL
    @Column(name = "twitter", length = 500)
    private String twitter;

    @URL
    @Column(name = "portfolio", length = 500)
    private String portfolio;
}
