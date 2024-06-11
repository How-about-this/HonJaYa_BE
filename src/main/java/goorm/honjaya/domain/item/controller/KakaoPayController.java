package goorm.honjaya.domain.item.controller;

import goorm.honjaya.domain.item.PayInfoDto;
import goorm.honjaya.domain.item.pay.response.PayReadyResDto;
import goorm.honjaya.domain.item.service.KakaoPayService;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import goorm.honjaya.domain.item.pay.response.PayApproveResDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    /** 결제 준비 redirect url 받기 --> 상품명과 가격을 같이 보내줘야함 */

    @PostMapping("/ready")
    public ResponseEntity<?> getRedirectUrl(@RequestBody PayInfoDto payInfoDto,
    @RequestParam int userId) {
        try {
            PayReadyResDto payReadyResDto = kakaoPayService.getRedirectUrl(payInfoDto,userId);
            String redirectUrl = payReadyResDto.getNext_redirect_pc_url(); // 이 값을 클라이언트에 전달
//            return ResponseEntity.status(HttpStatus.OK).body(redirectUrl);
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", redirectUrl);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
    /**
     * 결제 성공 pid 를  받기 위해 request를 받고 pgToken은 rediret url에 뒤에 붙어오는걸 떼서 쓰기 위함
     */
    @GetMapping("/success/{id}")
    public void afterGetRedirectUrl(HttpServletResponse response,
                                    @PathVariable("id")Long id,
                                    @RequestParam("pg_token") String pgToken) {
        try {
            System.out.println("Controller method entered with id: " + id + " and pgToken: " + pgToken);
            kakaoPayService.getApprove(pgToken,id);
            response.setStatus(HttpStatus.SEE_OTHER.value());
            response.setHeader("Location", "http://localhost:3000/");
        }
        catch(Exception e){
        }
    }
    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public void cancel(HttpServletResponse response) throws IOException {
        System.out.println(response);
        response.setStatus(HttpStatus.SEE_OTHER.value());
        response.setHeader("Location", "http://localhost:3000/");
    }
    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public ResponseEntity<?> fail() {

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
//                .body(new BaseResponse<>(HttpStatus.EXPECTATION_FAILED.value(),"결제가 실패하였습니다."));
                .body("test");

    }

}