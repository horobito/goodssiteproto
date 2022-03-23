package com.prototype.user.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password create(String password){
        checkPasswordValidation(password);
        return new Password(password);
    }

    public static void checkPasswordValidation(String password){
        if (password.length()==0 || password.length()>15){
            throw new IllegalArgumentException();
        }
    }
}
