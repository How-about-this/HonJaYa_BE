package goorm.honjaya.domain.user.dto;

import goorm.honjaya.domain.user.entity.Ideal;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class IdealDto {

    private Long id;

    private LocalDate birthday;

    private String gender;

    private int height;

    private int weight;

    private String mbti;

    private String religion;

    private String drinkAmount;

    private boolean smoke;

    private String address;

    @Builder
    private IdealDto(Long id, LocalDate birthday, String gender, int height, int weight, String mbti, String religion, String drinkAmount, boolean smoke, String address) {
        this.id = id;
        this.birthday = birthday;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.mbti = mbti;
        this.religion = religion;
        this.drinkAmount = drinkAmount;
        this.smoke = smoke;
        this.address = address;
    }

    public static IdealDto from(Ideal ideal) {
        return IdealDto.builder()
                .id(ideal.getId())
                .birthday(ideal.getBirthday())
                .gender(ideal.getGender())
                .height(ideal.getHeight())
                .weight(ideal.getWeight())
                .mbti(ideal.getMbti())
                .religion(ideal.getReligion())
                .drinkAmount(ideal.getDrinkAmount())
                .smoke(ideal.isSmoke())
                .build();
    }

    public Ideal toIdeal() {
        return Ideal.builder()
                .id(id)
                .birthday(birthday)
                .gender(gender)
                .height(height)
                .weight(weight)
                .mbti(mbti)
                .religion(religion)
                .drinkAmount(drinkAmount)
                .smoke(smoke)
                .build();
    }
}
