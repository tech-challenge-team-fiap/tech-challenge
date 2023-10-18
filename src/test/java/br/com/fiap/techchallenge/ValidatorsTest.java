package br.com.fiap.techchallenge;

import br.com.fiap.techchallenge.common.validation.CpfValidator;
import br.com.fiap.techchallenge.common.validation.EmailValidator;
import br.com.fiap.techchallenge.common.validation.PhoneNumberValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorsTest {

    @Test
    void shouldValidateCpf() {
        assertTrue(CpfValidator.isValidCpf("valid cpf"));
        assertFalse(CpfValidator.isValidCpf("invalid cpf"));
    }


    @Test
    void shouldValidateEmail() {
        assertTrue(EmailValidator.isValidEmail("valid email"));
        assertFalse(EmailValidator.isValidEmail("invalid email"));
    }


    @Test
    void shouldValidatePhoneNumber() {
        assertTrue(PhoneNumberValidator.isValidPhoneNumber("+5511987654321"));
        assertFalse(PhoneNumberValidator.isValidPhoneNumber("invalid phone number"));
    }
}