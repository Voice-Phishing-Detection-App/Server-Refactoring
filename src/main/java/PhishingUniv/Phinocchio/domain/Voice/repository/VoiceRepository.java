package PhishingUniv.Phinocchio.domain.Voice.repository;

import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoiceRepository extends JpaRepository<VoiceEntity, Long> {

    VoiceEntity save(VoiceEntity voiceEntity);

    Optional<VoiceEntity> findByVoiceId(Long voiceId);

}
