package br.com.fiap.techchallenge.db.dto.order;

import br.com.fiap.techchallenge.common.enums.PaymentsType;

import java.util.UUID;

import br.com.fiap.techchallenge.db.dto.product.ProductOrderFormDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFormDto {


    private UUID clientId;

    private PaymentsType paymentsType;

    private List<ProductOrderFormDto> products;
}