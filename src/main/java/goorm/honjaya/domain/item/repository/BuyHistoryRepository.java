package goorm.honjaya.domain.item.repository;

import goorm.honjaya.domain.item.entity.BuyHistory;
import goorm.honjaya.domain.item.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyHistoryRepository extends JpaRepository<BuyHistory, Long>  {
    BuyHistory findByUserIdAndStatus(String id,String status);
    BuyHistory findTop1ByUserIdAndStatusOrderByBoughtDateDesc(String userId, String status);
}