package br.com.fiap.techchallenge.common.utils;

import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import java.util.HashMap;
import java.util.Map;

public class ProblemAware {

    public static Map<String, String> problemOf(InvalidProcessException ex) {
        Map<String, String> details = new HashMap<>();

        details.put("tittle", ex.getTittle());
        details.put("message", ex.getMessage());

        return details;
    }

}
