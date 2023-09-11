
package com.enoca.challenge.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;



@Data
@Entity
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    // Swaggerin bodyde bunu gormezden gelmesi icin
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private Double totalPrice;



    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    // sosnuz donguyu engellemek icin
    private Customer customer;

    //    Otomatik calisip createDate i dolduracaktir
    @PrePersist
    protected void onCreate() {
        this.createDate = new Date();
    }
}
