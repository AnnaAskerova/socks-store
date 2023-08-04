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

    public boolean ifSocksEnough(SocksDto dto) {
        SocksEntity entity = getEntityByDto(dto);
        return entity != null && entity.getQuantity() >= dto.getQuantity();
    }

    public void registerIncome(SocksDto dto) {
        SocksEntity entity = getEntityByDto(dto);
        if (entity == null) {
            repository.save(mapper.mapToEntity(dto));
        } else {
            entity.setQuantity(entity.getQuantity() + dto.getQuantity());
            repository.save(entity);
        }
    }

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

    private SocksEntity getEntityByDto(SocksDto dto) {
        return repository.findByColorIgnoreCaseAndCottonPart(dto.getColor(), dto.getCottonPart());
    }

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
