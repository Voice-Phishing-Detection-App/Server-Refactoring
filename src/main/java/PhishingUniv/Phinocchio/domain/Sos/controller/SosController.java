package PhishingUniv.Phinocchio.domain.Sos.controller;

import PhishingUniv.Phinocchio.domain.Sos.dto.SosDeleteDto;
import PhishingUniv.Phinocchio.domain.Sos.dto.SosDto;
import PhishingUniv.Phinocchio.domain.Sos.dto.SosUpdateDto;
import PhishingUniv.Phinocchio.domain.Sos.service.SosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sos")
@AllArgsConstructor
@Tag(name = "SosController", description = "SOS를 관리하는 컨트롤러")
public class SosController {

    private final SosService sosService;

    @GetMapping("/list")
    @Operation(summary = "SOS 리스트를 조회하는 컨트롤러", description = "SOS 리스트를 조회하는 컨트롤러입니다.")
    public ResponseEntity list() {
        return sosService.sosList();
    }

    @PostMapping("/add")
    @Operation(summary = "SOS를 추가하는 컨트롤러", description = "SOS를 추가하는 컨트롤러입니다.")
    public ResponseEntity add(@RequestBody SosDto sosDto) {
        return sosService.addSos(sosDto);
    }

    @PostMapping("/update")
    @Operation(summary = "SOS를 수정하는 컨트롤러", description = "SOS를 수정하는 컨트롤러입니다.")
    public ResponseEntity update(@RequestBody SosUpdateDto sosUpdateDto) {
        return sosService.updateSos(sosUpdateDto);
    }

    @PostMapping("/delete")
    @Operation(summary = "SOS를 삭제하는 컨트롤러", description = "SOS를 삭제하는 컨트롤러입니다.")
    public ResponseEntity delete(@RequestBody SosDeleteDto sosDeleteDto) {
        return sosService.deleteSos(sosDeleteDto);
    }

}
