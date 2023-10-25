package PhishingUniv.Phinocchio.domain.Twilio.service;

import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Twilio.dto.TwilioTokenRequestDto;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VoiceGrant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private UserRepository userRepository;

    public AccessToken generateToken(TwilioTokenRequestDto twilioTokenRequestDto){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        String fcmToken = twilioTokenRequestDto.getFcmToken();
        AccessToken token = null;

        Optional<UserEntity> userEntity = userRepository.findByIdAndFcmToken(id, fcmToken);

        if(userEntity.isPresent()){
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
