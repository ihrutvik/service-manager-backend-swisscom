package com.swisscom.servicemanager.controller;

import com.swisscom.servicemanager.dto.ApiResponse;
import com.swisscom.servicemanager.model.ServiceEntity;
import com.swisscom.servicemanager.service.ServiceManager;
import com.swisscom.servicemanager.exception.ServiceNotFoundException;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/services")
@Slf4j
public class ServiceController {

    private final ServiceManager manager;

    public ServiceController(ServiceManager manager) {
        this.manager = manager;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ServiceEntity>> create(@Valid @RequestBody ServiceEntity request) {
        log.info("Creating service: {}", request.getId());
        ServiceEntity created = manager.createDummyService(request);
        return ResponseEntity.ok(ApiResponse.success("Service created", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceEntity>> get(@PathVariable String id) {
        try {
            ServiceEntity service = manager.getServiceById(id);
            return ResponseEntity.ok(ApiResponse.success("Service fetched", service));
        } catch (ServiceNotFoundException e) {
            return ResponseEntity.status(404).body(ApiResponse.failure("Service not found"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceEntity>> update(@PathVariable String id, @RequestBody ServiceEntity request) {
        try {
            ServiceEntity updated = manager.updateService(id, request);
            return ResponseEntity.ok(ApiResponse.success("Service updated", updated));
        } catch (ServiceNotFoundException e) {
            return ResponseEntity.status(404).body(ApiResponse.failure("Service not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        try {
            manager.deleteService(id);
            log.info("Service with id={} deleted", id);
            return ResponseEntity.ok(ApiResponse.success("Service deleted", null));
        } catch (ServiceNotFoundException e) {
            log.warn("Service with id={} not found for deletion", id);
            return ResponseEntity.status(404).body(ApiResponse.failure("Service not found"));
        }
    }

}
