package com.swisscom.servicemanager.repository;

import com.swisscom.servicemanager.model.ServiceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepository extends MongoRepository<ServiceEntity, String> {
}