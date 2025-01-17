package com.service.small.square.domain.notification.helpers;

import java.security.SecureRandom;

public class PinGenerator {

    private PinGenerator() {}

    public static String generatePin() {
        SecureRandom random = new SecureRandom();
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            pin.append(digit);
        }
        return pin.toString();
    }
}