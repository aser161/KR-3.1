package com.example.kr3_1.services;

import com.example.kr3_1.model.Socks;

public interface SocksService {
    void add(Socks socks, int amount);

    boolean issue(Socks socks, int amount);

    int getAmountOfSocks(Socks socks);

    boolean delete(Socks socks, int amount);
}
