package com.swisscom.servicemanager.model;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {
    private String id;
    private List<Owner> owners;
}