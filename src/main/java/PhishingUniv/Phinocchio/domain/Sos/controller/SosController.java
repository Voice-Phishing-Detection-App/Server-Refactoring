package PhishingUniv.Phinocchio.domain.Sos.controller;

import PhishingUniv.Phinocchio.domain.Sos.dto.SosDeleteDto;
import PhishingUniv.Phinocchio.domain.Sos.dto.SosDto;
import PhishingUniv.Phinocchio.domain.Sos.dto.SosUpdateDto;
import PhishingUniv.Phinocchio.domain.Sos.service.SosService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sos")
@AllArgsConstructor
public class SosController {

    private final SosService sosService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody SosDto sosDto) {
        return sosService.addSos(sosDto);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody SosUpdateDto sosUpdateDto) {
        return sosService.updateSos(sosUpdateDto);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody SosDeleteDto sosDeleteDto) {
        return sosService.deleteSos(sosDeleteDto);
    }

}
