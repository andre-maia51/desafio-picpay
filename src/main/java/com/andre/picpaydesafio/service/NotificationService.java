package com.andre.picpaydesafio.service;

import com.andre.picpaydesafio.domain.dto.NotificationDTO;
import com.andre.picpaydesafio.domain.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(User user, String message) throws Exception{
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        try {
            ResponseEntity<String> notificationResponse = restTemplate.postForEntity(
                    "https://util.devi.tools/api/v1/notify", notificationRequest, String.class
            );
        } catch (RestClientException e) {
            System.out.println("Erro ao enviar notificação.\n");
            throw new Exception("Serviço de notificação fora do ar.");
        }
    }
}
