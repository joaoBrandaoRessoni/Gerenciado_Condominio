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
public class MensalidadeModal {
    private int id;
    private int id_residencia;
    private Date vencimento;
    private double valor;
    private int status;
    
    public MensalidadeModal(){}

    public MensalidadeModal(int id, int id_residencia, Date vencimento, double valor, int status) {
        this.id = id;
        this.id_residencia = id_residencia;
        this.vencimento = vencimento;
        this.valor = valor;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_residencia() {
        return id_residencia;
    }

    public void setId_residencia(int id_residencia) {
        this.id_residencia = id_residencia;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "id=" + id + ", id_residencia=" + id_residencia + ", vencimento=" + vencimento + ", valor=" + valor + ", status=" + status + '\n';
    }
}
