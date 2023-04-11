package com.example.kr3_1.model.dto;

import com.example.kr3_1.model.Socks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocksDTO {
    private Socks socks;
    private int amount;
}
