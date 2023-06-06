package PhishingUniv.Phinocchio.domain.Doubt.service;

import PhishingUniv.Phinocchio.domain.Doubt.dto.MLResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLRequestDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLService {

    private static RestTemplate restTemplate = null;
    private static final String MLServerUrl = "https://225e-119-70-86-177.ngrok-free.app/predict";

    public MLService() {
        this.restTemplate = new RestTemplate();
    }

    public static MLResponseDto processText(MLRequestDto mlRequestDto) {
//        // 머신러닝 서버로 데이터 전송 후 데이터 받음
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MLRequestDto> requestEntity = new HttpEntity<>(mlRequestDto, headers);
        ResponseEntity<MLResponseDto> responseEntity = restTemplate.exchange(MLServerUrl, HttpMethod.POST, requestEntity, MLResponseDto.class);

        System.out.println(responseEntity.getBody());

        return responseEntity.getBody();

//        MLResponseDto mlResponseDto = new MLResponseDto();
//        mlResponseDto.setLevel(3);
//
//        return mlResponseDto;
    }
}
