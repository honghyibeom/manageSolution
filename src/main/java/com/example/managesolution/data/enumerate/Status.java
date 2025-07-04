package com.example.managesolution.data.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    UNPAID("UNPAID"),
    NOMEMBERSHIP("NOMEMBERSHIP");

    private final String status;
}
