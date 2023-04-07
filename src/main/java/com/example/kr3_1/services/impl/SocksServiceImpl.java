package com.example.kr3_1.services.impl;

import com.example.kr3_1.model.Socks;
import com.example.kr3_1.services.FileService;
import com.example.kr3_1.services.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SocksServiceImpl implements SocksService {

    private Map<Socks, Integer> socksMap = new HashMap<>();

    public SocksServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    private FileService fileService;

    @Override
    public void add(Socks socks, int amount){
        if (socksMap.get(socks) == null || socksMap.isEmpty()) {
            socksMap.put(socks, amount);
        } else {
            socksMap.put(socks, socksMap.get(socks) + amount);
        }
        saveToFile();
    }

    @Override
    public boolean issue(Socks socks, int amount){
        if (socksMap.get(socks) != null || !socksMap.isEmpty() || getAmountOfSocks(socks)>=amount) {
            socksMap.put(socks, socksMap.get(socks) - amount);
            saveToFile();
            return true;
        }
        return false;
    }

    @Override
    public int getAmountOfSocks(Socks socks){
        return socksMap.get(socks);
    }

    @Override
    public boolean delete(Socks socks, int amount){
        if (socksMap.get(socks) != null || !socksMap.isEmpty() || getAmountOfSocks(socks)>=amount) {
            socksMap.put(socks, socksMap.get(socks) - amount);
            saveToFile();
            return true;
        }
        return false;
    }

    @PostConstruct
    private void init(){
        readFromFile();
    }

    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile(){
        try {
            String json = fileService.readFromFile();
            socksMap = new ObjectMapper().readValue(json, new TypeReference <Map<Socks, Integer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
