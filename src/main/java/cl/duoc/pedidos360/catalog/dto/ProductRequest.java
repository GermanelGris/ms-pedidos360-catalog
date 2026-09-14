package cl.duoc.pedidos360.catalog.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank(message = "es obligatorio") @Size(max = 150) String name,
        @Size(max = 500) String description,
        @NotNull(message = "es obligatorio") @PositiveOrZero(message = "no puede ser negativo") BigDecimal price,
        @NotNull(message = "es obligatorio") @PositiveOrZero(message = "no puede ser negativo") Integer stock,
        Boolean active) {
}
