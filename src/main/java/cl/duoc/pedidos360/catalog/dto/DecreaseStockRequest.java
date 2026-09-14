package cl.duoc.pedidos360.catalog.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DecreaseStockRequest(@NotEmpty List<@Valid Item> items) {

    public record Item(@NotNull Long productId, @Positive int quantity) {
    }
}
