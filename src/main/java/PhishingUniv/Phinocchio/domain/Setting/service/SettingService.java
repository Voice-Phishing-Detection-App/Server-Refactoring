package PhishingUniv.Phinocchio.domain.Setting.service;

import PhishingUniv.Phinocchio.domain.Setting.entity.SettingEntity;
import PhishingUniv.Phinocchio.domain.Setting.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

    public Optional<SettingEntity> getSettingByUserId(Long userId) {
        return settingRepository.findByUserId(userId);
    }


}
