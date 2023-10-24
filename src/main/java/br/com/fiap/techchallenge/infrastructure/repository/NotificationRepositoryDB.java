package br.com.fiap.techchallenge.infrastructure.repository;

import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "NOTIFICATIONS")
@Getter
@Setter
public class NotificationRepositoryDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String numberOrder;

    private String message;

    @Column(name="statusOrder", length=250)
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;

    public NotificationRepositoryDB(String numberOrder, String message, StatusOrder status, Date dateRegister){
        this.numberOrder = numberOrder;
        this.message = message;
        this.statusOrder = status;
        this.dateRegister = dateRegister;
    }
}
