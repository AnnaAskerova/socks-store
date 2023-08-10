package com.example.socksstore.utils;

import com.example.socksstore.model.SocksDto;
import com.example.socksstore.model.SocksEntity;
import org.springframework.stereotype.Component;

@Component
public class SocksMapper {
    /**
     * Маппинг ДТО в сущность
     * @param dto объект класса {@link SocksDto}
     * @return объект класса {@link SocksEntity}
     */
    public SocksEntity mapToEntity(SocksDto dto) {
        return new SocksEntity(dto.getColor().toLowerCase(), dto.getCottonPart(), dto.getQuantity());
    }
}
