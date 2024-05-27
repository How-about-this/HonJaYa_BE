package goorm.honjaya.domain.user.entity;

import goorm.honjaya.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Ideal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ideal_id")
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
    public Ideal(Long id, LocalDate birthday, String gender, int height, int weight, String mbti, String religion, String drinkAmount, boolean smoke, String address) {
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
}
