package PhishingUniv.Phinocchio.domain.Sos.service;

import PhishingUniv.Phinocchio.domain.Sos.dto.SosDeleteDto;
import PhishingUniv.Phinocchio.domain.Sos.dto.SosDto;
import PhishingUniv.Phinocchio.domain.Sos.dto.SosUpdateDto;
import PhishingUniv.Phinocchio.domain.Sos.entity.SosEntity;
import PhishingUniv.Phinocchio.domain.Sos.repository.SosRepository;
import PhishingUniv.Phinocchio.exception.Sos.AppException;
import PhishingUniv.Phinocchio.exception.Sos.ErrorCode;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SosService {

    private final SosRepository sosRepository;

    private final ModelMapper modelMapper;

    private SosEntity convertToEntity(SosDto sosDto) {
        return modelMapper.map(sosDto, SosEntity.class);
    }

    private SosEntity convertToEntity(SosUpdateDto sosUpdateDto) {
        return modelMapper.map(sosUpdateDto, SosEntity.class);
    }

    public ResponseEntity addSos(SosDto sosDto) {
        SosEntity sosEntity = convertToEntity(sosDto);
        sosRepository.save(sosEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity updateSos(SosUpdateDto sosUpdateDto) {
        sosRepository.findById(sosUpdateDto.getSosId())
                .orElseThrow(() -> new AppException(ErrorCode.SOS_NOT_FOUND, "존재하지 않는 긴급연락처입니다."));

        SosEntity sosEntity = convertToEntity(sosUpdateDto);
        sosRepository.save(sosEntity);

        return new ResponseEntity(HttpStatus.OK);

    }

    public ResponseEntity deleteSos(SosDeleteDto sosDeleteDto) {
        SosEntity sosEntity = sosRepository.findById(sosDeleteDto.getSosId())
                .orElseThrow(() -> new AppException(ErrorCode.SOS_NOT_FOUND, "존재하지 않는 긴급연락처입니다."));

        sosRepository.delete(sosEntity);

        return new ResponseEntity(HttpStatus.OK);

    }

    public List<SosEntity> getSosListByLevel (int level) {
        List<SosEntity> sosEntities = sosRepository.findByLevelGreaterThanEqual(level);
        return sosEntities;
    }

}
