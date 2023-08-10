package com.example.socksstore.controller;

import com.example.socksstore.model.SocksDto;
import com.example.socksstore.service.SocksService;
import com.example.socksstore.utils.Validator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
public class SocksController {
    private final SocksService service;
    private final Validator validator;

    public SocksController(SocksService service, Validator validator) {
        this.service = service;
        this.validator = validator;
    }

    @Operation(
            summary = "Регистрирация прихода",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Все поля обязательны для заполнения: " +
                            "color — цвет, " +
                            "cottonPart — процентное содержание хлопка (целое число от 0 до 100), " +
                            "quantity — количество пар (целое число больше 0)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SocksDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удалось добавить приход"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны"
                    )
            }
    )
    @PostMapping("/income")
    public ResponseEntity<?> registerIncomeSocks(@RequestBody SocksDto dto) {
        if (validator.checkDto(dto)) {
            service.registerIncome(dto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Регистрирация расхода",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Все поля обязательны для заполнения: " +
                            "color — цвет, " +
                            "cottonPart — процентное содержание хлопка (целое число от 0 до 100), " +
                            "quantity — количество пар (целое число больше 0)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SocksDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удалось добавить расход"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны"
                    )
            }
    )
    @PostMapping("/outcome")
    public ResponseEntity<?> registerOutcomeSocks(@RequestBody SocksDto dto) {
        if (validator.checkDto(dto) && service.ifSocksEnough(dto)) {
            service.registerOutcome(dto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Количество носков на складе с заданными параметрами",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<String> getCountSocks(@Parameter(description = "Цвет носков (в любом регистре)")
                                                @RequestParam String color,
                                                @Parameter(description = "Оператор сравнения значения количества хлопка, " +
                                                        "одно значение из: moreThan, lessThan, equal (в любом регистре)")
                                                @RequestParam String operation,
                                                @Parameter(description = "Значение % хлопка (целое число от 0 до 100)")
                                                @RequestParam Short cottonPart) {
        if (validator.checkString(color) && validator.checkOperation(operation)
                && validator.checkCottonPart(cottonPart)) {
            return ResponseEntity.ok(String.valueOf(service.getSocksByParams(color, operation, cottonPart)));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
