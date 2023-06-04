package PhishingUniv.Phinocchio.Doubt.controller;

import PhishingUniv.Phinocchio.Doubt.dto.DoubtRequestDto;
import PhishingUniv.Phinocchio.Doubt.service.DoubtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DoubtController {

    private final DoubtService doubtService;

    @PostMapping("/doubt")
    public ResponseEntity<?> doubt(@RequestBody DoubtRequestDto doubtDto) {
        return doubtService.doubt(doubtDto);
    }

}
