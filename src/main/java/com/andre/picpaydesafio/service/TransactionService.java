package com.andre.picpaydesafio.service;

import com.andre.picpaydesafio.domain.dto.NotificationDTO;
import com.andre.picpaydesafio.domain.dto.TransactionDTO;
import com.andre.picpaydesafio.domain.entities.Transaction;
import com.andre.picpaydesafio.domain.entities.User;
import com.andre.picpaydesafio.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;
    private final NotificationService notificationService;

    public TransactionService(UserService userService, TransactionRepository transactionRepository, RestTemplate restTemplate, NotificationService notificationService) {
        this.userService = userService;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
        this.notificationService = notificationService;
    }

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());
        BigDecimal value = transactionDTO.value();

        userService.validateTransaction(sender, value);

        boolean isAuthorized = this.authorizeTransaction(sender, value);
        if(!isAuthorized) {
            throw new Exception("Transação não autorizada.");
        } else {
            Transaction transaction = new Transaction();
            transaction.setAmount(value);
            transaction.setSender(sender);
            transaction.setReceiver(receiver);
            transaction.setTimeStamp(LocalDateTime.now());

            sender.setBalance(sender.getBalance().subtract(value));
            receiver.setBalance(receiver.getBalance().add(value));

            this.transactionRepository.save(transaction);
            this.userService.saveUser(sender);
            this.userService.saveUser(receiver);

            this.notificationService.sendNotification(sender, "Transação realizada com sucesso.");
            this.notificationService.sendNotification(receiver, "Transação recebida com sucesso.");
            return transaction;
        }
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        try {
            ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(
                    "https://util.devi.tools/api/v2/authorize", Map.class
            );

            if(authorizationResponse.getStatusCode() == HttpStatus.OK) {
                assert authorizationResponse.getBody() != null;
                String message = (String) authorizationResponse.getBody().get("status");
                return "success".equalsIgnoreCase(message);
            } else return false;
        } catch (HttpClientErrorException e) {
            return false;
        }

    }
}
