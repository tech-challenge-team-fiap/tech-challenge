package br.com.fiap.techchallenge.adapter.driven.entities.useCase.order;

import br.com.fiap.techchallenge.adapter.driven.entities.Order;
import br.com.fiap.techchallenge.adapter.driven.entities.Product;
import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderResultFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductOrderFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.client.RegisterNewClientUseCase;
import br.com.fiap.techchallenge.common.enums.PaymentsType;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.gateway.OrderGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import java.math.BigDecimal;
import java.util.List;
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

    @Autowired
    public RegisterNewOrderUseCase(OrderGateway orderGateway, OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderGateway = orderGateway;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<OrderResultFormDto> register(final OrderFormDto orderFormDto) {

        if(orderFormDto.getProducts().isEmpty()){
            throw new BaseException("No product specified to place the order!");
        }

        ClientRepositoryDb client = null;
        if(orderFormDto.getClientId() != null){
            client = clientRepository.findById(orderFormDto.getClientId())
            .orElseThrow(() -> new BaseException("Client with ID " + orderFormDto.getClientId()+ " does not exists!"));
        }

        return orderGateway.register(orderFormDto, client);
    }
}
