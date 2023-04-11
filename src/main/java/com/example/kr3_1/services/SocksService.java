package com.example.kr3_1.services;

import com.example.kr3_1.model.Color;
import com.example.kr3_1.model.SizeSocks;
import com.example.kr3_1.model.Socks;

public interface SocksService {
    void add(Socks socks, int amount);

    boolean issue(Socks socks, int amount);


    int getAmountOfSocks(Socks socks);

    int getAmountOfSocksWithCottonPart(Color color,
                                       SizeSocks sizeSocks,
                                       Integer cottonMin,
                                       Integer cottonMax);

    boolean delete(Socks socks, int amount);
}
