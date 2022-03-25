package com.prototype.user.domain;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Embedded
    private Gender gender;

    private boolean isDeleted;

    private LocalDate birthDay;



    private User(Username username, Password password,  LocalDate birthDay, Gender gender) {
        this.username = username;
        this.password = password;
        this.isDeleted = false;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public static User create(Username username, Password password, LocalDate birthDay, Gender gender) {
        return new User(username, password,  birthDay, gender);
    }


    public void delete() {
        if (this.isDeleted){
            throw new IllegalArgumentException();
        }
        this.isDeleted = true;
    }

    public void revive(){
        if (!this.isDeleted){
            throw new IllegalArgumentException();
        }
        this.isDeleted = false;
    }


    public void changePassword(String changePassword) {
        this.password = Password.create(changePassword);
    }

    public void changeUsername(String username) {
        this.username = Username.create(username);
    }

    public void changeBirthday(LocalDate newBirthDay) {
        this.birthDay = newBirthDay;
    }

    public void changeGender(String gender) {
        this.gender = Gender.create(gender.toUpperCase());
    }



    public Long getUserId() {
        return this.id;
    }

    public String getUsername() {
        return this.username.getUsername();
    }

    public String getPassword() {
        return this.password.getPassword();
    }



    public LocalDate getBirthDay() {
        return this.birthDay;
    }

    public Gender getGender() {
        return this.gender;
    }


}
