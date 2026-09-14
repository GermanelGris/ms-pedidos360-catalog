package cl.duoc.pedidos360.catalog.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.pedidos360.catalog.model.Product;
import cl.duoc.pedidos360.catalog.repository.ProductRepository;

/** Carga productos de ejemplo la primera vez que la base está vacía. */
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedProducts(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Product("Pizza Margarita", "Pizza familiar de tomate y mozzarella", new BigDecimal("8990"), 50),
                        new Product("Hamburguesa Doble", "Doble carne, queso cheddar y tocino", new BigDecimal("7490"), 40),
                        new Product("Papas Fritas", "Porción grande", new BigDecimal("3290"), 60),
                        new Product("Bebida 1.5L", "Bebida gaseosa", new BigDecimal("2490"), 100)));
            }
        };
    }
}
