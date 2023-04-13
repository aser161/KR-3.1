package com.example.kr3_1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Socks {
    private Color color;
    private SizeSocks size;
    private int cottonPart;


    public Socks(Color color, SizeSocks size, int cottonPart ) {
        this.color = color;
        this.size = size;
        if (cottonPart  >= 0 && cottonPart  <= 100){
            this.cottonPart = cottonPart ;
        }
    }
}
