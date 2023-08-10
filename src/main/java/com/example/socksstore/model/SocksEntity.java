package com.example.socksstore.model;

import javax.persistence.*;

@Entity
@Table(name = "socks", schema = "public")
public class SocksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String color;
    @Column(name = "cotton_part")
    private short cottonPart;
    private long quantity;

    public SocksEntity(String color, short cottonPart, long quantity) {
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    public SocksEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public short getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(short cottonPart) {
        this.cottonPart = cottonPart;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
