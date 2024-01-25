package PhishingUniv.Phinocchio.domain.SMS.constant;

public enum SMS {

  MESSAGE("[피노키오] %s 번호에서 보이스피싱으로 의심되는 통화가 발견되었습니다. \"%s\"단계");

  private String smsContent;

  SMS(String smsContent) {
    this.smsContent = smsContent;
  }

  public String getSmsContent(String phoneNumber, String levelName) {
    return String.format(smsContent, phoneNumber, levelName);
  }
}