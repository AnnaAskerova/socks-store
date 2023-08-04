package com.example.socksstore.utils;

import com.example.socksstore.model.SocksDto;
import com.example.socksstore.model.SocksEntity;
import org.springframework.stereotype.Component;

@Component
public class SocksMapper {
    public SocksEntity mapToEntity(SocksDto dto) {
        return new SocksEntity(dto.getColor().toLowerCase(), dto.getCottonPart(), dto.getQuantity());
    }
}
