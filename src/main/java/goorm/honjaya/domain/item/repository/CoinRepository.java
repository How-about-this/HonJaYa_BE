package goorm.honjaya.domain.item.repository;

import goorm.honjaya.domain.item.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    Optional<Coin> findById(Long id);
    Optional<Coin> findByUserId(Long id);
    @Query("SELECT c.coinQty FROM Coin c WHERE c.userId = :userId")
    Integer findCoinQtyByUserId(@Param("userId") Long userId);
}
