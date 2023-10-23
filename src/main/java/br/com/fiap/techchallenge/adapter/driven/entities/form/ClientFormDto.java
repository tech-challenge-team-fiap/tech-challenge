package br.com.fiap.techchallenge.adapter.driven.entities.form;

import java.time.Instant;
import java.time.LocalDateTime;

public class ClientFormDto {

    private final String name;
    private final String cpf;
    private final String email;
    private final String phone;
    private final LocalDateTime dateRegister;

    public ClientFormDto(String name, String cpf, String email, String phone, LocalDateTime dateRegister) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.dateRegister = dateRegister;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }


    public void setCpf(String validCpf) {
    }

    public void setEmail(String validEmail) {
    }

    public void setPhoneNumber(String s) {
    }
}
