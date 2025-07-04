package com.example.managesolution.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    PT("PT"),
    MEMBERSHIP("MEMBERSHIP"),;

    private final String type;

}
