package cl.duoc.pedidos360.catalog.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.pedidos360.catalog.dto.DecreaseStockRequest;
import cl.duoc.pedidos360.catalog.dto.ProductRequest;
import cl.duoc.pedidos360.catalog.dto.ProductResponse;
import cl.duoc.pedidos360.catalog.exception.ApiException;
import cl.duoc.pedidos360.catalog.model.Product;
import cl.duoc.pedidos360.catalog.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> list() {
        return repository.findAllByOrderByNameAsc().stream().map(ProductResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse get(Long id) {
        return ProductResponse.from(find(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = new Product(request.name(), request.description(), request.price(), request.stock());
        if (request.active() != null) {
            product.setActive(request.active());
        }
        return ProductResponse.from(repository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = find(id);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        if (request.active() != null) {
            product.setActive(request.active());
        }
        return ProductResponse.from(repository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(find(id));
    }

    /**
     * Regla de negocio: el stock decrece al aceptar un pedido.
     * Es transaccional: si un producto no tiene stock suficiente no se descuenta ninguno.
     */
    @Transactional
    public void decreaseStock(DecreaseStockRequest request) {
        for (DecreaseStockRequest.Item item : request.items()) {
            Product product = find(item.productId());
            if (product.getStock() < item.quantity()) {
                throw new ApiException(HttpStatus.CONFLICT, "Stock insuficiente para %s: disponible %d, solicitado %d"
                        .formatted(product.getName(), product.getStock(), item.quantity()));
            }
            product.setStock(product.getStock() - item.quantity());
        }
    }

    private Product find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El producto " + id + " no existe"));
    }
}
