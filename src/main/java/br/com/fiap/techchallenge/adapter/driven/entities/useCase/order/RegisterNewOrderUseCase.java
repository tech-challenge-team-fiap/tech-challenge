package br.com.fiap.techchallenge.adapter.driven.entities.useCase.order;

import br.com.fiap.techchallenge.adapter.driven.entities.Product;
import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderResultFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.client.RegisterNewClientUseCase;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.gateway.OrderGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class RegisterNewOrderUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterNewOrderUseCase.class);
    private final OrderGateway orderGateway;
    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final String Alphabet = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    @Autowired
    public RegisterNewOrderUseCase(OrderGateway orderGateway, OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderGateway = orderGateway;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<OrderResultFormDto> register(final OrderFormDto order) {

        if(order.getProducts().isEmpty()){
            throw new BaseException("No product specified to place the order!");
        }

        ClientRepositoryDb client = null;
        if(order.getClientId() != null){
            client = clientRepository.findById(order.getClientId())
            .orElseThrow(() -> new BaseException("Client with ID " + order.getClientId()+ " does not exists!"));
        }

        return orderGateway.register(order, client);
    }

//    public ResponseEntity<ProductRepositoryDb> register(final ProductFormDto productFormDto){
//        validateQuantity(productFormDto.getQuantity());
//
//        productFormDto.setTypeStatus(TypeStatus.ACTIVE);
//        productFormDto.setDateRegister(new Date());
//
//        return productGateway.register(new Product(productFormDto));
//    }
}
