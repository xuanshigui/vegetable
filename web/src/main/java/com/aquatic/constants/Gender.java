package com.aquatic.constants;

public enum Gender {
    M("男"), F("女");

    String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
