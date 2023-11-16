package PhishingUniv.Phinocchio.domain.Sos.service;

import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Sos.dto.SosDeleteDto;
import PhishingUniv.Phinocchio.domain.Sos.dto.SosDto;
import PhishingUniv.Phinocchio.domain.Sos.dto.SosUpdateDto;
import PhishingUniv.Phinocchio.domain.Sos.entity.SosEntity;
import PhishingUniv.Phinocchio.domain.Sos.repository.SosRepository;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import PhishingUniv.Phinocchio.exception.Sos.SosAppException;
import PhishingUniv.Phinocchio.exception.Sos.SosErrorCode;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SosService {

    private final SosRepository sosRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private SosEntity convertToEntity(SosDto sosDto) {
        return modelMapper.map(sosDto, SosEntity.class);
    }

    private SosEntity convertToEntity(SosUpdateDto sosUpdateDto) {
        return modelMapper.map(sosUpdateDto, SosEntity.class);
    }

    public ResponseEntity sosList() throws LoginAppException{
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new LoginAppException(LoginErrorCode.USERNAME_NOT_FOUND));
        Long userId = userEntity.getUserId();

        return ResponseEntity.ok(sosRepository.findByUserId(userId));
    }

    public ResponseEntity addSos(SosDto sosDto) throws LoginAppException{
        SosEntity sosEntity = convertToEntity(sosDto);

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new LoginAppException(LoginErrorCode.USERNAME_NOT_FOUND));
        Long userId = userEntity.getUserId();

        sosEntity.setUserId(userId);

        sosRepository.save(sosEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity updateSos(SosUpdateDto sosUpdateDto) throws SosAppException, LoginAppException{
        sosRepository.findById(sosUpdateDto.getSosId())
                .orElseThrow(() -> new SosAppException(SosErrorCode.SOS_NOT_FOUND));

        SosEntity sosEntity = convertToEntity(sosUpdateDto);

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new LoginAppException(LoginErrorCode.USERNAME_NOT_FOUND));
        Long userId = userEntity.getUserId();

        sosEntity.setUserId(userId);
        sosRepository.save(sosEntity);

        return new ResponseEntity(HttpStatus.OK);

    }

    public ResponseEntity deleteSos(SosDeleteDto sosDeleteDto) throws SosAppException{
        SosEntity sosEntity = sosRepository.findById(sosDeleteDto.getSosId())
                .orElseThrow(() -> new SosAppException(SosErrorCode.SOS_NOT_FOUND));

        sosRepository.delete(sosEntity);

        return new ResponseEntity(HttpStatus.OK);

    }

    public List<SosEntity> getSosListByLevel (Long userId, int level) {
        List<SosEntity> sosEntities = sosRepository.findByUserIdAndLevelGreaterThanEqual(userId, level);
        return sosEntities;
    }

}
