package com.example.managesolution.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    UNPAID("UNPAID"),
    NOMEMBERSHIP("NOMEMBERSHIP"),
    BOOKED("BOOKED"),
    ATTENDED("ATTENDED"),
    NO_SHOW("NO_SHOW");

    private final String status;
}
