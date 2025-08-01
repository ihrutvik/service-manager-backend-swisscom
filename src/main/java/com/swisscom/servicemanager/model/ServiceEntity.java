package com.swisscom.servicemanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "services")
public class ServiceEntity {
    @Id
    private String id;
    private List<Resource> resources;
}