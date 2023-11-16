package PhishingUniv.Phinocchio.domain.Doubt.service;

import PhishingUniv.Phinocchio.domain.Doubt.dto.MLResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLServerResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLService {

    private static RestTemplate restTemplate = null;
    private static String MLServerUrl;

    public MLService() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<?> setMLServerUrl(String url) {
        MLServerUrl = url;

        MLServerResponseDto mlServerResponseDto = new MLServerResponseDto();
        mlServerResponseDto.setMlServer(MLServerUrl);
        return ResponseEntity.ok(mlServerResponseDto);
    }

    public static MLResponseDto processText(MLRequestDto mlRequestDto) {
//        // 머신러닝 서버로 데이터 전송 후 데이터 받음
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MLRequestDto> requestEntity = new HttpEntity<>(mlRequestDto, headers);
        ResponseEntity<MLResponseDto> responseEntity = restTemplate.exchange(MLServerUrl, HttpMethod.POST, requestEntity, MLResponseDto.class);

        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();

        // 테스트용
//        MLResponseDto mlResponseDto = new MLResponseDto();
//        mlResponseDto.setLevel(2);
//
//        return mlResponseDto;
    }
}
