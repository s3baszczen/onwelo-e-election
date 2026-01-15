package com.onwelo.election.voter.interfaces.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeselValidator implements ConstraintValidator<Pesel, String> {

    private static final int PESEL_LENGTH = 11;
    private static final int[] WEIGHTS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

    @Override
    public boolean isValid(String pesel, ConstraintValidatorContext context) {
        if (pesel == null || pesel.isEmpty()) {
            return false;
        }

        if (!validLEngthAndDigits(pesel)) {
            return false;
        }

        return validateChecksum(pesel);
    }

    private static boolean validLEngthAndDigits(String pesel) {
        return pesel.matches("\\d{11}");
    }

    private boolean validateChecksum(String pesel) {
        int sum = 0;
        for (int i = 0; i < WEIGHTS.length; i++) {
            int digit = Character.getNumericValue(pesel.charAt(i));
            sum += digit * WEIGHTS[i];
        }

        int checksum = (10 - (sum % 10)) % 10;
        int lastDigit = Character.getNumericValue(pesel.charAt(PESEL_LENGTH - 1));

        return checksum == lastDigit;
    }
}
