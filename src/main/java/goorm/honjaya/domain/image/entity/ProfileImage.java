package goorm.honjaya.domain.image.entity;

import goorm.honjaya.domain.member.entity.Member;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("P")
public class ProfileImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
