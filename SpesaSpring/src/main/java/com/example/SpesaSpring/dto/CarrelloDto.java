package com.example.SpesaSpring.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarrelloDto {

    private long id ;
    private long userid ;
    private long groceryid ;
    private int quantity;
}
