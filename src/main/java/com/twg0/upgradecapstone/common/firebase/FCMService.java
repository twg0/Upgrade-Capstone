package com.twg0.upgradecapstone.common.firebase;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.twg0.upgradecapstone.notification.dto.NotificationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMService {

    private String API_URL = "https://fcm.googleapis.com/v1/projects/al-ddeul-sin-bang/messages:send";
    private final ObjectMapper objectMapper;

    public void sendMessageTo(final NotificationRequest notificationRequest) throws IOException {
    	log.info("-SEND NOTIFICATION TITLE-");
    	log.info("title = {}",notificationRequest.getTitle());
    	log.info("title = {}",notificationRequest.getBody());
    	log.info("title = {}",notificationRequest.getToken());
    	
        String message = makeMessage(
        		notificationRequest.getToken(), 
        		notificationRequest.getTitle(), 
        		notificationRequest.getBody());

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        log.info(response.body().string());
    }

    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FCMMessage fcmMessage = FCMMessage.builder()
                .message(FCMMessage.Message.builder()
                        .token(targetToken)
                        .notification(FCMMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }
    
    private String getAccessToken() throws IOException {
    	log.info("-GET ACCESS TOKEN-");
        String firebaseConfigPath = "firebase/al-ddeul-sin-bang-firebase-adminsdk-s558e-4c2d420b38.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        log.info("-GET ACCESS TOKEN = {}-", googleCredentials.getAccessToken().getTokenValue());
        return googleCredentials.getAccessToken().getTokenValue();
    }
}