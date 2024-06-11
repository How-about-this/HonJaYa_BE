package goorm.honjaya.domain.image.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProfileImagesDeleteDto {

    private List<Long> profileImagesIds;

}
