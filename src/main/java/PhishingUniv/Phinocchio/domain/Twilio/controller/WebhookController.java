package PhishingUniv.Phinocchio.domain.Twilio.controller;

import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Twilio.service.FCMNotificationService;
import PhishingUniv.Phinocchio.domain.Twilio.service.WebhookService;
import PhishingUniv.Phinocchio.exception.FCM.FCMAppException;
import PhishingUniv.Phinocchio.exception.FCM.FCMErrorCode;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class WebhookController {

    @Autowired
    private WebhookService twilioService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FCMNotificationService fcmNotificationService;

    @PostMapping("/voice")
    public void voice(HttpServletRequest req, HttpServletResponse response) {
        // twilio webhook 처리
        String phoneNumber = twilioService.listenWebhook(req, response);


        // fcm token 조회 후 해당 디바이스로 trigger push
        String fcmToken = userRepository.findByPhoneNumber(phoneNumber).orElseThrow().getFcmToken();
        System.out.println("fcmToken = " + fcmToken);
        try {
            fcmNotificationService.sendPushNotification(fcmToken);
        } catch (FirebaseMessagingException e) {
            throw new FCMAppException(FCMErrorCode.FCM_ERROR, "FCM 관련 오류입니다.");
        }
    }
}