package goorm.honjaya.domain.item.controller;

import goorm.honjaya.domain.item.entity.Coin;
import goorm.honjaya.domain.item.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class CoinController {
    private final CoinService coinService;
    @GetMapping("/getCoin/{userId}")
    public ResponseEntity<?> getCoinByUserId(@PathVariable Long userId) {
        try {
            int coin = coinService.getCoinByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(coin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
