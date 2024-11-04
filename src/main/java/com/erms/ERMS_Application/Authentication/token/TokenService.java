package com.erms.ERMS_Application.Authentication.token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TokenService {


    @Autowired
    private TokenRepository tokenRepository;


    @Scheduled(fixedRate = 3600000) // Runs every hour
    public void deleteExpiredTokens() {
        tokenRepository.deleteExpiredTokens();
        System.out.println("Expired tokens deleted successfully.");
    }



}
