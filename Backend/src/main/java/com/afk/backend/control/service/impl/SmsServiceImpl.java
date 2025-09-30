package com.afk.backend.control.service.impl;

import com.afk.backend.control.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromNumber;

    private final Map<String, OtpEntry> otpCache = new ConcurrentHashMap<>();

    @Override
    public void sendSms(String to, String messageBody) {
        try {
            Twilio.init(accountSid, authToken);

            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(fromNumber),
                    messageBody
            ).create();

            log.info("✅ SMS enviado a {} con SID: {}", to, message.getSid());
        } catch (Exception e) {
            log.error("❌ Error al enviar SMS a {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("No se pudo enviar el SMS");
        }
    }

    @Override
    public String generateAndSendOtp(String phone) {

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);


        otpCache.put(phone, new OtpEntry(otp, LocalDateTime.now().plusMinutes(5)));


        String message = "Tu código de verificación es: " + otp;
        sendSms(phone, message);

        return otp;
    }

    @Override
    public boolean validateOtp(String phone, String otp) {
        OtpEntry entry = otpCache.get(phone);

        if (entry != null &&
                entry.getOtp().equals(otp) &&
                LocalDateTime.now().isBefore(entry.getExpiry())) {
            otpCache.remove(phone);
            return true;
        }

        return false; // inválido o expirado
    }
    private static class OtpEntry {
        private final String otp;
        private final LocalDateTime expiry;

        public OtpEntry(String otp, LocalDateTime expiry) {
            this.otp = otp;
            this.expiry = expiry;
        }

        public String getOtp() { return otp; }
        public LocalDateTime getExpiry() { return expiry; }
    }
}
