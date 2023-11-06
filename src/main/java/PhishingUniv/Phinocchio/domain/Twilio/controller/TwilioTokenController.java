package PhishingUniv.Phinocchio.domain.Twilio.controller;

import PhishingUniv.Phinocchio.domain.Twilio.dto.TwilioTokenDto;
import PhishingUniv.Phinocchio.domain.Twilio.dto.TwilioTokenRequestDto;
import PhishingUniv.Phinocchio.domain.Twilio.service.TwilioTokenService;
import com.twilio.jwt.accesstoken.AccessToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/twilio")
@RestController
@RequiredArgsConstructor
@Tag(name = "TwilioTokenController", description = "Twilio Token을 생성하는 컨트롤러")
public class TwilioTokenController {

    private final TwilioTokenService twilioTokenService;

    @PostMapping("/token")
    @Operation(summary = "Twilio Token을 생성하는 컨트롤러", description = "Twilio Token을 생성하는 컨트롤러입니다.")
    public ResponseEntity<?> generateToken(@RequestBody TwilioTokenRequestDto twilioTokenRequestDto) {
        AccessToken token = twilioTokenService.generateToken(twilioTokenRequestDto);

        return ResponseEntity.ok().body(new TwilioTokenDto(token.toJwt()));
    }
}

