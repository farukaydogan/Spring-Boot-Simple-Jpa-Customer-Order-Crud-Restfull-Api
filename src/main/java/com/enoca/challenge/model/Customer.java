package com.enoca.challenge.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;



@Data
@Entity
public class Customer {

    @Id
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    // Swaggerin bodyde bunu gormezden gelmesi icin

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    // Swaggerin bodyde bunu gormezden gelmesi icin
    @JsonBackReference
    //Sonsuz donguden kacinmak icin
    private List<Order> orders;

    // Getter, Setter ve Constructor metodları
}
