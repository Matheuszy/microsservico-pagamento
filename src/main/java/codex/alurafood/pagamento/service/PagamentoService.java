package codex.alurafood.pagamento.service;

import codex.alurafood.pagamento.dto.request.PagamentoCartaoRequest;
import codex.alurafood.pagamento.dto.request.PagamentoRequest;
import codex.alurafood.pagamento.dto.response.PagamentoResponse;
import codex.alurafood.pagamento.model.*;
import codex.alurafood.pagamento.repository.PagamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Page<PagamentoResponse> listarPagamentos(Pageable paginacao) {
        return pagamentoRepository.findAll(paginacao)
                .map(p -> new PagamentoResponse(p.getId(), p.getValor(), p.getStatus(), getTipo(p)));
    }

    public PagamentoResponse buscarPagamentoPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        return new PagamentoResponse(pagamento.getId(), pagamento.getValor(), pagamento.getStatus(), getTipo(pagamento));
    }

    @Transactional
    public PagamentoResponse criarPagamento(PagamentoRequest request) {
        Pagamento pagamento;

        if (request instanceof PagamentoCartaoRequest cartaoRequest) {
            PagamentoCartao cartao = new PagamentoCartao();
            cartao.setNome(cartaoRequest.nome());
            cartao.setNumero(cartaoRequest.numero());
            cartao.setCodigo(cartaoRequest.codigo());
            cartao.setExpiracao(cartaoRequest.expiracao());
            cartao.setFormaDePagamento(cartaoRequest.formaDePagamento());
            pagamento = cartao;
        } else {
            PagamentoPix pix = new PagamentoPix();
            pix.setFormaDePagamento(TipoPagamento.PIX);
            pagamento = pix;
        }

        pagamento.setValor(request.valor());
        pagamento.setPedidoId(request.pedidoId());
        pagamento.setStatus(Status.CRIADO);

        pagamentoRepository.save(pagamento);
        return new PagamentoResponse(pagamento.getId(), pagamento.getValor(), pagamento.getStatus(), getTipo(pagamento));
    }

    @Transactional
    public PagamentoResponse atualizarStatusPagamento(Long id, PagamentoRequest request) {
        Pagamento pagamentoAntigo = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        if (pagamentoAntigo.getStatus() == Status.APROVADO) {
            throw new RuntimeException("Não é possível alterar o status de um pagamento aprovado");
        }
        pagamentoRepository.delete(pagamentoAntigo);
        return criarPagamento(request);
    }

    @Transactional
    public void deletarPagamento(Long id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pagamento não encontrado");
        }
    }

    private TipoPagamento getTipo(Pagamento pagamento) {
        if (pagamento instanceof PagamentoCartao cartao) {
            return cartao.getFormaDePagamento();
        }
        return TipoPagamento.PIX;
    }
}