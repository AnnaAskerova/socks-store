package com.example.socksstore.utils;

import com.example.socksstore.model.SocksDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {
    private final Validator validator = new Validator();
    private final String color = "red";
    private final short cotton = 11;

    @Test
    void checkDtoNullObjectTest() {
        assertFalse(validator.checkDto(null));
    }

    @Test
    void checkDtoValidObjectTest() {
        SocksDto dto = new SocksDto();
        dto.setColor(color);
        long quantity = 20;
        dto.setQuantity(quantity);
        dto.setCottonPart(cotton);
        assertTrue(validator.checkDto(dto));
    }

    @Test
    void checkDtoInvalidQuantityObjectTest() {
        SocksDto dto = new SocksDto();
        dto.setColor(color);
        dto.setQuantity(-1);
        dto.setCottonPart(cotton);
        assertFalse(validator.checkDto(dto));
    }

    @Test
    void checkValidStringTest() {
        assertTrue(validator.checkString(color));
    }

    @Test
    void checkNullStringTest() {
        assertFalse(validator.checkString(null));
    }

    @Test
    void checkEmptyStringTest() {
        assertFalse(validator.checkString("  "));
    }

    @Test
    void checkValidCottonPartTest() {
        assertTrue(validator.checkCottonPart(cotton));
    }

    @Test
    void checkInvalidCottonPartTest() {
        assertFalse(validator.checkCottonPart((short) 150));
        assertFalse(validator.checkCottonPart((short) -20));
    }

    @Test
    void checkValidOperationTest() {
        String operation = "equal";
        String operation1 = "moreThan";
        String operation2 = "lessThan";
        assertTrue(validator.checkOperation(operation));
        assertTrue(validator.checkOperation(operation1));
        assertTrue(validator.checkOperation(operation2));
    }

    @Test
    void checkInvalidOperationTest() {
        String operation = "dddd";
        assertFalse(validator.checkOperation(operation));
    }
}