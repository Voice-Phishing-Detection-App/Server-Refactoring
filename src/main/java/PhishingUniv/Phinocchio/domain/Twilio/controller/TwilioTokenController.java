package PhishingUniv.Phinocchio.domain.Twilio.controller;

import PhishingUniv.Phinocchio.domain.Twilio.dto.TwilioTokenDto;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VoiceGrant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/twilio")
@RestController
public class TwilioTokenController {

    @Value("${TWILIO_ACCOUNT_SID}")
     private String TWILIO_ACCOUNT_SID;
    @Value("${TWILIO_API_KEY}")
    private String TWILIO_API_KEY;
    @Value("${TWILIO_API_SECRET}")
    private String TWILIO_API_SECRET;
    @Value("${outgoingApplicationSid}")
    private String outgoingApplicationSid;

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody Map<String, Object> payload) {
        try {
            // 클라이언트 식별자 가져오기 (페이로드에서 제공되어야 함)
            String identity = (String) payload.get("identity");

            // 음성 권한 부여
            VoiceGrant grant = new VoiceGrant();
            grant.setOutgoingApplicationSid(outgoingApplicationSid);

            // 액세스 토큰 만들기
            AccessToken token = new AccessToken.Builder(TWILIO_ACCOUNT_SID, TWILIO_API_KEY, TWILIO_API_SECRET)
                    .identity(identity)
                    .grant(grant)
                    .build();

            return ResponseEntity.ok().body(new TwilioTokenDto(token.toJwt()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

