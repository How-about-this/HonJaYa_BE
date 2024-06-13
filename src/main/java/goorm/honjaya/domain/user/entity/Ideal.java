package goorm.honjaya.domain.user.entity;

import goorm.honjaya.domain.base.BaseEntity;
import goorm.honjaya.domain.user.dto.IdealDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Ideal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ideal_id")
    private Long id;

    private int maxAge;

    private int minAge;

    private int maxHeight;

    private int minHeight;

    private int maxWeight;

    private int minWeight;

    private String mbti;

    private String religion;

    private String drinkAmount;

    private boolean smoke;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @Builder
    private Ideal(Long id, int maxAge, int minAge, int maxHeight, int minHeight, int maxWeight, int minWeight, String mbti, String religion, String drinkAmount, boolean smoke, User user) {
        this.id = id;
        this.maxAge = maxAge;
        this.minAge = minAge;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.maxWeight = maxWeight;
        this.minWeight = minWeight;
        this.mbti = mbti;
        this.religion = religion;
        this.drinkAmount = drinkAmount;
        this.smoke = smoke;
        this.user = user;
    }

    public void updateFrom(IdealDto idealDto) {
        this.maxAge = idealDto.getMaxAge();
        this.minAge = idealDto.getMinAge();
        this.maxHeight = idealDto.getMaxHeight();
        this.minHeight = idealDto.getMinHeight();
        this.maxWeight = idealDto.getMaxWeight();
        this.minWeight = idealDto.getMinWeight();
        this.mbti = idealDto.getMbti();
        this.religion = idealDto.getReligion();
        this.drinkAmount = idealDto.getDrinkAmount();
        this.smoke = idealDto.isSmoke();
    }
}
