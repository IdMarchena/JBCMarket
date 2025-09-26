package com.afk.backend.control.service;

public interface SmsService {
    void sendSms(String to, String message);
    String generateAndSendOtp(String phone);
    boolean validateOtp(String phone, String otp);
}
