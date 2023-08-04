package com.example.socksstore.service;

import com.example.socksstore.model.SocksDto;
import com.example.socksstore.model.SocksEntity;
import com.example.socksstore.repository.SocksRepository;
import com.example.socksstore.utils.SocksMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.AopInvocationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocksServiceTest {
    @Mock
    private SocksRepository repository;
    @Mock
    private SocksMapper mapper;
    @InjectMocks
    private SocksService service;

    private final long quantity = 20;
    private final String color = "red";
    private final short cotton = 11;
    private final long excepted = 12L;
    private SocksDto dto;
    private SocksEntity entity;

    @BeforeEach
    void SetUp() {
        dto = new SocksDto();
        dto.setColor(color);
        dto.setQuantity(quantity);
        dto.setCottonPart(cotton);
        entity = new SocksEntity(color, cotton, quantity);
    }


    @Test
    void ifSocksEnoughTest() {
        when(repository.findByColorIgnoreCaseAndCottonPart(color, cotton)).thenReturn(entity);
        assertTrue(service.ifSocksEnough(dto));
    }

    @Test
    void ifSocksNotEnoughTest() {
        SocksEntity entity1 = new SocksEntity(color, cotton, quantity - 1);
        when(repository.findByColorIgnoreCaseAndCottonPart(color, cotton)).thenReturn(entity1);
        assertFalse(service.ifSocksEnough(dto));
    }

    @Test
    void ifSocksNotExistEnoughTest() {
        when(repository.findByColorIgnoreCaseAndCottonPart(color, cotton)).thenReturn(null);
        assertFalse(service.ifSocksEnough(dto));
    }

    @Test
    void registerIncomeNewTypeTest() {
        when(repository.findByColorIgnoreCaseAndCottonPart(color, cotton)).thenReturn(null);
        when(mapper.mapToEntity(dto)).thenReturn(entity);
        service.registerIncome(dto);
        verify(repository).save(entity);
    }

    @Test
    void registerIncomeOldTypeTest() {
        when(repository.findByColorIgnoreCaseAndCottonPart(color, cotton)).thenReturn(entity);
        service.registerIncome(dto);
        entity.setQuantity(entity.getQuantity() + dto.getQuantity());
        verify(repository).save(entity);
    }

    @Test
    void registerOutcomeWithoutBalanceTest() {
        when(repository.findByColorIgnoreCaseAndCottonPart(color, cotton)).thenReturn(entity);
        service.registerOutcome(dto);
        verify(repository).delete(entity);
    }

    @Test
    void registerOutcomeWithBalanceTest() {
        SocksEntity entity1 = new SocksEntity(color, cotton, quantity + 1);
        when(repository.findByColorIgnoreCaseAndCottonPart(color, cotton)).thenReturn(entity1);
        service.registerOutcome(dto);
        entity.setQuantity(entity1.getQuantity() - dto.getQuantity());
        verify(repository).save(entity1);
    }

    @Test
    void getSocksByParamsEqualTest() {
        String operation = "equal";
        when(repository.getEqual(color, cotton)).thenReturn(excepted);
        assertEquals(excepted, service.getSocksByParams(color, operation, cotton));
    }

    @Test
    void getSocksByParamsLessThanTest() {
        String operation = "lessThan";
        when(repository.getLessThan(color, cotton)).thenReturn(excepted);
        assertEquals(excepted, service.getSocksByParams(color, operation, cotton));
    }

    @Test
    void getSocksByParamsMoreThanTest() {
        String operation = "moreThan";
        when(repository.getMoreThan(color, cotton)).thenReturn(excepted);
        assertEquals(excepted, service.getSocksByParams(color, operation, cotton));
    }

    @Test
    void getSocksByParamsWhenSocksNotExistTest() {
        String operation = "moreThan";
        when(repository.getMoreThan(color, cotton)).thenThrow(AopInvocationException.class);
        assertEquals(0, service.getSocksByParams(color, operation, cotton));
    }
}