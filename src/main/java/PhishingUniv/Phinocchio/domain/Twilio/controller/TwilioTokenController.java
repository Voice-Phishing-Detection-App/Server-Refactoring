package PhishingUniv.Phinocchio.domain.Twilio.controller;

import PhishingUniv.Phinocchio.domain.Twilio.dto.TwilioTokenDto;
import PhishingUniv.Phinocchio.domain.Twilio.dto.TwilioTokenRequestDto;
import PhishingUniv.Phinocchio.domain.Twilio.service.TwilioTokenService;
import PhishingUniv.Phinocchio.exception.ErrorResponse;
import PhishingUniv.Phinocchio.exception.FCM.FCMAppException;
import com.twilio.jwt.accesstoken.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/twilio")
@RestController
@RequiredArgsConstructor
public class TwilioTokenController {

    private final TwilioTokenService twilioTokenService;


    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody TwilioTokenRequestDto twilioTokenRequestDto) {
        try {
                AccessToken token = twilioTokenService.generateToken(twilioTokenRequestDto);

            return ResponseEntity.ok().body(new TwilioTokenDto(token.toJwt()));
        } catch (FCMAppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getErrorCode().name(), e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

