package PhishingUniv.Phinocchio.exception.Login;


import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LoginErrorCode implements ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    PHONENUMBER_DUPLICATED(HttpStatus.CONFLICT, "이미 사용중인 전화번호입니다."),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND,"사용자를 불러올 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"틀린 비밀번호 입니다."),
    DEVICE_DUPLICATED(HttpStatus.CONFLICT, "해당 기기에서 사용중인 계정이 존재합니다."),
    JWT_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "로그인 중인 사용자 정보 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
