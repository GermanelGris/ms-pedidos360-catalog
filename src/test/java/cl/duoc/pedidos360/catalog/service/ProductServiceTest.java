package cl.duoc.pedidos360.catalog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import cl.duoc.pedidos360.catalog.dto.DecreaseStockRequest;
import cl.duoc.pedidos360.catalog.exception.ApiException;
import cl.duoc.pedidos360.catalog.model.Product;
import cl.duoc.pedidos360.catalog.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void descuentaStockCuandoHayDisponible() {
        Product pizza = new Product("Pizza", null, new BigDecimal("8990"), 10);
        given(repository.findById(1L)).willReturn(Optional.of(pizza));

        service.decreaseStock(new DecreaseStockRequest(List.of(new DecreaseStockRequest.Item(1L, 3))));

        assertThat(pizza.getStock()).isEqualTo(7);
    }

    @Test
    void stockInsuficienteRespondeConflicto() {
        Product pizza = new Product("Pizza", null, new BigDecimal("8990"), 2);
        given(repository.findById(1L)).willReturn(Optional.of(pizza));

        assertThatThrownBy(() -> service.decreaseStock(
                new DecreaseStockRequest(List.of(new DecreaseStockRequest.Item(1L, 5)))))
                .isInstanceOf(ApiException.class)
                .extracting("status").isEqualTo(HttpStatus.CONFLICT);
        assertThat(pizza.getStock()).isEqualTo(2);
    }

    @Test
    void productoInexistenteRespondeNotFound() {
        given(repository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(99L))
                .isInstanceOf(ApiException.class)
                .extracting("status").isEqualTo(HttpStatus.NOT_FOUND);
    }
}
