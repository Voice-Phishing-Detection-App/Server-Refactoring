package PhishingUniv.Phinocchio.domain.Twilio.service;

import PhishingUniv.Phinocchio.domain.Twilio.dto.TwilioTokenRequestDto;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.exception.FCM.FCMAppException;
import PhishingUniv.Phinocchio.exception.FCM.FCMErrorCode;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VoiceGrant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TwilioTokenService {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String TWILIO_ACCOUNT_SID;
    @Value("${TWILIO_API_KEY}")
    private String TWILIO_API_KEY;
    @Value("${TWILIO_API_SECRET}")
    private String TWILIO_API_SECRET;
    @Value("${outgoingApplicationSid}")
    private String outgoingApplicationSid;


    public AccessToken generateToken(TwilioTokenRequestDto twilioTokenRequestDto){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        String fcmToken = twilioTokenRequestDto.getFcmToken();
        AccessToken token = null;

        UserEntity userEntity = userRepository.findByIdAndFcmToken(id, fcmToken)
                .orElseThrow(() -> new FCMAppException(FCMErrorCode.FCM_NOT_FOUND, "사용자의 fcm token과 일치하지 않습니다."));

        if(userEntity != null){
            // 음성 권한 부여
            VoiceGrant grant = new VoiceGrant();
            grant.setOutgoingApplicationSid(outgoingApplicationSid);

            // 액세스 토큰 만들기
            token = new AccessToken.Builder(TWILIO_ACCOUNT_SID, TWILIO_API_KEY, TWILIO_API_SECRET)
                    .identity(fcmToken)
                    .grant(grant)
                    .build();
        }

        return token;

    }
}
