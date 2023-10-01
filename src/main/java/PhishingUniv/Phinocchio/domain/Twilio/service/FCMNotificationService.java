package PhishingUniv.Phinocchio.domain.Twilio.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FCMNotificationService {

    public void sendPushNotification(String fcmToken) throws FirebaseMessagingException {
        Message message = Message.builder()
                .putData("call", "incoming")
                .setToken(fcmToken)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);

        System.out.println("Successfully sent message: " + response);
    }

}
