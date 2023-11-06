package PhishingUniv.Phinocchio.domain.Twilio.controller;

import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.FCM.service.FCMNotificationService;
import PhishingUniv.Phinocchio.domain.Twilio.service.WebhookService;
import PhishingUniv.Phinocchio.exception.FCM.FCMAppException;
import PhishingUniv.Phinocchio.exception.FCM.FCMErrorCode;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "WebhookController", description = "Twilio webhook을 처리하는 컨트롤러")
public class WebhookController {

    private final WebhookService twilioService;

    private final UserRepository userRepository;

    private final FCMNotificationService fcmNotificationService;

    @PostMapping("/voice")
    @Operation(summary = "Twilio webhook을 처리하는 컨트롤러", description = "Twilio webhook을 처리하는 컨트롤러입니다.")
    public void voice(HttpServletRequest req, HttpServletResponse response) {
        // twilio webhook 처리
        String phoneNumber = twilioService.listenWebhook(req, response);


        // fcm token 조회 후 해당 디바이스로 trigger push
        String fcmToken = userRepository.findByPhoneNumber(phoneNumber).orElseThrow().getFcmToken();
        System.out.println("fcmToken = " + fcmToken);
        try {
            fcmNotificationService.sendPushNotification(fcmToken, "call", "incoming");
        } catch (FirebaseMessagingException e) {
            throw new FCMAppException(FCMErrorCode.FCM_ERROR);
        }
    }
}