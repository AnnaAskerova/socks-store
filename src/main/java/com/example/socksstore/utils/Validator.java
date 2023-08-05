package com.example.socksstore.utils;

import com.example.socksstore.model.SocksDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Validator {
    /**
     * Список строковых представлений констант {@link Operation}
     */
    private final List<String> operations =
            Arrays.stream(Operation.values()).map(Operation::name).collect(Collectors.toList());

    /**
     * Проверяет объект {@link SocksDto} на null и валидирует его поля
     * @param dto объект класса {@link SocksDto}
     * @return true/false
     */
    public boolean checkDto(SocksDto dto) {
        return dto != null && checkString(dto.getColor()) && dto.getQuantity() > 0
                && checkCottonPart(dto.getCottonPart());
    }

    /**
     * Проверяет, не является ли строка null, пустой или стостоящей только из пробелов
     * @param s строкка
     * @return true/false
     */
    public boolean checkString(String s) {
        return s != null && !s.isBlank();
    }

    /**
     * Проверяет, входит ли число в диапазон от 0 до 100
     * @param part число типа short
     * @return true/false
     */
    public boolean checkCottonPart(short part) {
        return part > -1 && part < 101;
    }

    /**
     * Проверяет, не является ли строка null и содержится ли в списке {@link Validator#operations}
     * @param operation строка с описанием операции
     * @return true/false
     */
    public boolean checkOperation(String operation) {
        return operation != null && operations.contains(operation.toUpperCase());
    }
}
