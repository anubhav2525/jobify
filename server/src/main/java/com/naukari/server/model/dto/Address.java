package com.naukari.server.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    @Size(max = 500, message = "Street address cannot exceed 500 characters")
    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Size(max = 100, message = "City cannot exceed 100 characters")
    @Column(name = "city", nullable = false)
    private String city;

    @Size(max = 100, message = "State cannot exceed 100 characters")
    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "country", nullable = false)
    @Size(max = 100, message = "Country cannot exceed 100 characters")
    private String country;
}
