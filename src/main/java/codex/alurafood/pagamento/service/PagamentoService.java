package codex.alurafood.pagamento.service;

import codex.alurafood.pagamento.dto.request.PagamentoRequest;
import codex.alurafood.pagamento.dto.response.PagamentoResponse;
import codex.alurafood.pagamento.model.Pagamento;
import codex.alurafood.pagamento.model.Status;
import codex.alurafood.pagamento.repository.PagamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Page<PagamentoResponse> listarPagamentos(Pageable paginacao) {
        return pagamentoRepository.findAll(paginacao)
                .map( p -> new  PagamentoResponse(p.getValor(), p.getStatus()));
    }

    public PagamentoResponse buscarPagamentoPorId(Long id) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);
        return pagamento.map(p -> new PagamentoResponse(p.getValor(), p.getStatus()))
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    @Transactional
    public PagamentoResponse criarPagamento(PagamentoRequest pagamentoRequest) {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(pagamentoRequest.valor());
        pagamento.setNome(pagamentoRequest.nome());
        pagamento.setNumero(pagamentoRequest.numero());
        pagamento.setExpiracao(pagamentoRequest.expiracao());
        pagamento.setStatus(pagamentoRequest.status());
        pagamento.setPedidoId(pagamentoRequest.pedidoId());
        pagamento.setFormaDePagamento(pagamentoRequest.formaDePagamento());

        pagamentoRepository.save(pagamento);
        return ResponseEntity.status(201)
                .body(new PagamentoResponse(pagamento.getValor(),
                        pagamento.getStatus()))
                .getBody();

    }

    @Transactional
    public PagamentoResponse atualizarStatusPagamento(Long id, PagamentoRequest request) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        if (pagamento.getStatus() == Status.APROVADO) {
            throw new RuntimeException("Não é possível alterar o status de um pagamento aprovado");
        }
        else if (pagamento.getStatus() == Status.CRIADO) {
            pagamento.setValor(request.valor());
            pagamento.setNome(request.nome());
            pagamento.setNumero(request.numero());
            pagamento.setExpiracao(request.expiracao());
            pagamento.setStatus(request.status());
            pagamento.setPedidoId(request.pedidoId());
            pagamento.setFormaDePagamento(request.formaDePagamento());

            pagamentoRepository.save(pagamento);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new PagamentoResponse(pagamento.getValor(),
                            pagamento.getStatus()))
                    .getBody();
        }

        else {
            throw new RuntimeException("Pagamento foi reprovado e não pode ser alterado");
        }
    }

    @Transactional
    public void deletarPagamento(Long id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pagamento não encontrado");
        }
    }
}
