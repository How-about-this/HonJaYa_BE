package goorm.honjaya.domain.user.dto;

import goorm.honjaya.domain.user.entity.Profile;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileDto {

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
    private ProfileDto(Long id, LocalDate birthday, String gender, int height, int weight, String mbti, String religion, String drinkAmount, boolean smoke, String address) {
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

    public static ProfileDto from(Profile profile) {
        return ProfileDto.builder()
                .id(profile.getId())
                .birthday(profile.getBirthday())
                .gender(profile.getGender())
                .height(profile.getHeight())
                .weight(profile.getWeight())
                .mbti(profile.getMbti())
                .religion(profile.getReligion())
                .drinkAmount(profile.getDrinkAmount())
                .smoke(profile.isSmoke())
                .address(profile.getAddress())
                .build();
    }

    public Profile toProfile() {
        return Profile.builder()
                .birthday(birthday)
                .gender(gender)
                .height(height)
                .weight(weight)
                .mbti(mbti)
                .religion(religion)
                .drinkAmount(drinkAmount)
                .smoke(smoke)
                .address(address)
                .build();
    }
}
