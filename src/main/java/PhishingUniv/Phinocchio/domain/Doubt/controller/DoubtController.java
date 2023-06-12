package PhishingUniv.Phinocchio.domain.Doubt.controller;

import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.service.DoubtService;
import PhishingUniv.Phinocchio.util.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class DoubtController {

    private final DoubtService doubtService;

    private final TokenService tokenService;

    @PostMapping("/doubt")
    public ResponseEntity<?> doubt(HttpServletRequest request, @RequestBody DoubtRequestDto doubtDto) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Long userId = tokenService.getUserIdByRequest(request);
        return doubtService.doubt(userId, doubtDto);
    }

}
