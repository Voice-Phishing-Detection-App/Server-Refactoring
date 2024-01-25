package PhishingUniv.Phinocchio.domain.Doubt.controller;

import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLServerRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.domain.Doubt.service.DoubtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DoubtController {

  private final DoubtService doubtService;

  @PostMapping("/doubt")
  public DoubtResponseDto doubt(@RequestBody DoubtRequestDto doubtDto)
      throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
    return doubtService.doubt(doubtDto);
  }


  @GetMapping("/doubt/get")
  public List<DoubtEntity> getDoubtList() {
    return doubtService.getDoubtList();
  }


  @PostMapping("/doubt/set-ml-server")
  public ResponseEntity<?> setMLServer(@RequestBody MLServerRequestDto mlServerRequestDto) {
    return doubtService.setMLServerUrl(mlServerRequestDto);
  }
}
