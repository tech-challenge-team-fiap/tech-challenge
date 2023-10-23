package br.com.fiap.techchallenge.common.enums;

import lombok.Getter;
import lombok.Setter;

public enum TypeStatus {

    ACTIVE(1),

    INACTIVE(2);

    @Getter
    @Setter
    private java.lang.Integer code;
    TypeStatus(java.lang.Integer code){
        this.code = code;
    }
}