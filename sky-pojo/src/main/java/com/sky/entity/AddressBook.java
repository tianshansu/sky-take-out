package com.sky.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * address book
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // User ID
    private Long userId;

    // Consignee
    private String consignee;

    // Phone number
    private String phone;

    // Gender: 0 - Female, 1 - Male
    @JsonProperty("sex")
    private String gender;

    // Province code
    private String provinceCode;

    // Province name
    private String provinceName;

    // City code
    private String cityCode;

    // City name
    private String cityName;

    // District code
    private String districtCode;

    // District name
    private String districtName;

    // Detailed address
    private String detail;

    // Label
    private String label;

    // Default status: 0 - No, 1 - Yes
    private Integer isDefault;

}
