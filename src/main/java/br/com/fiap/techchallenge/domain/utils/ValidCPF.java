package br.com.fiap.techchallenge.domain.utils;

import br.com.fiap.techchallenge.domain.exception.client.InvalidCpfException;
import br.com.fiap.techchallenge.domain.validation.CpfValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidCPF {
    private static final Logger logger = LoggerFactory.getLogger(ValidCPF.class);
    public static void validateCpf(String cpf) throws InvalidCpfException {
        if (!CpfValidator.isValidCpf(cpf)) {
            String message = String.format("Invalid CPF: %s", cpf);
            logger.info(message);
            throw new InvalidCpfException(cpf);
        }
    }


}
