package PhishingUniv.Phinocchio.exception.GoogleSTT;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum STTErrorCode implements ErrorCode {
    FILE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST, "오디오 파일을 업로드할 수 없습니다."),
    CONVERT_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "오디오 파일을 wav 파일로 변환할 수 없습니다."),
    LOCAL_TRANSLATION_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "로컬 오디오 파일을 텍스트 변환할 수 없습니다."),
    GOOGLE_DRIVE_TRANSLATION_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "구글 드라이브 오디오 파일을 텍스트 변환할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}