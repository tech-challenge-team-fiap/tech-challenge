package br.com.fiap.techchallenge.db.datasource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("br.com.fiap.techchallenge.infrastructure.repository")
public class JPARepositoriesAutoConfiguration {

}
