package com.prototype.user.service;


import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class UserService {

    // temp
    public UserDto getLoggedInUser(){
        return new UserDto(
                1L,
                "temp username",
                "temp password",
                "temp gender",
                false,
                LocalDate.now(),
                "temp auth"
        );
    }
    // temp
    public UserDto getUserInfo(Long userId){
        return new UserDto(
                1L,
                "temp username",
                "temp password",
                "temp gender",
                false,
                LocalDate.now(),
                "temp auth"
        );
    }
}
