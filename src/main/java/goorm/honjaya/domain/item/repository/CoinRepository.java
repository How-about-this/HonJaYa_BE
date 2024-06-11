package goorm.honjaya.domain.item.repository;

import goorm.honjaya.domain.item.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {
    Optional<Coin> findById(Long id);
    Optional<Coin> findByUserId(Long id);
}
