package goorm.honjaya.domain.user.controller;

import goorm.honjaya.domain.user.dto.IdealDto;
import goorm.honjaya.domain.user.service.IdealService;
import goorm.honjaya.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IdealController {

    private final IdealService idealService;

    @PostMapping("/user/{id}/ideal")
    public ApiResponse<?> save(@PathVariable("id") Long id, IdealDto idealDto) {
        idealService.save(id, idealDto);
        return ApiResponse.success();
    }
}
