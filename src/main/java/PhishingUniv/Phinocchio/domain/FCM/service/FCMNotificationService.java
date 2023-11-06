package PhishingUniv.Phinocchio.domain.FCM.service;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FCMNotificationService {

    public void sendPushNotification(String fcmToken, String title, String body) throws FirebaseMessagingException {

        Notification notification = new Notification(title, body);

        // 메시지 생성
        Message message = Message.builder()
                .setNotification(notification)
                .setToken(fcmToken) // 리액트 네이티브 애플리케이션의 디바이스 토큰
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

}