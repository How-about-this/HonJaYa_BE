package goorm.honjaya.domain.user.entity;

import goorm.honjaya.domain.base.BaseEntity;
import goorm.honjaya.domain.user.dto.ProfileDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @Builder
    public Profile(Long id, LocalDate birthday, String gender, int height, int weight, String mbti, String religion, String drinkAmount, boolean smoke, String address, User user) {
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
        this.user = user;
    }

    public void updateFrom(ProfileDto profileDto) {
        this.user.setName(profileDto.getName());
        this.birthday = profileDto.getBirthday();
        this.gender = profileDto.getGender();
        this.height = profileDto.getHeight();
        this.weight = profileDto.getWeight();
        this.mbti = profileDto.getMbti();
        this.religion = profileDto.getReligion();
        this.drinkAmount = profileDto.getDrinkAmount();
        this.smoke = profileDto.isSmoke();
        this.address = profileDto.getAddress();
    }
}
