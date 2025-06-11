package com.example.managesolution.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    TRAINER("TRAINER");

    private final String authority;

}
