package com.CodeChefs.ordenMS.model;



import jakarta.persistence.\*;



@Entity

public class Orden {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String cliente;

    private Double total;

    private String estado;



    public Long getId() {

        return id;

    }



    public String getCliente(){

        return cliente;

    }



    public void setCliente(String cliente){

        this.cliente = cliente;

    }


    public Double getTotal(){

        return total;

    }



    public void setTotal(Double total) {

        this.total = total;

    }



    public String getEstado(){

        return estado;

    }



    public void setEstado(String estado) {

        this.estado = estado;

    }





}