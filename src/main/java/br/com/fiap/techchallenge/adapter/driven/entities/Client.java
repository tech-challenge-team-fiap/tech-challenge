package br.com.fiap.techchallenge.adapter.driven.entities;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
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

    public Client(ClientFormDto clientFormDto) {
        this.name = clientFormDto.getName();
        this.cpf = clientFormDto.getCpf();
        this.email = clientFormDto.getEmail();
        this.phone = clientFormDto.getCpf();
        this.dateRegister = clientFormDto.getDateRegister();
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
