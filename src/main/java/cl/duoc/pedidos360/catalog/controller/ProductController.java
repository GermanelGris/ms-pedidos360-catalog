package cl.duoc.pedidos360.catalog.controller;

import java.util.List;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.pedidos360.catalog.dto.DecreaseStockRequest;
import cl.duoc.pedidos360.catalog.dto.ProductRequest;
import cl.duoc.pedidos360.catalog.dto.ProductResponse;
import cl.duoc.pedidos360.catalog.service.ProductService;

@RestController
@RequestMapping("/api/catalog/products")
@Tag(name = "Catálogo")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar productos")
    public List<ProductResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalle de un producto")
    public ProductResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear producto")
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto, precio y stock")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar producto")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    /** Uso interno: lo invoca ms-pedidos360-orders al aceptar un pedido. El BFF no lo expone. */
    @PostMapping("/decrease-stock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Descontar stock (interno, usado por orders al aceptar un pedido)")
    public void decreaseStock(@Valid @RequestBody DecreaseStockRequest request) {
        service.decreaseStock(request);
    }
}
