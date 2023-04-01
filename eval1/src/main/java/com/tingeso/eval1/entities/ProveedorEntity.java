package com.tingeso.eval1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProveedorEntity {

    @Id
    @Column(unique = true, nullable = false)
    private String codigo;

    private String nombre;
    private String categoria;
    private String retencion;
}
