package PhishingUniv.Phinocchio.domain.SMS.service;

import PhishingUniv.Phinocchio.domain.FCM.constant.DoubtLevel;
import PhishingUniv.Phinocchio.domain.Login.service.UserService;
import PhishingUniv.Phinocchio.domain.SMS.constant.SMS;
import PhishingUniv.Phinocchio.domain.Sos.dto.MessageDTO;
import PhishingUniv.Phinocchio.domain.Sos.dto.SmsRequestDTO;
import PhishingUniv.Phinocchio.domain.Sos.dto.SmsResponseDTO;
import PhishingUniv.Phinocchio.domain.Sos.entity.SosEntity;
import PhishingUniv.Phinocchio.domain.Sos.service.SosService;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Sos.SosAppException;
import PhishingUniv.Phinocchio.exception.Sos.SosErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {

  @Value("${naver-cloud-sms.accessKey}")
  private String accessKey;

  @Value("${naver-cloud-sms.secretKey}")
  private String secretKey;

  @Value("${naver-cloud-sms.serviceId}")
  private String serviceId;

  @Value("${naver-cloud-sms.senderPhone}")
  private String phone;

  private final SosService sosService;

  private final UserService userService;

  public void sendSms(int level)
      throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException,
      JsonProcessingException, LoginAppException, SosAppException {

    String smsMessage = generateSmsMessage(level);
    MessageDTO messageDTO = MessageDTO.builder()
        .content(smsMessage)
        .build();

    List<SosEntity> sosEntities = sosService.getSosListByLevel(level);
    for (SosEntity sosEntity : sosEntities) {
      messageDTO.setTo(sosEntity.getPhoneNumber());
      SmsResponseDTO smsResponseDTO = requestSms(messageDTO);
      if (!smsResponseDTO.getStatusCode().equals("202")) {
        throw new SosAppException(SosErrorCode.FAILED_TO_SEND_SMS);
      }
    }
  }

  private String generateSmsMessage(int level) {
    UserEntity user = userService.getCurrentUser();
    String userPhoneNumber = user.getPhoneNumber();
    String smsMessage = SMS.MESSAGE.getSmsContent(userPhoneNumber,
        DoubtLevel.getDoubtLevelName(level));
    return smsMessage;
  }

  private String makeSignature(Long time)
      throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
    String space = " ";          // one space
    String newLine = "\n";          // new line
    String method = "POST";          // method
    String url =
        "/sms/v2/services/" + this.serviceId + "/messages";  // url (include query string)
    String timestamp = time.toString();      // current timestamp (epoch)
    String accessKey = this.accessKey;      // access key id (from portal or Sub Account)
    String secretKey = this.secretKey;

    String message = new StringBuilder()
        .append(method)
        .append(space)
        .append(url)
        .append(newLine)
        .append(timestamp)
        .append(newLine)
        .append(accessKey)
        .toString();

    SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(signingKey);

    byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
    String encodeBase64String = Base64.encodeBase64String(rawHmac);

    return encodeBase64String;
  }

  private SmsResponseDTO requestSms(MessageDTO messageDto)
      throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    Long time = System.currentTimeMillis();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-ncp-apigw-timestamp", time.toString());
    headers.set("x-ncp-iam-access-key", accessKey);
    headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

    List<MessageDTO> messages = new ArrayList<>();
    messages.add(messageDto);

    SmsRequestDTO request = SmsRequestDTO.builder()
        .type("SMS")
        .contentType("COMM")
        .countryCode("82")
        .from(phone)
        .content(messageDto.getContent())
        .messages(messages)
        .build();

    ObjectMapper objectMapper = new ObjectMapper();
    String body = objectMapper.writeValueAsString(request);
    HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    SmsResponseDTO response = restTemplate.postForObject(
        new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"),
        httpBody, SmsResponseDTO.class);

    return response;
  }

}
