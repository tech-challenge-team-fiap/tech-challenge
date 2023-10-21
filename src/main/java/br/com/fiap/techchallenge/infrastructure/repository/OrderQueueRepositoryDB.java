package br.com.fiap.techchallenge.infrastructure.repository;

import br.com.fiap.techchallenge.common.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "ORDER_QUEUE")
@Getter
@Setter
public class OrderQueueRepositoryDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String numberOrder;

    @Column(name="statusOrder", length=250)
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;

    public OrderQueueRepositoryDB(String numberOrder, StatusOrder status, Date dateRegister){
        this.numberOrder = numberOrder;
        this.statusOrder = status;
        this.dateRegister = dateRegister;
    }
}
