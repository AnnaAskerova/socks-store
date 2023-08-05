package com.example.socksstore.repository;

import com.example.socksstore.model.SocksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SocksRepository extends JpaRepository<SocksEntity, Long> {
    /**
     * Возвращает объект с заданными параметрами
     * @param color цвет носков, строка
     * @param cottonPart содержание хлопка
     * @return объект класса {@link SocksEntity}
     */
    SocksEntity findByColorIgnoreCaseAndCottonPart(String color, short cottonPart);

    /**
     * Возвращает количество носков на складе с заданным цветом и содержанием хлопка меньше,
     * чем передано в параметрах
     * @param color цвет носков, строка
     * @param cotton содержание хлопка
     * @return число типа long, количество
     */
    @Query(value = "select sum(quantity) from socks where cotton_part < :cotton and color = :color", nativeQuery = true)
    long getLessThan(String color, short cotton);

    /**
     * Возвращает количество носков на складе с заданным цветом и содержанием хлопка больше,
     * чем передано в параметрах
     * @param color цвет носков, строка
     * @param cotton содержание хлопка
     * @return число типа long, количество
     */
    @Query(value = "select sum(quantity) from socks where cotton_part > :cotton and color = :color", nativeQuery = true)
    long getMoreThan(String color, short cotton);


    /**
     * Возвращает количество носков на складе с заданным цветом и содержанием хлопка
     * @param color цвет носков, строка
     * @param cotton содержание хлопка
     * @return число типа long, количество
     */
    @Query(value = "select sum(quantity) from socks where cotton_part = :cotton and color = :color", nativeQuery = true)
    long getEqual(String color, short cotton);
}
