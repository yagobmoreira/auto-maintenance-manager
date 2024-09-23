package com.ultracar.automaintenance.automaintenancemanager.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * Class to configure the OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig implements OpenApiCustomizer {

  @Override
  public void customise(OpenAPI openApi) {
    Info info = new Info()
                    .title("Ultracar Auto Maintenance Manager")
                    .description(
                        "Este projeto apresenta uma API RESTful que permite a criação, "
                            + "listagem e busca de agendamentos de manutenções em veículos. "
                            + "A API oferece endpoints para a criação de agendamentos, "
                            + "listagem de todos os agendamentos e busca de um agendamento "
                            + "específico, além de endpoints para a criação de clientes e busca"
                            + " de todos os clientes cadastrados.")
                    .version("1.0.0");

    openApi.info(info);
  }
}
