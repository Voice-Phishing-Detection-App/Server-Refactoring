package PhishingUniv.Phinocchio.Setting.repository;

import PhishingUniv.Phinocchio.Setting.entity.SettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<SettingEntity, Long> {

    public Optional<SettingEntity> findByUserId(Long userId);

    public SettingEntity save(SettingEntity settingEntity);

}
