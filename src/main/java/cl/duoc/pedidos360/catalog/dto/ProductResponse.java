package cl.duoc.pedidos360.catalog.dto;

import java.math.BigDecimal;
import java.time.Instant;

import cl.duoc.pedidos360.catalog.model.Product;

public record ProductResponse(Long id, String name, String description, BigDecimal price, int stock,
                              boolean active, Instant createdAt, Instant updatedAt) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getStock(), product.isActive(), product.getCreatedAt(), product.getUpdatedAt());
    }
}
