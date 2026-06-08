package codex.alurafood.pagamento.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("pedidos")
public interface PedidoClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/pedidos/{id}/pago")
    void atualizarPagamento(@PathVariable Long id);

}
