package PhishingUniv.Phinocchio.domain.Voice.service;

import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import PhishingUniv.Phinocchio.domain.Voice.repository.VoiceRepository;
import PhishingUniv.Phinocchio.exception.Voice.VoiceAppException;
import PhishingUniv.Phinocchio.exception.Voice.VoiceErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoiceService {

  private final VoiceRepository voiceRepository;

  public VoiceEntity saveVoice(String text) throws VoiceAppException {
    VoiceEntity voice = new VoiceEntity();
    voice.setText(text);

    VoiceEntity savedVoice = voiceRepository.save(voice);
    if (savedVoice == null) {
      throw new VoiceAppException(VoiceErrorCode.FAILED_TO_SAVE);
    }

    return savedVoice;
  }
}
