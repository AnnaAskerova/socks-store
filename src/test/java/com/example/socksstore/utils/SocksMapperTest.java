package com.example.socksstore.utils;

import com.example.socksstore.model.SocksDto;
import com.example.socksstore.model.SocksEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SocksMapperTest {
    private final SocksMapper mapper = new SocksMapper();

    @Test
    void mapToEntity() {
        String color = "red";
        short cotton = 11;
        long quantity = 20;
        SocksDto dto = new SocksDto();
        dto.setColor(color);
        dto.setQuantity(quantity);
        dto.setCottonPart(cotton);
        SocksEntity entity = mapper.mapToEntity(dto);
        assertEquals(entity.getColor(), color);
        assertEquals(entity.getCottonPart(), cotton);
        assertEquals(entity.getQuantity(), quantity);
    }
}