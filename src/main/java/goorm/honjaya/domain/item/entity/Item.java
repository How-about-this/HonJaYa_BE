package goorm.honjaya.domain.item.entity;

import goorm.honjaya.domain.base.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_code")
    private int id;

    private String itemName;

    private int itemAmount;

    private String desc;
}
