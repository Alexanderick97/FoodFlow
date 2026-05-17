package com.CodeChefs.ReporteMS.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoReporte;
    private String fechaReporte;
    private Integer totalPedidos;
    private double totalVentas;
    private Integer pedidosEntregados;
    private Integer pedidosCancelados;

    public Reporte(){

    }

    public Reporte (String tipoReporte, String fechaReporte, Integer totalPedidos, double totalVentas, Integer pedidosEntregados, Integer pedidosCancelados){
        this.tipoReporte = tipoReporte;
        this.fechaReporte = fechaReporte;
        this.totalPedidos = totalPedidos;
        this.totalVentas = totalVentas;
        this.pedidosEntregados = pedidosEntregados;
        this.pedidosCancelados = pedidosCancelados;
    }

    public Long getId (){
        return id;
    }

    public String getTipoReporte (){
        return tipoReporte;
    }

    public void setTipoReporte (String tipoReporte){
        this.tipoReporte = tipoReporte;
    }

    public String getFechaReporte (){
        return fechaReporte;
    }

    public void setFechaReporte (String fechaReporte){
        this.fechaReporte = fechaReporte;
    }

    public Integer getTotalPedidos (){
        return totalPedidos;
    }

    public void setTotalPedidos (Integer totalPedidos){
        this.totalPedidos = totalPedidos;
    }

    public double getTotalVentas (){
        return totalVentas;
    }

    public void setTotalVentas (double totalVentas){
        this.totalVentas = totalVentas;
    }
    
    public Integer getPedidosEntregados(){
        return pedidosEntregados;
    }

    public void setPedidosEntregados(Integer pedidosEntregados){
        this.pedidosEntregados = pedidosEntregados;
    }

    public Integer getPedidosCancelados(){
        return pedidosCancelados;
    }

    public void setPedidosCancelados (Integer pedidosCancelados){
        this.pedidosCancelados = pedidosCancelados;
    }
}
