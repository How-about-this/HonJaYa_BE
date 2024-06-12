package goorm.honjaya.domain.chat.dto;

import goorm.honjaya.domain.chat.entity.ChatRoom;
import goorm.honjaya.domain.user.entity.User;
import goorm.honjaya.domain.image.entity.ProfileImage; // 추가된 부분
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ChatRoomDTO {
    private Long id;
    private Set<ParticipantDto> participants; // 변경된 부분
    private LocalDateTime createdAt; // 추가된 부분

    public static ChatRoomDTO from(ChatRoom chatRoom) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setId(chatRoom.getId());
        chatRoomDTO.setParticipants(
                chatRoom.getParticipants().stream()
                        .map(ParticipantDto::from) // 변경된 부분
                        .collect(Collectors.toSet())
        );
        chatRoomDTO.setCreatedAt(chatRoom.getCreatedAt()); // 추가된 부분
        return chatRoomDTO;
    }

    @Data
    public static class ParticipantDto { // 추가된 부분
        private Long id;
        private String username;
        private String profileImageUrl;

        public static ParticipantDto from(User user) {
            ParticipantDto dto = new ParticipantDto();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setProfileImageUrl(user.getProfileImages().stream()
                    .filter(ProfileImage::isPrimary)
                    .map(ProfileImage::getImageUrl)
                    .findFirst()
                    .orElse(null));
            return dto;
        }
    }
}
