package br.com.fiap.techchallenge.external.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("br.com.fiap.techchallenge.infrastructure.repository")
public class JPARepositoriesAutoConfiguration {

}
