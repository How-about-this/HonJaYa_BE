package goorm.honjaya.domain.user.controller;

import goorm.honjaya.domain.user.dto.IdealDto;
import goorm.honjaya.domain.user.service.IdealService;
import goorm.honjaya.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class IdealController {

    private final IdealService idealService;

    @PostMapping("/users/{id}/ideal")
    public ApiResponse<?> save(@PathVariable("id") Long id, @RequestBody IdealDto idealDto) {
        idealService.save(id, idealDto);
        return ApiResponse.success();
    }

    @GetMapping("/users/{id}/ideal")
    public ApiResponse<?> findById(@PathVariable Long id) {
        IdealDto idealDto = idealService.findByUserId(id);
        return ApiResponse.success(idealDto);
    }

    @PutMapping("/users/{userId}/ideal")
    public ApiResponse<?> update(@PathVariable Long userId,
                                 @RequestBody IdealDto idealDto) {
        idealService.update(userId, idealDto);
        return ApiResponse.success();
    }
}
