package goorm.honjaya.domain.item.service;
import goorm.honjaya.domain.item.entity.Coin;
import goorm.honjaya.domain.item.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoinService {

    @Autowired
    private CoinRepository coinRepository;

    public Integer getCoinByUserId(Long userId) throws Exception {
        int coin = coinRepository.findCoinQtyByUserId(userId);
        return coin;
    }
}
