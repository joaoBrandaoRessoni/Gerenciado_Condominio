/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.model;

import java.sql.Date;

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
    private Date data_criacao;
    
    public ResidenciaModal(){}

    public ResidenciaModal(int id, int id_proprietario, int numero, String cep, String logradouro, Date data_criacao) {
        this.id = id;
        this.id_proprietario = id_proprietario;
        this.numero = numero;
        this.cep = cep;
        this.logradouro = logradouro;
        this.data_criacao = data_criacao;
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

    public String getDataCriacao() {
        return data_criacao.toString();
    }

    public void setDataCriacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    @Override
    public String toString() {
        return "id=" + id + ", id_proprietario=" + id_proprietario + ", numero=" + numero + ", cep=" + cep + ", logradouro=" + logradouro + ", data_criacao=" + data_criacao + '\n';
    }
    
    
}
