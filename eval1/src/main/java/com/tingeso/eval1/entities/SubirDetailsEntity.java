package com.tingeso.eval1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubirDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    private String proveedor;
    private String grasa;
    private String solido;
}
