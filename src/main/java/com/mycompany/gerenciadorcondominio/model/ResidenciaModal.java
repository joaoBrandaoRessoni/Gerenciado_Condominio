/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.model;

/**
 *
 * @author joao_
 */
public class ResidenciaModal {
    private int id;
    private int id_proprietario;
    private int numero;
    private String cep;
    private String logradouro;
    private int status;
    
    public ResidenciaModal(){}

    public ResidenciaModal(int id, int id_proprietario, int numero, String cep, String logradouro, int status) {
        this.id = id;
        this.id_proprietario = id_proprietario;
        this.numero = numero;
        this.cep = cep;
        this.logradouro = logradouro;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_proprietario() {
        return id_proprietario;
    }

    public void setId_proprietario(int id_proprietario) {
        this.id_proprietario = id_proprietario;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
