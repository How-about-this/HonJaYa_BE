package goorm.honjaya.domain.image.entity;

import goorm.honjaya.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
public abstract class Image extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    /**
     * 업로드된 파일의 원본 이름
     */
    private String originalImageName;

    /**
     * UUID.randomUUID()를 통해 받은 랜덤값을 통해 S3파일 서버에 저장할 파일의 이름
     */
    private String saveImageName;

    /**
     * 파일의 확장자
     */
    private String extension;

    /**
     * 파일이 저장된 시간 저장
     * Lob 는 긴 문자열을 처리해 줍니다.
     */
    @Lob
    @Setter
    private String imageUrl;

    @Setter
    private Boolean deleted = Boolean.FALSE;

    protected Image() {
        this(null, null, null, null);
    }

    protected Image(String originalImageName, String saveImageName, String extension, String imageUrl) {
        this.originalImageName = originalImageName;
        this.saveImageName = saveImageName;
        this.extension = extension;
        this.imageUrl = imageUrl;
    }

}
