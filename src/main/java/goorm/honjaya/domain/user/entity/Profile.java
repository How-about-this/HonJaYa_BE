package goorm.honjaya.domain.user.entity;

import goorm.honjaya.domain.base.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
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
    private User user;
}
