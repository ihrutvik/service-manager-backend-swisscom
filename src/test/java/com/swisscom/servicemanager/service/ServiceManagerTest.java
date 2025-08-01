package com.swisscom.servicemanager.service;

import com.swisscom.servicemanager.exception.ServiceNotFoundException;
import com.swisscom.servicemanager.model.ServiceEntity;
import com.swisscom.servicemanager.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceManagerTest {

    @Mock
    private ServiceRepository repository;

    @Mock
    private ServiceCache cache;

    @InjectMocks
    private ServiceManager manager;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetServiceFromCache() {
        ServiceEntity cached = new ServiceEntity("id2", Collections.emptyList());
        when(cache.get("id2")).thenReturn(Optional.of(cached));

        ServiceEntity result = manager.getServiceById("id2");

        assertNotNull(result);
        assertEquals("id2", result.getId());
        verify(repository, never()).findById(anyString()); // Should not hit DB
    }

    @Test
    void testGetServiceFromDBIfCacheMiss() {
        ServiceEntity dbEntity = new ServiceEntity("id3", Collections.emptyList());
        when(cache.get("id3")).thenReturn(Optional.empty());
        when(repository.findById("id3")).thenReturn(Optional.of(dbEntity));

        ServiceEntity result = manager.getServiceById("id3");

        assertEquals("id3", result.getId());
        verify(cache).put(dbEntity);
    }

    @Test
    void testGetServiceNotFound() {
        when(cache.get("id4")).thenReturn(Optional.empty());
        when(repository.findById("id4")).thenReturn(Optional.empty());

        assertThrows(ServiceNotFoundException.class, () -> manager.getServiceById("id4"));
    }

    @Test
    void testUpdateServiceSuccess() {
        ServiceEntity existing = new ServiceEntity("id5", Collections.emptyList());
        ServiceEntity updated = new ServiceEntity("id5", Collections.emptyList());

        when(repository.findById("id5")).thenReturn(Optional.of(existing));
        when(repository.save(updated)).thenReturn(updated);

        ServiceEntity result = manager.updateService("id5", updated);

        assertEquals("id5", result.getId());
        verify(cache).put(updated);
    }

    @Test
    void testUpdateServiceNotFound() {
        ServiceEntity updated = new ServiceEntity("id6", Collections.emptyList());
        when(repository.findById("id6")).thenReturn(Optional.empty());

        assertThrows(ServiceNotFoundException.class, () -> manager.updateService("id6", updated));
    }
}