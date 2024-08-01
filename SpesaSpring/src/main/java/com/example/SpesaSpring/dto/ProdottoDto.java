package com.example.SpesaSpring.dto;

import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Table(name = "groceries")
public class ProdottoDto {
    private long id;
    private String name;
    private String description;
    private Double price;
    private String photo;
}
