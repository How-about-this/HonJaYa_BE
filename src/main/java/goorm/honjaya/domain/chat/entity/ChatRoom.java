package goorm.honjaya.domain.chat.entity;

import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.image.entity.ProfileImage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "chat_room_participants",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<>();

    @ElementCollection
    private Set<String> participantProfileImages = new HashSet<>(); // 참가자의 프로필 이미지 URL을 저장하는 필드

    private LocalDateTime createdAt; // 채팅방 생성 시간

    private LocalDateTime expiresAt; // 채팅방 만료 시간

    public void addParticipant(User user) {
        participants.add(user);
        participantProfileImages.add(user.getProfileImages().stream()
                .filter(ProfileImage::isPrimary)
                .map(ProfileImage::getImageUrl)
                .findFirst()
                .orElse(null));
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.expiresAt = this.createdAt.plusHours(24); // 채팅방 만료 시간 설정 (24시간 후)
    }
}
