package com.example.kr3_1.services.impl;

import com.example.kr3_1.model.Color;
import com.example.kr3_1.model.SizeSocks;
import com.example.kr3_1.model.Socks;
import com.example.kr3_1.model.dto.SocksDTO;
import com.example.kr3_1.services.FileService;
import com.example.kr3_1.services.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {

    private LinkedHashMap<Socks, Integer> socksMap = new LinkedHashMap<>();

    public SocksServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    private FileService fileService;

    @Override
    public void add(Socks socks, int amount){
        if (socksMap.isEmpty()) {
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
    public int getAmountOfSocksWithCottonPart(Color color,
                                              SizeSocks sizeSocks,
                                              Integer cottonMin,
                                              Integer cottonMax){
        int amount = 0;
        for (int i = cottonMin; i <= cottonMax; i++){
            Socks socks = new Socks(color, sizeSocks, i);
            if (this.getAmountOfSocks(socks) != 0 ) {
                amount += this.getAmountOfSocks(socks);
            }
        }
        return amount;
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
            String json = new ObjectMapper().writeValueAsString(mapToList(socksMap));
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private LinkedList<SocksDTO> mapToList(LinkedHashMap<Socks, Integer> hashMap){
        LinkedList<SocksDTO> listSocks = new LinkedList<>();
        for(Socks socks : hashMap.keySet()){
            listSocks.add(new SocksDTO(socks,hashMap.get(socks)));
        }
        return listSocks;
    }

    private void readFromFile(){
        try {
            String json = fileService.readFromFile();
            LinkedList<SocksDTO> linkedList = new ObjectMapper().readValue(json, new TypeReference <>() {
            });
            socksMap = listToMap(linkedList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private LinkedHashMap<Socks,Integer> listToMap(LinkedList<SocksDTO> list){
        LinkedHashMap<Socks, Integer> hashMap = new LinkedHashMap<>();
        for (SocksDTO socksDTO : list) {
            hashMap.put(socksDTO.getSocks(), socksDTO.getAmount());
        }
        return hashMap;
    }

}
