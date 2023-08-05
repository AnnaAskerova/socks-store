package com.example.socksstore.service;

import com.example.socksstore.model.SocksDto;
import com.example.socksstore.model.SocksEntity;
import com.example.socksstore.repository.SocksRepository;
import com.example.socksstore.utils.Operation;
import com.example.socksstore.utils.SocksMapper;
import org.springframework.aop.AopInvocationException;
import org.springframework.stereotype.Service;

@Service
public class SocksService {
    private final SocksRepository repository;
    private final SocksMapper mapper;

    public SocksService(SocksRepository repository, SocksMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Проверяет, есть ли на складе запрашиваемое количество носков
     * с содержанием хлопка и цветом как у передаваемого объекта
     * @param dto объект класса {@link SocksDto}
     * @return true/false
     */
    public boolean ifSocksEnough(SocksDto dto) {
        SocksEntity entity = getEntityByDto(dto);
        return entity != null && entity.getQuantity() >= dto.getQuantity();
    }

    /**
     * Регистрация прихода: создается новая запись в бд или редактируется имеющаяся
     * @param dto объект класса {@link SocksDto}
     */
    public void registerIncome(SocksDto dto) {
        SocksEntity entity = getEntityByDto(dto);
        if (entity == null) {
            repository.save(mapper.mapToEntity(dto));
        } else {
            entity.setQuantity(entity.getQuantity() + dto.getQuantity());
            repository.save(entity);
        }
    }

    /**
     * Регистрация расхода: соответствующая запись в бд редактируется или удаляется
     * @param dto объект класса {@link SocksDto}
     */
    public void registerOutcome(SocksDto dto) {
        SocksEntity entity = getEntityByDto(dto);
        long newQuantity = entity.getQuantity() - dto.getQuantity();
        if (newQuantity == 0) {
            repository.delete(entity);
        } else {
            entity.setQuantity(newQuantity);
            repository.save(entity);
        }
    }

    /**
     * Возвращает объект с цветом и содержанием хлопка, как у переданного в параметрах объекта
     * @param dto объект класса {@link SocksDto}
     * @return объект класса {@link SocksEntity}
     */
    private SocksEntity getEntityByDto(SocksDto dto) {
        return repository.findByColorIgnoreCaseAndCottonPart(dto.getColor(), dto.getCottonPart());
    }

    /**
     * Возвращает количество носков на складе с заданным цветом и количктвои хлопка равным/больше/меньше
     * параметра cottonPart в зависимости от значения operation
     * @param color цвет носков, строка
     * @param operation операция сравнения, строка
     * @param cottonPart содержание хлопка
     * @return число типа long, количество
     */
    public long getSocksByParams(String color, String operation, Short cottonPart) {
        Operation oper = Operation.valueOf(operation.toUpperCase());
        try {
            switch (oper) {
                case EQUAL:
                    return repository.getEqual(color, cottonPart);
                case LESSTHAN:
                    return repository.getLessThan(color, cottonPart);
                case MORETHAN:
                    return repository.getMoreThan(color, cottonPart);
                default:
                    throw new RuntimeException();
            }
        } catch (AopInvocationException e) {
            return 0L;
        }
    }
}
