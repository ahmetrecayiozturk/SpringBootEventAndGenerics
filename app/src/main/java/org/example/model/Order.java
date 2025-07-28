package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "OrdersTable")
public class Order {
    @Id
    private Long id;
    private String productName;
    private Integer quantity;
    private Double price;
}
