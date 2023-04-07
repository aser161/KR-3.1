package com.example.kr3_1.controllers;

import com.example.kr3_1.model.*;
import com.example.kr3_1.model.Color;
import com.example.kr3_1.services.SocksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/socks")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @GetMapping
    public String i(){
        return "u";
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> addSocks(@RequestParam Color color,
                                            @RequestParam SizeSocks sizeSocks,
                                            @RequestParam Integer cottonPart,
                                            @RequestParam Integer amount) {
        Socks socks = new Socks(color, sizeSocks, cottonPart);
        socksService.add(socks, amount);
        return ResponseEntity.ok().body(socksService.getAmountOfSocks(socks));
    }

    @PutMapping("/issue")
    public ResponseEntity<Integer> issueSocks(@RequestParam Color color,
                                              @RequestParam SizeSocks sizeSocks,
                                              @RequestParam Integer cottonPart,
                                              @RequestParam Integer amount) {
        Socks socks = new Socks(color, sizeSocks, cottonPart);
        if (socksService.issue(socks,amount)) {
            return ResponseEntity.ok().body(socksService.getAmountOfSocks(socks));
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/get")
    public ResponseEntity<Integer> getAmountSocks(@RequestParam Color color,
                                                  @RequestParam SizeSocks sizeSocks,
                                                  @RequestParam Integer cottonMin,
                                                  @RequestParam Integer cottonMax
    ) {
        int total = 0;
        for (int i = cottonMin; i <= cottonMax; i++){
            Socks socks = new Socks(color, sizeSocks, i);
            if (socksService.getAmountOfSocks(socks) != 0 ) {
                total+=socksService.getAmountOfSocks(socks);
            }
        }
        return ResponseEntity.ok().body(total);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteSocks(@RequestParam Color color,
                                               @RequestParam SizeSocks sizeSocks,
                                               @RequestParam Integer cottonPart,
                                               @RequestParam Integer amount) {
        Socks socks = new Socks(color, sizeSocks, cottonPart);
        if (socksService.delete(socks,amount)) {
            return ResponseEntity.ok().body(socksService.getAmountOfSocks(socks));
        }
        return ResponseEntity.internalServerError().build();
    }

}
