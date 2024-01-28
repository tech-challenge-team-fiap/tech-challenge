package br.com.fiap.techchallenge.domain.interfaces.abstracts;

import br.com.fiap.techchallenge.domain.exception.client.InvalidCpfException;
import br.com.fiap.techchallenge.domain.exception.client.InvalidEmailException;
import br.com.fiap.techchallenge.domain.exception.client.InvalidPhoneNumberException;
import br.com.fiap.techchallenge.domain.utils.ValidCPF;
import br.com.fiap.techchallenge.domain.validation.EmailValidator;
import br.com.fiap.techchallenge.domain.validation.PhoneNumberValidator;
import org.slf4j.Logger;

public abstract class AbstractClientUseCase {
    private final Logger logger;

    protected AbstractClientUseCase(Logger logger) {
        this.logger = logger;
    }

    protected void validatePhoneNumber(String phone) throws InvalidPhoneNumberException {
        if (!PhoneNumberValidator.isValidPhoneNumber(phone)) {
            String message = String.format("Invalid Phone Number: %s", phone);
            logger.info(message);
            throw new InvalidPhoneNumberException(phone);
        }
    }

    protected void validateCpf(String cpf) throws InvalidCpfException {
        ValidCPF.validateCpf(cpf);
    }

    protected void validateEmail(String email) throws InvalidEmailException {
        if (!EmailValidator.isValidEmail(email)) {
            String message = String.format("Invalid Email: %s", email);
            logger.info(message);
            throw new InvalidEmailException(email);
        }
    }
}
