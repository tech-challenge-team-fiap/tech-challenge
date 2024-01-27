package br.com.fiap.techchallenge.useCase.order;

import br.com.fiap.techchallenge.application.dto.order.OrderFormDto;
import br.com.fiap.techchallenge.application.dto.order.OrderResultFormDto;
import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;
import br.com.fiap.techchallenge.domain.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.domain.interfaces.abstracts.AbstractOrderUseCase;
import br.com.fiap.techchallenge.external.infrastructure.gateway.OrderGatewayImpl;
import br.com.fiap.techchallenge.external.infrastructure.repositories.ClientRepository;
import br.com.fiap.techchallenge.external.infrastructure.repositories.OrderRepository;
import br.com.fiap.techchallenge.external.infrastructure.repositories.ProductRepository;
import br.com.fiap.techchallenge.external.infrastructure.entities.ClientDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewOrderUseCase extends AbstractOrderUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterNewOrderUseCase.class);
    private final OrderGatewayImpl orderGatewayImpl;

    private final ClientRepository clientRepository;

    @Autowired
    public RegisterNewOrderUseCase(OrderGatewayImpl orderGatewayImpl, ClientRepository clientRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        super(productRepository, orderRepository);
        this.orderGatewayImpl = orderGatewayImpl;
        this.clientRepository = clientRepository;
    }

    public OrderResultFormDto register(final OrderFormDto orderFormDto) throws InvalidProcessException {
        validateProduct(orderFormDto.getProducts());

        ClientDB client = null;
        if(orderFormDto.getClientId() != null){
            client = clientRepository.findById(orderFormDto.getClientId())
            .orElseThrow(() -> new ClientNotFoundException(orderFormDto.getClientId().toString()));
        }

        return orderGatewayImpl.register(orderFormDto, client);
    }
}
