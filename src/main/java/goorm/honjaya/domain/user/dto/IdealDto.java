package goorm.honjaya.domain.user.dto;

import goorm.honjaya.domain.user.entity.Ideal;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IdealDto {

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

    @Builder
    private IdealDto(int maxAge, int minAge, int maxHeight, int minHeight, int maxWeight, int minWeight, String mbti, String religion, String drinkAmount, boolean smoke) {
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
    }

    public static IdealDto from(Ideal ideal) {
        return IdealDto.builder()
                .maxAge(ideal.getMaxAge())
                .minAge(ideal.getMinAge())
                .maxHeight(ideal.getMaxHeight())
                .minHeight(ideal.getMinHeight())
                .maxWeight(ideal.getMaxWeight())
                .minWeight(ideal.getMinWeight())
                .mbti(ideal.getMbti())
                .religion(ideal.getReligion())
                .drinkAmount(ideal.getDrinkAmount())
                .smoke(ideal.isSmoke())
                .build();
    }

    public Ideal toIdeal() {
        return Ideal.builder()
                .maxAge(maxAge)
                .minAge(minAge)
                .maxHeight(maxHeight)
                .minHeight(minHeight)
                .maxWeight(maxWeight)
                .minWeight(minWeight)
                .mbti(mbti)
                .religion(religion)
                .drinkAmount(drinkAmount)
                .smoke(smoke)
                .build();
    }
}
