package br.com.fiap.techchallenge.common.validation;

public class CpfValidator {

    private static final int CPF_LENGTH = 11;

    public static boolean isValidCpf(String cpf) {

        if (cpf == null) {
            return false;
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != CPF_LENGTH || cpf.matches(cpf.charAt(0) + "{" + CPF_LENGTH + "}")) {
            return false;
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit > 9) {
            firstDigit = 0;
        }

        if (cpf.charAt(9) - '0' != firstDigit) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit > 9) {
            secondDigit = 0;
        }

        return cpf.charAt(10) - '0' == secondDigit;
    }


}
