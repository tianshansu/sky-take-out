package com.sky.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    //private Long id;

    private String username;

    private String name;

    private String phone;

    @JsonProperty("sex")
    private String gender;

    private String idNumber;

}
