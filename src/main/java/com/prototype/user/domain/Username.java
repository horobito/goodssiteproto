package com.prototype.user.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Username {

    private String username;

    private Username(String username) {
        this.username = username;
    }

    public static Username create(String username){
        checkNameValidation(username);
        return new Username(username);
    }

    public static void checkNameValidation(String username){
        if (username.length()==0 || username.length()>15){
            throw new IllegalArgumentException();
        }
    }
}
