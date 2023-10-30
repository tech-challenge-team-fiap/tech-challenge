package br.com.fiap.techchallenge.infrastructure.repository;

import br.com.fiap.techchallenge.adapter.driven.entities.Client;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.common.type.StringRepresentationUUIDType;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "CLIENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class ClientRepositoryDb {

    @Id
    @Builder.Default
    @Type(StringRepresentationUUIDType.class)
    @Column(name = "ID")
    @NotNull
    @EqualsAndHashCode.Include
    private UUID id = nextId();

    @Column(name = "NAME")
    private String name;

    @Column(name = "CPF")
    @EqualsAndHashCode.Include
    @NaturalId
    private String cpf;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DATE_REGISTER")
    @CreatedDate
    private LocalDateTime dateRegister;

    @NotNull
    private static UUID nextId() {
        return UlidCreator.getMonotonicUlid().toUuid();
    }

    public ClientRepositoryDb(ClientFormDto clientFormDto) {
        this.name = clientFormDto.getName();
        this.cpf = clientFormDto.getCpf();
        this.email = clientFormDto.getEmail();
        this.phone = clientFormDto.getPhone();
        this.dateRegister = clientFormDto.getDateRegister();
    }

    public void updateFromDto(ClientFormDto clientFormDto) {
        // Assuming Client class has these setters
        this.setName(clientFormDto.getName());
        this.setEmail(clientFormDto.getEmail());
        this.setPhone(clientFormDto.getPhone());
        this.setCpf(clientFormDto.getCpf());
        // Add more fields as needed
    }

    public ClientRepositoryDb(Client client) {
        this.name = client.getName();
        this.cpf = client.getCpf();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.dateRegister = client.getDateRegister();
    }
}
