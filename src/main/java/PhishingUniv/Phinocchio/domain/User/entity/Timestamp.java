package PhishingUniv.Phinocchio.domain.User.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass// Entity가 컬럼으로 인식
@Getter
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트합니다.
public class Timestamp {

    @CreatedDate
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @CreatedDate
    @Column(name = "agree_date")
    private LocalDateTime agreeDate;


}