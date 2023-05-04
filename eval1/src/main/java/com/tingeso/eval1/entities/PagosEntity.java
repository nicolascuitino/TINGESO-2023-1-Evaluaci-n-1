package com.tingeso.eval1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    private String quincena;
    private String codigo;
    private String nombre;
    private String klsLeche;
    private String nroDiasLeche;
    private String promedioKlsLeche;
    private String variacionLeche;
    private String grasa;
    private String variacionGrasa;
    private String solidos;
    private String variacionSolidos;
    private String pagoXLeche;
    private String pagoXGrasa;
    private String pagoXST;
    private String boniFrecuencia;
    private String dctoLeche;
    private String dctoGrasa;
    private String dctoST;
    private String pagoTotal;
    private String retencion;
    private String pagoFinal;
}
