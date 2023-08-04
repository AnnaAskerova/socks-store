package com.example.socksstore.utils;

import com.example.socksstore.model.SocksDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Validator {
    private final List<String> operations =
            Arrays.stream(Operation.values()).map(Operation::name).collect(Collectors.toList());

    public boolean checkDto(SocksDto dto) {
        return dto != null && checkString(dto.getColor()) && dto.getQuantity() > 0
                && checkCottonPart(dto.getCottonPart());
    }

    public boolean checkString(String s) {
        return s != null && !s.isBlank();
    }

    public boolean checkCottonPart(short part) {
        return part > -1 && part < 101;
    }

    public boolean checkOperation(String operation) {
        return operation != null && operations.contains(operation.toUpperCase());
    }
}
