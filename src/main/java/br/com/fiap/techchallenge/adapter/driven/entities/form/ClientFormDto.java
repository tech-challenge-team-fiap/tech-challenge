package br.com.fiap.techchallenge.adapter.driven.entities.form;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientFormDto {

    private String name;
    private String cpf;
    private String email;
    private String phone;
    private LocalDateTime dateRegister;
}
