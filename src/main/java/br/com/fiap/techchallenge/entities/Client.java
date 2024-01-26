package br.com.fiap.techchallenge.entities;

import br.com.fiap.techchallenge.db.dto.client.ClientDto;
import br.com.fiap.techchallenge.db.dto.client.ClientFormDto;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    private String name;
    private String cpf;
    private String email;
    private String phone;
    private LocalDateTime dateRegister;

    public Client() {}

    public Client(ClientDto clientDto) {
        this.name = clientDto.getName();
        this.cpf = clientDto.getCpf();
        this.email = clientDto.getEmail();
        this.phone = clientDto.getCpf();
        this.dateRegister = clientDto.getDateRegister();
    }

    public Client(ClientFormDto clientDto) {
        this.name = clientDto.getName();
        this.cpf = clientDto.getCpf();
        this.email = clientDto.getEmail();
        this.phone = clientDto.getCpf();
        this.dateRegister = clientDto.getDateRegister();
    }

    public ClientRepositoryDb build(){
        return ClientRepositoryDb.builder()
                                 .name(getName())
                                 .cpf(getCpf())
                                 .email(getEmail())
                                 .phone(getPhone())
                                 .dateRegister(getDateRegister())
                                 .build();
    }
}
