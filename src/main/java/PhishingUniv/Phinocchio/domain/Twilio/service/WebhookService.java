package PhishingUniv.Phinocchio.domain.Twilio.service;

import PhishingUniv.Phinocchio.domain.Twilio.dto.TwilioMessageDto;
import PhishingUniv.Phinocchio.exception.Twilio.TwilioAppException;
import PhishingUniv.Phinocchio.exception.Twilio.TwilioErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Client;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Say;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Service
public class WebhookService {


    private ObjectMapper objectapper = new ObjectMapper();

    private void printStartLine(HttpServletRequest request) {
        System.out.println("--- REQUEST-LINE - start ---");
        System.out.println("request.getMethod() = " + request.getMethod()); //GET
        System.out.println("request.getProtocol() = " + request.getProtocol()); //HTTP/1.1
        System.out.println("request.getScheme() = " + request.getScheme()); //http
        // http://localhost:8080/request-header
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        // /request-header
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        //username=hi
        System.out.println("request.getQueryString() = " +
                request.getQueryString());
        System.out.println("request.isSecure() = " + request.isSecure()); //https사용 유무
        System.out.println("--- REQUEST-LINE - end ---");
        System.out.println();
    }


    private void printHeaders(HttpServletRequest request) {
        System.out.println("--- Headers - start ---");
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ": " +
                        request.getHeader(headerName)));
        System.out.println("--- Headers - end ---");
        System.out.println();
    }


    //Header 편리한 조회
    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("--- Header 편의 조회 start ---");
        System.out.println("[Host 편의 조회]");
        System.out.println("request.getServerName() = " +
                request.getServerName()); //Host 헤더
        System.out.println("request.getServerPort() = " +
                request.getServerPort()); //Host 헤더
        System.out.println();
        System.out.println("[Accept-Language 편의 조회]");

        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " +locale));
        System.out.println("request.getLocale() = " + request.getLocale());

        System.out.println();

        System.out.println("[cookie 편의 조회]");

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }

        System.out.println();
        System.out.println("[Content 편의 조회]");
        System.out.println("request.getContentType() = " +
                request.getContentType());
        System.out.println("request.getContentLength() = " +
                request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " +
                request.getCharacterEncoding());
        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();
    }

    private void printEtc(HttpServletRequest request) { System.out.println("--- 기타 조회 start ---");
        System.out.println("[Remote 정보]");
        System.out.println("request.getRemoteHost() = " +
                request.getRemoteHost()); //
        System.out.println("request.getRemoteAddr() = " +
                request.getRemoteAddr()); //
        System.out.println("request.getRemotePort() = " +
                request.getRemotePort()); //
        System.out.println();
        System.out.println("[Local 정보]");
        System.out.println("request.getLocalName() = " +
                request.getLocalName()); //
        System.out.println("request.getLocalAddr() = " +
                request.getLocalAddr()); //
        System.out.println("request.getLocalPort() = " +
                request.getLocalPort()); //
        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }

    public String getTo(String messageBody) {
        // 문자열 처리로 To 값 추출
        String[] params = messageBody.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals("To")) {
                return keyValue[1].replace("%2B", "+");
            }
        }
        throw new TwilioAppException(TwilioErrorCode.TWILIO_ERROR, "수신 전화번호를 받아올 수 없습니다.");
    }

    public String listenWebhook(HttpServletRequest req, HttpServletResponse response) {

        String phoneNumber = "";

        try {
            // 음성 메시지 설정
            Say say = new Say.Builder("Connecting your call.")
                    .build();
//            printStartLine(req);
//            printHeaders(req);
//            printHeaderUtils(req);
//            printEtc(req);
            ServletInputStream inputStream = req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            System.out.println("messageBody = " + messageBody);

            phoneNumber = getTo(messageBody);
            System.out.println("To = " + phoneNumber);


            // 실제 사용자와의 통화 연결
            Client client = new Client.Builder("client_name_here").build();
            Dial dial = new Dial.Builder().client(client).build();

            VoiceResponse voiceResponse = new VoiceResponse.Builder()
                    .say(say)
                    .dial(dial)
                    .build();


            response.setContentType("application/xml");
            response.getWriter().print(voiceResponse.toXml());

        } catch (Exception e) {
            throw new TwilioAppException(TwilioErrorCode.TWILIO_ERROR, "Twilio webhook 관련 오류입니다.");
        }

        return phoneNumber;
    }

}
