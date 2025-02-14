package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //wechat openid
    private String openid;

    //name
    private String name;

    //phone number
    private String phone;

    //0 female, 1 male
    private String gender;

    //identity number
    private String idNumber;

    //avatar
    private String avatar;

    //create time
    private LocalDateTime createTime;
}
