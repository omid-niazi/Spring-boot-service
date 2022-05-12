package org.maktab.houseservice.service;

public interface EmailSenderService {
    void send(String to, String subject, String content);
}
