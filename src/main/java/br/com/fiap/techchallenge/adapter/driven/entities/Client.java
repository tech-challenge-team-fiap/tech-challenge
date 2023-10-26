package br.com.fiap.techchallenge.adapter.driven.entities;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import java.time.Instant;
import java.time.LocalDateTime;


public class Client {

    private String name;
    private String cpf;
    private String email;
    private String phone;
    private LocalDateTime dateRegister;

    public Client() {}

    public Client(ClientFormDto clienteFormDto) {
        this.name = clienteFormDto.getName();
        this.cpf = clienteFormDto.getCpf();
        this.email = clienteFormDto.getEmail();
        this.phone = clienteFormDto.getCpf();
        this.dateRegister = clienteFormDto.getDateRegister();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
    }
}
