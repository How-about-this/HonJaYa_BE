package goorm.honjaya.domain.item.dto;

import goorm.honjaya.domain.item.entity.ItemStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryDto {
    private Long id;
    private ItemStatus itemStatus;
    private String userId;
    private LocalDateTime boughtDate;
    private LocalDateTime usingDate;
    private int boughtQty;
    private String pgToken;
}
