package com.swisscom.servicemanager.service;

import com.swisscom.servicemanager.model.ServiceEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Component
public class ServiceCache {
    private final ConcurrentHashMap<String, ServiceEntity> cache = new ConcurrentHashMap<>();

    public Optional<ServiceEntity> get(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    public void put(ServiceEntity serviceEntity) {
        if (serviceEntity != null && serviceEntity.getId() != null) {
            cache.put(serviceEntity.getId(), serviceEntity);
        }
    }


    public ServiceEntity computeIfAbsent(String id, Function<String, ServiceEntity> loader) {
        return cache.computeIfAbsent(id, loader);
    }

    public void evict(String id) {
        cache.remove(id);
    }
}
