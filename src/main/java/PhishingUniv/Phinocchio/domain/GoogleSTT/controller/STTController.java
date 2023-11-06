package PhishingUniv.Phinocchio.domain.GoogleSTT.controller;

import PhishingUniv.Phinocchio.domain.GoogleSTT.service.STTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/stt")
@RestController
@RequiredArgsConstructor
public class STTController {

    public final STTService sttService;

    @PostMapping()
    public ResponseEntity<?> handleGoogleSTTAPI(@RequestParam("file") MultipartFile file) {
        return sttService.handleGoogleSTTAPI(file);
    }
}