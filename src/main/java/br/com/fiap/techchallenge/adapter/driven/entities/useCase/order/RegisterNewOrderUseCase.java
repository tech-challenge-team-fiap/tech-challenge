package br.com.fiap.techchallenge.adapter.driven.entities.useCase.order;

import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderResultFormDto;
import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import br.com.fiap.techchallenge.common.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.common.exception.order.InvalidOrderProductException;
import br.com.fiap.techchallenge.infrastructure.gateway.OrderGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewOrderUseCase extends AbstractOrderUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterNewOrderUseCase.class);
    private final OrderGateway orderGateway;

    private final ClientRepository clientRepository;

    @Autowired
    public RegisterNewOrderUseCase(OrderGateway orderGateway, ClientRepository clientRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        super(productRepository, orderRepository);
        this.orderGateway = orderGateway;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<OrderResultFormDto> register(final OrderFormDto orderFormDto) throws InvalidProcessException {
        validateProduct(orderFormDto.getProducts());

        ClientRepositoryDb client = null;
        if(orderFormDto.getClientId() != null){
            client = clientRepository.findById(orderFormDto.getClientId())
            .orElseThrow(() -> new ClientNotFoundException(orderFormDto.getClientId().toString()));
        }

        return orderGateway.register(orderFormDto, client);
    }
}
