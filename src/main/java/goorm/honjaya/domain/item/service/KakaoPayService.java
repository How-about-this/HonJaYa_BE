package goorm.honjaya.domain.item.service;

import goorm.honjaya.domain.item.PayInfoDto;
import goorm.honjaya.domain.item.dto.InventoryDto;
import goorm.honjaya.domain.item.entity.BuyHistory;
import goorm.honjaya.domain.item.entity.Coin;
import goorm.honjaya.domain.item.pay.request.MakePayRequest;
import goorm.honjaya.domain.item.pay.request.PayRequest;
import goorm.honjaya.domain.item.pay.response.PayReadyResDto;
import goorm.honjaya.domain.item.repository.BuyHistoryRepository;
import goorm.honjaya.domain.item.repository.CoinRepository;
import goorm.honjaya.domain.user.repository.UserRepository;
import goorm.honjaya.global.auth.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import goorm.honjaya.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class KakaoPayService {
    private final MakePayRequest makePayRequest;
    private final BuyHistoryRepository buyHistoryRepository;
    private final UserRepository userRepository;
    private final CoinRepository coinRepository;
    private InventoryDto inventoryDto;
    @Value("${pay.admin-key}")
    public String adminKey;


    /** 카오페이 결제를 시작하기 위해 상세 정보를 카카오페이 서버에 전달하고 결제 고유 번호(TID)를 받는 단계입니다.
     * 어드민 키를 헤더에 담아 파라미터 값들과 함께 POST로 요청합니다.
     * 테스트  가맹점 코드로 'TC0ONETIME'를 사용 */
    @Transactional
    public PayReadyResDto getRedirectUrl(PayInfoDto payInfoDto,int userId)throws Exception{
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String name = ((CustomOAuth2User) principal).getUsername();

        Long id = Long.valueOf(userId);

        User user = userRepository.findById(id)
                .orElseThrow(()->new Exception("해당 유저가 존재하지 않습니다."));


        String name = user.getUsername();
        HttpHeaders headers=new HttpHeaders();

        /** 요청 헤더 */
        String auth = "KakaoAK " + adminKey;
        headers.set("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization",auth);

        /** 요청 Body */
        PayRequest payRequest=makePayRequest.getReadyRequest(id,payInfoDto);

        /** Header와 Body 합쳐서 RestTemplate로 보내기 위한 밑작업 */
        HttpEntity<MultiValueMap<String, String>> urlRequest = new HttpEntity<>(payRequest.getMap(), headers);

        /** RestTemplate로 Response 받아와서 DTO로 변환후 return */
        RestTemplate rt = new RestTemplate();
        PayReadyResDto payReadyResDto = rt.postForObject(payRequest.getUrl(), urlRequest, PayReadyResDto.class);

        BuyHistory buyHistory = new BuyHistory();

        buyHistory.setUserId(name);
        buyHistory.setBoughtDate(LocalDateTime.now());
        buyHistory.setAmount(payInfoDto.getPrice());
        buyHistory.setStatus("Pending");
        buyHistory.setPaymentMethod("KakaoPay");
        buyHistory.setTransactionId(payReadyResDto.getTid());
        buyHistory.setDescription(payInfoDto.getItemName());
        buyHistoryRepository.save(buyHistory);


        return payReadyResDto;
    }
    // 결제 성공시 타는 메소드
    @Transactional
    public void getApprove(String pgToken, Long id)throws Exception{


        System.out.println("결제성공 다음 스텝..");

        String userName = String.valueOf(id);

        User user = userRepository.findByUsername(userName)
                .orElseThrow(()->new Exception("해당 유저가 존재하지 않습니다."));
        BuyHistory buyHistory = buyHistoryRepository.findTop1ByUserIdAndStatusOrderByBoughtDateDesc(user.getUsername(), "Pending");

        Optional<Coin> optionalCoin = coinRepository.findByUserId(user.getId());

        Coin coin;
        if (optionalCoin.isPresent()) {
            coin = optionalCoin.get();
            String description = buyHistory.getDescription();
            int coinQtyToAdd = Integer.parseInt(description.substring("zem_".length()));
            coin.setCoinQty(coin.getCoinQty() + coinQtyToAdd);
        } else {
            coin = new Coin();
            coin.setUserId(user.getId());
            String description = buyHistory.getDescription();
            int coinQtyToAdd = Integer.parseInt(description.substring("zem_".length()));
            coin.setCoinQty(coinQtyToAdd);
        }
        coin.setDescription(user.getUsername());
        coin.setUpdateAt(LocalDateTime.now());
        coinRepository.save(coin);
        buyHistory.setStatus("Success");
        buyHistoryRepository.save(buyHistory);
    }


}