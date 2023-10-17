package br.com.fiap.techchallenge.infrastructure.repository;


import br.com.fiap.techchallenge.adapter.driven.entities.Client;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import jakarta.persistence.*;

@Entity
@Table(name = "CLIENT")
public class ClientRepositoryDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String cpf;
    private String email;
    private String phone;
    private String dateRegister;

    public ClientRepositoryDb() {

    }

    public ClientRepositoryDb(int id, String name, String cpf, String email, String phone, String dateRegister) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.dateRegister = dateRegister;
    }

    public ClientRepositoryDb(ClientFormDto clientFormDto) {
        this.name = clientFormDto.getName();
        this.cpf = clientFormDto.getCpf();
        this.email = clientFormDto.getEmail();
        this.phone = clientFormDto.getPhone();
        this.dateRegister = clientFormDto.getDateRegister();
    }

    public ClientRepositoryDb(Client client) {
        this.name = client.getName();
        this.cpf = client.getCpf();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.dateRegister = client.getDateRegister();
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

    public String getDateRegister() {
        return dateRegister;
    }

    public int getId() {
        return id;
    }
}
