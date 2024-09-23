package com.ultracar.automaintenance.automaintenancemanager.service.impl;

import com.ultracar.automaintenance.automaintenancemanager.entity.Agendamento;
import com.ultracar.automaintenance.automaintenancemanager.entity.Cliente;
import com.ultracar.automaintenance.automaintenancemanager.enums.StatusType;
import com.ultracar.automaintenance.automaintenancemanager.repository.AgendamentoRepository;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.AgendamentoNotFoundException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.BusinessException;
import com.ultracar.automaintenance.automaintenancemanager.service.exception.ClienteNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço de Agendamento.
 */
@Service
public class AgendamentoServiceImpl {

  private final AgendamentoRepository agendamentoRepository;
  private ClienteServiceImpl clienteService;

  @Autowired
  public AgendamentoServiceImpl(AgendamentoRepository agendamentoRepository) {
    this.agendamentoRepository = agendamentoRepository;
  }

  @Autowired
  public void setClienteService(ClienteServiceImpl clienteService) {
    this.clienteService = clienteService;
  }

  /**
   * Lista todos os agendamentos.
   *
   * @return uma lista de agendamentos.
   */
  public List<Agendamento> obterAgendamentos() {
    return this.agendamentoRepository.findAll();
  }

  /**
   * /Procura um agendamento pelo ‘ID’.
   *
   * @param agendamentoId 'ID' do agendamento
   * @return Objeto do tipo Agendamento
   * @throws AgendamentoNotFoundException Caso não exista um agendamento com o 'ID' indicado
   */
  public Agendamento obterAgendamentoPeloId(Long agendamentoId)
      throws AgendamentoNotFoundException {
    return this.agendamentoRepository.findById(agendamentoId).orElseThrow(
        AgendamentoNotFoundException::new);
  }

  /**
   * Cria um agendamento, e víncula ao cliente.
   *
   * @param novoAgendamento novo agendamento
   * @param clienteId       'ID' do cliente
   * @return Objeto do tipo Agendamento
   * @throws ClienteNotFoundException Caso não exista um cliente com o 'ID' indicado
   * @throws BusinessException        Caso a data seja inválida
   */
  @Transactional
  public Agendamento criarAgendamento(Agendamento novoAgendamento, Long clienteId)
      throws ClienteNotFoundException, BusinessException {
    Cliente cliente = this.clienteService.obterClientePeloId(clienteId);

    //Verificação da data de agendamento
    verificarDataAgendamento(novoAgendamento.getDataAgendamento());

    novoAgendamento.setCliente(cliente);
    cliente.setAgendamento(novoAgendamento);

    return this.agendamentoRepository.save(novoAgendamento);
  }

  private void verificarDataAgendamento(LocalDateTime dataAgendamento) throws BusinessException {
    LocalDateTime dataLimite = LocalDateTime.now().plusMonths(6);

    //Agendamentos são válidos apenas para datas até 6 meses após a data atual.
    if (dataAgendamento.isAfter(dataLimite)) {
      throw new BusinessException(
          "A data de agendamento não pode ser superior à 6 meses da data atual.");
    }

    //O horário permitido para agendamento é das 08h00 às 17h59
    if (dataAgendamento.getHour() < 8 || dataAgendamento.getHour() > 18) {
      throw new BusinessException("O horário de agendamento deve ser entre 8h e 18h.");
    }
  }

  /**
   * Finalizar serviço agendamento.
   *
   * @param agendamentoId 'ID' do agendamento
   * @return Objeto do tipo Agendamento
   * @throws AgendamentoNotFoundException Caso não exista um agendamento com o 'ID' indicado
   * @throws BusinessException            Caso o agendamento já esteja sido concluído ou cancelado
   *                                      anteriormente.
   */
  @Transactional
  public Agendamento finalizarServico(Long agendamentoId)
      throws AgendamentoNotFoundException, BusinessException {
    Agendamento agendamento = this.obterAgendamentoPeloId(agendamentoId);

    if (!agendamento.getStatus().equals(StatusType.PENDENTE)) {
      throw new BusinessException(
          "Erro ao finalizar o agendamento. Agendamento já realizado ou cancelado anteriormente");
    }

    agendamento.setStatus(StatusType.REALIZADO);

    return agendamentoRepository.save(agendamento);
  }

  /**
   * Cancelar servico agendamento.
   *
   * @param agendamentoId 'ID' do agendamento
   * @return Objeto do tipo Agendamento
   * @throws AgendamentoNotFoundException Caso não exista um agendamento com o 'ID' indicado
   * @throws BusinessException            Caso o agendamento já esteja sido concluído ou cancelado
   *                                      anteriormente. Agendamento deve ser cancelado em até 24h
   *                                      de antecedência.
   */
  @Transactional
  public Agendamento cancelarServico(Long agendamentoId)
      throws AgendamentoNotFoundException, BusinessException {
    Agendamento agendamento = this.obterAgendamentoPeloId(agendamentoId);

    if (!agendamento.getStatus().equals(StatusType.PENDENTE)) {
      throw new BusinessException(
          "Erro ao cancelar o agendamento. Agendamento já realizado ou cancelado anteriormente");
    }

    LocalDateTime dataLimite = agendamento.getDataAgendamento().minusHours(24L);

    if (LocalDateTime.now().isAfter(dataLimite)) {
      throw new BusinessException(
          "Erro ao cancelar o agendamento. "
              + "Cancelamento deve ser feito com no mínimo 24 horas de antecedência.");
    }

    agendamento.setStatus(StatusType.CANCELADO);

    return agendamentoRepository.save(agendamento);
  }


  /**
   * Listar agendamentos de um cliente entre duas datas.
   *
   * @param clienteId   'ID' do cliente
   * @param dataInicial Data inicial
   * @param dataFinal   Data final
   * @return uma lista de agendamentos.
   */
  public List<Agendamento> listarAgendamentoEntreDatas(Long clienteId, LocalDateTime dataInicial,
      LocalDateTime dataFinal) {
    return this.agendamentoRepository.obterAgendamentosClienteEntreDatas(clienteId, dataInicial,
        dataFinal);
  }

}
