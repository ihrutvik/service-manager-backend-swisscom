package com.swisscom.servicemanager.service;

import com.swisscom.servicemanager.exception.ServiceNotFoundException;
import com.swisscom.servicemanager.model.ServiceEntity;
import com.swisscom.servicemanager.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ServiceManager {

    private final ServiceRepository repository;
    private final ServiceCache cache;

    public ServiceManager(ServiceRepository repository, ServiceCache cache) {
        this.repository = repository;
        this.cache = cache;
    }

    /**
     * Create and persist a new ServiceEntity.
     * If the cache is used, avoid duplicate insertions for the same ID.
     */
    public ServiceEntity createDummyService(ServiceEntity service) {
        String id = service.getId();

        if (cache.get(id).isPresent() || repository.existsById(id)) {
            log.warn("ServiceEntity id={} already exists in cache or DB", id);
            throw new ServiceNotFoundException("ServiceEntity with id " + id + " already exists");
        }

        ServiceEntity saved = repository.save(service);
        cache.put(saved);
        log.info("ServiceEntity id={} saved and cached", id);
        return saved;
    }


    /**
     * Retrieve ServiceEntity by ID.
     * First check in cache; fallback to database.
     * Throws ServiceNotFoundException if not found.
     */
    public ServiceEntity getServiceById(String id) {
        return cache.get(id).map(service -> {
            log.info("Cache hit for ServiceEntity id={}", id);
            return service;
        }).orElseGet(() -> {
            log.info("Cache miss for ServiceEntity id={}, querying DB", id);
            return repository.findById(id)
                    .map(service -> {
                        cache.put(service);
                        log.debug("ServiceEntity id={} loaded from DB and cached", id);
                        return service;
                    })
                    .orElseThrow(() -> {
                        log.warn("ServiceEntity id={} not found", id);
                        return new ServiceNotFoundException("ServiceEntity with id " + id + " not found");
                    });
        });
    }

    /**
     * Update an existing ServiceEntity by ID.
     * If not found, throws ServiceNotFoundException.
     */
    public ServiceEntity updateService(String id, ServiceEntity updated) {
        return repository.findById(id).map(existing -> {
            updated.setId(id);
            ServiceEntity saved = repository.save(updated);
            cache.put(saved);
            log.info("ServiceEntity id={} updated in DB and cache", id);
            return saved;
        }).orElseThrow(() -> {
            log.warn("Update failed - ServiceEntity id={} not found", id);
            return new ServiceNotFoundException("ServiceEntity with id " + id + " not found");
        });
    }

    /**
     * Delete a ServiceEntity by ID.
     * Also evict from cache.
     */
    public void deleteService(String id) {
        if (!repository.existsById(id)) {
            log.warn("Delete called on non-existing ServiceEntity id={}", id);
            throw new ServiceNotFoundException("ServiceEntity with id " + id + " not found");
        }
        repository.deleteById(id);
        cache.evict(id);
        log.info("ServiceEntity id={} deleted from DB and cache evicted", id);
    }

}
