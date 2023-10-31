package br.com.fiap.techchallenge;

import br.com.fiap.techchallenge.adapter.driven.entities.Client;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.client.RegisterNewClientUseCase;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.common.exception.client.ClientAlreadyExistsException;
import br.com.fiap.techchallenge.common.exception.client.InvalidCpfException;
import br.com.fiap.techchallenge.common.exception.client.InvalidPhoneNumberException;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterNewClientUseCaseTest {

    @Mock
    private ClientGateway clientGateway;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private RegisterNewClientUseCase registerNewClientUseCase;

    @Test
    void shouldRegisterNewClient() {
        // Set up mock behavior to indicate that no client with the given CPF exists
        when(clientRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(clientGateway.register(any(Client.class))).thenReturn(ResponseEntity.ok(UUID.randomUUID()));

        ClientFormDto clientFormDto = new ClientFormDto("John Doe", "550.705.870-99", "john.doe@example.com", "+5511987654321", LocalDateTime.now());

        // This should not throw an exception
        assertDoesNotThrow(() -> registerNewClientUseCase.register(clientFormDto));
    }

    @Test
    void shouldThrowInvalidInputExceptionForInvalidCpf() {
        ClientFormDto clientFormDto = new ClientFormDto("John Doe", "55070587098", "john.doe@example.com", "1234567890", LocalDateTime.now());
        clientFormDto.setCpf("55070587098");

        assertThrows(InvalidCpfException.class, () -> registerNewClientUseCase.register(clientFormDto));
    }

    @Test
    void shouldThrowInvalidInputExceptionForInvalidEmail() {
        ClientFormDto clientFormDto = new ClientFormDto("John Doe", "550.705.870-99", "john.doe@examplecom", "1234567890", LocalDateTime.now());
        clientFormDto.setEmail("john.doe@examplecom");

        assertThrows(InvalidPhoneNumberException.class, () -> registerNewClientUseCase.register(clientFormDto));
    }

    @Test
    void shouldThrowInvalidInputExceptionForInvalidPhoneNumber() {
        ClientFormDto clientFormDto = new ClientFormDto("John Doe", "550.705.870-99", "john.doe@example.com", "1234567890", LocalDateTime.now());
        clientFormDto.setPhone("1234567890");

        assertThrows(InvalidPhoneNumberException.class, () -> registerNewClientUseCase.register(clientFormDto));
    }

    @Test
    void shouldThrowClientAlreadyExistsExceptionForExistingClient() {
        ClientFormDto clientFormDto = new ClientFormDto("John Doe", "550.705.870-99", "john.doe@example.com", "1234567890", LocalDateTime.now());

        clientFormDto.setCpf("550.705.870-99");

        when(clientRepository.findByCpf(anyString())).thenReturn(Optional.of(new ClientRepositoryDb()));

        assertThrows(ClientAlreadyExistsException.class, () -> registerNewClientUseCase.register(clientFormDto));
    }
}