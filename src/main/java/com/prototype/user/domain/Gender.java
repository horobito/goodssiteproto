package com.prototype.user.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Gender {
    String gender;

    private Gender(String gender) {
        this.gender = gender;
    }

    public static Gender create(String gender){
        return new Gender(gender);
    };
}
