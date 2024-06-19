package goorm.honjaya.domain.item.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "buy_history", uniqueConstraints = @UniqueConstraint(columnNames = "buy_history_id"))
public class BuyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buy_history_id")
    private Long id;
    private String userId;
    private LocalDateTime boughtDate;
    private int amount;
    private String status;
    private String paymentMethod;
    private String description;
    private String transactionId;

    // Getters and Setters

    public BuyHistory() {}

    public BuyHistory(String userId, LocalDateTime boughtDate, int amount, String status, String paymentMethod, String description, String transactionId) {
        this.userId = userId;
        this.boughtDate = boughtDate;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.transactionId = transactionId;
    }
}

