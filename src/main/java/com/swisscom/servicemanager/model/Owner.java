package com.swisscom.servicemanager.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    private String id;
    private String name;
    private String accountNumber;
    private int level;
}