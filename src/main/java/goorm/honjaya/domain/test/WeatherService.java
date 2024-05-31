package goorm.honjaya.domain.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;

    public String getWeather(String city) {
        String apiKey = "55a021485a39c0a1663549328e7bb394";
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, apiKey);
        return restTemplate.getForObject(url, String.class);
    }
}
