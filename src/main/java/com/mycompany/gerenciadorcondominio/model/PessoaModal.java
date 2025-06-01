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
public class PessoaModal {
    private int id;
    private String nome;
    private Date dt_nasc;
    private String cpf;
    private String rg;
    
    public PessoaModal(){}

    public PessoaModal(int id, String nome, Date dt_nasc, String cpf, String rg) {
        this.id = id;
        this.nome = nome;
        this.dt_nasc = dt_nasc;
        this.cpf = cpf;
        this.rg = rg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDt_nasc() {
        return dt_nasc.toString();
    }

    public void setDt_nasc(Date dt_nasc) {
        this.dt_nasc = dt_nasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Override
    public String toString() {
        return "id=" + id + ", nome=" + nome + ", dt_nasc=" + dt_nasc + ", cpf=" + cpf + ", rg=" + rg + '\n';
    }
}
