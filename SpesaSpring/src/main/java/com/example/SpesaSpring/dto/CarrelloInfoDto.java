package com.example.SpesaSpring.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarrelloInfoDto {
    private long id;
    private String name;
    private String photo;
    private Double price;
    private int quantity;
    private double total;
}
