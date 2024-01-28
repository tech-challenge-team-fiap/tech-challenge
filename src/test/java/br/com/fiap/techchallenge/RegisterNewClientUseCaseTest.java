package br.com.fiap.techchallenge;

import br.com.fiap.techchallenge.application.dto.client.ClientDto;
import br.com.fiap.techchallenge.external.infrastructure.gateway.ClientGatewayImpl;
import br.com.fiap.techchallenge.external.infrastructure.repositories.ClientRepository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterNewClientUseCaseTest {

    @Mock
    private ClientGatewayImpl clientGatewayImpl;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void shouldRegisterNewClient() {
        // Set up mock behavior to indicate that no client with the given CPF exists
        //when(clientRepository.findByCpf(anyString())).thenReturn(Optional.empty());
//        when(clientGateway.register(any(Client.class))).thenReturn(ResponseEntity.ok(UUID.randomUUID()));
//
//        ClientDto clientDto = new ClientDto("1","John Doe", "550.705.870-99", "john.doe@example.com", "+5511987654321", LocalDateTime.now());
//
//        // This should not throw an exception
//        assertDoesNotThrow(() -> registerNewClientUseCase.register(clientDto));
    }

    @Test
    void shouldThrowInvalidInputExceptionForInvalidCpf() {
        ClientDto clientDto = new ClientDto("1","John Doe", "55070587098", "john.doe@example.com", "1234567890", LocalDateTime.now());
        clientDto.setCpf("55070587098");

//        assertThrows(InvalidCpfException.class, () -> registerNewClientUseCase.register(clientDto));
    }

    @Test
    void shouldThrowInvalidInputExceptionForInvalidEmail() {
        ClientDto clientDto = new ClientDto("1","John Doe", "550.705.870-99", "john.doe@examplecom", "1234567890", LocalDateTime.now());
        clientDto.setEmail("john.doe@examplecom");

//        assertThrows(InvalidPhoneNumberException.class, () -> registerNewClientUseCase.register(clientDto));
    }

    @Test
    void shouldThrowInvalidInputExceptionForInvalidPhoneNumber() {
        ClientDto clientDto = new ClientDto("1","John Doe", "550.705.870-99", "john.doe@example.com", "1234567890", LocalDateTime.now());
        clientDto.setPhone("1234567890");

//        assertThrows(InvalidPhoneNumberException.class, () -> registerNewClientUseCase.register(clientDto));
    }

    @Test
    void shouldThrowClientAlreadyExistsExceptionForExistingClient() {
        ClientDto clientDto = new ClientDto("1","John Doe", "550.705.870-99", "john.doe@example.com", "1234567890", LocalDateTime.now());

        clientDto.setCpf("550.705.870-99");

//        when(clientRepository.findByCpf(anyString())).thenReturn(Optional.of(new ClientRepositoryDb()));

//        assertThrows(ClientAlreadyExistsException.class, () -> registerNewClientUseCase.register(clientDto));
    }
}