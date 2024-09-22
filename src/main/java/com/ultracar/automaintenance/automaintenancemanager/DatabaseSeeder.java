package com.ultracar.automaintenance.automaintenancemanager;

import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.entity.Endereco;
import com.ultracar.automaintenance.automaintenancemanager.entity.Veiculo;
import com.ultracar.automaintenance.automaintenancemanager.repository.ClienteRepository;
import com.ultracar.automaintenance.automaintenancemanager.repository.EnderecoRepository;
import com.ultracar.automaintenance.automaintenancemanager.repository.VeiculoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The type Database seeder.
 */
@Component
public class DatabaseSeeder implements CommandLineRunner {

  private final ClienteRepository clienteRepository;
  private final VeiculoRepository veiculoRepository;
  private final EnderecoRepository enderecoRepository;

  /**
   * Instantiates a new Database seeder.
   *
   * @param clienteRepository  the cliente repository
   * @param veiculoRepository  the veiculo repository
   * @param enderecoRepository the endereco repository
   */
  @Autowired
  public DatabaseSeeder(ClienteRepository clienteRepository, VeiculoRepository veiculoRepository,
      EnderecoRepository enderecoRepository) {
    this.clienteRepository = clienteRepository;
    this.veiculoRepository = veiculoRepository;
    this.enderecoRepository = enderecoRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    seedClientes();
  }

  private void seedClientes() {
    List<Veiculo> veiculos = new ArrayList<>();
    veiculos.add(new Veiculo("MZZ2865", "Tucson", "Hyundai", 2005));
    veiculos.add(new Veiculo("MZR1289", "Frontier", "Nissan", 2009));
    veiculos.add(new Veiculo("LZE2749", "456 GT", "Ferrari", 1993));

    List<Cliente> clientes = new ArrayList<>();
    clientes.add(new Cliente("Valentina Luana Castro", "71534439919"));
    clientes.add(new Cliente("Murilo Arthur Brito", "47511069290"));
    clientes.add(new Cliente("Luís Calebe Cauê Nogueira", "40576520861"));

    List<Endereco> enderecos = new ArrayList<>();
    enderecos.add(
        new Endereco("60331114", "Rua Raniele 176", "", "Barra do Ceará", "Fortaleza", "CE"));
    enderecos.add(
        new Endereco("29206025", "Rua Alameda Formosa 597", "", "Enseada Azul", "Guarapari",
            "ES"));
    enderecos.add(
        new Endereco("18618290", "Rua Antonio Putti 152", "", "Jardim Botucatu", "Botucatu",
            "SP"));

    clienteRepository.saveAll(clientes);
    
    clientes.get(0).setVeiculo(veiculos.get(0));
    clientes.get(1).setVeiculo(veiculos.get(1));
    clientes.get(2).setVeiculo(veiculos.get(2));

    veiculos.get(0).setCliente(clientes.get(0));
    veiculos.get(1).setCliente(clientes.get(1));
    veiculos.get(2).setCliente(clientes.get(2));

    veiculoRepository.saveAll(veiculos);

    enderecos.get(0).setCliente(clientes.get(0));
    enderecos.get(1).setCliente(clientes.get(1));
    enderecos.get(2).setCliente(clientes.get(2));

    enderecoRepository.saveAll(enderecos);

    clientes.get(0).setEndereco(enderecos.get(0));
    clientes.get(1).setEndereco(enderecos.get(1));
    clientes.get(2).setEndereco(enderecos.get(2));

    clienteRepository.saveAll(clientes);
  }

}
