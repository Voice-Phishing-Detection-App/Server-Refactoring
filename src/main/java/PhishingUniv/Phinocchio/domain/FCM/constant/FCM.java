package PhishingUniv.Phinocchio.domain.FCM.constant;

public enum FCM {

  TITLE("[피노키오] 보이스피싱으로 의심되는 통화가 발견되었습니다."),
  BODY("보이스피싱 \"%s\"단계입니다.");


  private final String content;

  FCM(String message) {
    this.content = message;
  }

  public String getContent() {
    return content;
  }

  public String generateFcmBody(String levelName) {
    return String.format(content, levelName);
  }
}
