package codex.alurafood.pagamento.controller;

import codex.alurafood.pagamento.dto.request.PagamentoRequest;
import codex.alurafood.pagamento.dto.response.PagamentoResponse;
import codex.alurafood.pagamento.model.Pagamento;
import codex.alurafood.pagamento.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pagamento")
public class PagamentoController {

    private PagamentoService service;
    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    public Page<PagamentoResponse> obterPagamentos(@PageableDefault(size = 10) Pageable paginacao) {
        return service.listarPagamentos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> obterPagamento(@PathVariable @Valid Long id) {
        PagamentoResponse response = service.buscarPagamentoPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PagamentoResponse> cadastrarPagamento(@RequestBody @Valid PagamentoRequest request, UriComponentsBuilder builder) {
        PagamentoResponse response = service.criarPagamento(request);
        UriComponents uriComponents = builder.path("/pagamento/{id}").buildAndExpand(response.id());
        return ResponseEntity.created(uriComponents.toUri()).body(response);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<PagamentoResponse> atualizarPagamento(@PathVariable @Valid Long id, @RequestBody @Valid PagamentoRequest request) {
        PagamentoResponse atualizado = service.atualizarStatusPagamento(id, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<PagamentoResponse> deletarPagamento(@PathVariable @Valid Long id) {
        service.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
