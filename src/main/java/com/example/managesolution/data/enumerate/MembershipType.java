package com.example.managesolution.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MembershipType {
    PERIOD("PERIOD"),
    COUNT("COUNT"),;
    private final String type;
}
