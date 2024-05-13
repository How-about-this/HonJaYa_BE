package goorm.honjaya.domain.item.entity;

import goorm.honjaya.domain.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_uuid")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ItemStatus itemStatus;

    private LocalDateTime boughtDate;

    private LocalDateTime usingDate;

    private int boughtQty;
}
