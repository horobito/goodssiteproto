package com.prototype.user.service;


import lombok.Value;

import java.time.LocalDate;

@Value
public class UserDto {

    Long userId;

    String username;

    String password;

    String gender;

    boolean isDeleted;

    LocalDate birthDay;

    String authority;
}
