package PhishingUniv.Phinocchio.Setting.service;

import PhishingUniv.Phinocchio.Setting.entity.SettingEntity;
import PhishingUniv.Phinocchio.Setting.repository.SettingRepository;
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
