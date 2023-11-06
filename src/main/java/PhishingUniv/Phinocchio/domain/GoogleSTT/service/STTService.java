package PhishingUniv.Phinocchio.domain.GoogleSTT.service;

import PhishingUniv.Phinocchio.domain.GoogleSTT.dto.STTResponseDto;
import PhishingUniv.Phinocchio.domain.GoogleSTT.utility.STTUtility;
import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.exception.GoogleSTT.STTAppException;
import PhishingUniv.Phinocchio.exception.GoogleSTT.STTErrorCode;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@RestController
public class STTService {
    @Value("${AUDIO_FOLDER_PATH}")
    private String AUDIO_FOLDER_PATH;

    private final UserRepository userRepository;

    public ResponseEntity<?> handleGoogleSTTAPI(MultipartFile file)throws InvalidJwtException {
        String ID = SecurityContextHolder.getContext().getAuthentication().getName();
        userRepository.findById(ID).orElseThrow(
                ()->new InvalidJwtException(LoginErrorCode.JWT_USER_NOT_FOUND));

        String fileName = STTUtility.convert(upload(file));
        System.out.println(fileName);
        String text = syncRecognizeFile(fileName); // STT 진행
        return ResponseEntity.ok(new STTResponseDto(text));
    }

    public String upload(MultipartFile file) throws STTAppException {
        String fileName = AUDIO_FOLDER_PATH + file.getOriginalFilename();
        Path filePath = Paths.get(fileName);

        if (!Files.exists(filePath.getParent())) {
            try {
                Files.createDirectories(filePath.getParent());
            } catch (IOException e) {
                throw new STTAppException(STTErrorCode.FOLDER_CREATION_ERROR);
            }
        }

        try {
            Files.write(filePath, file.getBytes());
        } catch (Exception e) {
            throw new STTAppException(STTErrorCode.FILE_UPLOAD_ERROR);
        }

        return fileName;
    }

    public String syncRecognizeFile(String fileName) {
        try (SpeechClient speech = SpeechClient.create()) {
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);

            // 오디오 파일 설정
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(AudioEncoding.LINEAR16)
                            .setLanguageCode("ko-kR")
                            .setSampleRateHertz(48000)
                            .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

            // 오디오 파일 텍스트화
            RecognizeResponse response = speech.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();
            String text = "";
            for (SpeechRecognitionResult result : results) {
                // 오디오 파일 텍스트화 결과 출력 (여러 버전으로 텍스트화되는데 일반적으로 첫 번째가 정확도가 높음)
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                text = alternative.getTranscript();
                System.out.printf("Transcription: %s%n", text);

            }
            return text;
        } catch (Exception e) {
            throw new STTAppException(STTErrorCode.LOCAL_TRANSLATION_ERROR);
        }
    }

}

