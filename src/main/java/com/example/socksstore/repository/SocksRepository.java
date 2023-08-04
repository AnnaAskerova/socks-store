package com.example.socksstore.repository;

import com.example.socksstore.model.SocksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SocksRepository extends JpaRepository<SocksEntity, Long> {
    SocksEntity findByColorIgnoreCaseAndCottonPart(String color, short cottonPart);

    @Query(value = "select sum(quantity) from socks where cotton_part < :cotton and color = :color", nativeQuery = true)
    long getLessThan(String color, short cotton);

    @Query(value = "select sum(quantity) from socks where cotton_part > :cotton and color = :color", nativeQuery = true)
    long getMoreThan(String color, short cotton);

    @Query(value = "select sum(quantity) from socks where cotton_part = :cotton and color = :color", nativeQuery = true)
    long getEqual(String color, short cotton);
}
