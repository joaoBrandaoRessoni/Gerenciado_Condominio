/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.controller;

import com.mycompany.gerenciadorcondominio.model.DAO;
import com.mycompany.gerenciadorcondominio.model.PessoaModal;
import com.mycompany.gerenciadorcondominio.model.ResidenciaModal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joao_
 */
public class PessoaController {

    public JTable index(JTable jTable) throws SQLException {
        String sql = "SELECT p.*, count(*) as casas\n"
                + "FROM pessoas p\n"
                + "JOIN morador_residencia mr ON p.id = mr.id_pessoa\n"
                + "JOIN residencias r ON r.id = mr.id\n"
                + "GROUP BY p.id;";
        ResultSet pessoas = DAO.runExecuteQuery(sql, new ArrayList<>());

        int rows = 0;

        if (pessoas.last()) {
            rows = pessoas.getRow();
            pessoas.beforeFirst();
        }

        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(rows);
        jTable.setModel(dtm);

        int posicaoLinha = 0;
        
        while(pessoas.next()){
            jTable.setValueAt(pessoas.getInt("id"), posicaoLinha, 0);
            jTable.setValueAt(pessoas.getString("nome"), posicaoLinha, 1);
            jTable.setValueAt(pessoas.getString("cpf"), posicaoLinha, 2);
            jTable.setValueAt(pessoas.getInt("casas"), posicaoLinha, 3);
            posicaoLinha++;
        }
        
        pessoas.close();

        return jTable;
    }
    
    public List<PessoaModal> indexPessoa() throws SQLException {
        String sql = "SELECT * FROM pessoas";
        return DAO.runExecuteQuery(sql, new ArrayList<>(), "PessoaModal");
    }
    
    public List<ResidenciaModal> showAllResidencias() throws SQLException{
        String sql = "SELECT * FROM residencias";
        return DAO.runExecuteQuery(sql, new ArrayList<>(), "ResidenciaModal");
    }

    public PessoaModal showProprietario(int idPessoa) throws SQLException {
        //Busca a pessoa
        String sql = "SELECT * FROM pessoas WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idPessoa);
        List<PessoaModal> retornoPessoa = DAO.runExecuteQuery(sql, params, "PessoaModal");
        PessoaModal pessoa = null;

        if (!retornoPessoa.isEmpty()) {
            pessoa = retornoPessoa.get(0);
        }

        return pessoa;
    }

    public JTable showPropriedades(int idPessoa, JTable jTable) throws SQLException {
        //Buscas as propriedades
        String sql = "SELECT * FROM residencias WHERE id_proprietario = ?";
        List<Object> params = new ArrayList();
        params.add(idPessoa);

        ResultSet propriedades = DAO.runExecuteQuery(sql, params);
        
        int rows = 0;

        if (propriedades.last()) {
            rows = propriedades.getRow();
            propriedades.beforeFirst();
        }

        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(rows);
        jTable.setModel(dtm);

        int posicaoLinha = 0;
        
        while(propriedades.next()){
            jTable.setValueAt(propriedades.getString("rua"), posicaoLinha, 0);
            jTable.setValueAt(propriedades.getInt("numero"), posicaoLinha, 1);
            jTable.setValueAt(propriedades.getString("cep"), posicaoLinha, 2);
            posicaoLinha++;
        }
        
        propriedades.close();

        return jTable;
    }

    public ResidenciaModal showMoradia(int idPessoa) throws SQLException {
        //Busca a residencia que ele mora
        String sql = "SELECT r.* FROM residencias AS r "
                + "JOIN morador_residencia ON id_residencia = r.id "
                + "WHERE id_pessoa = ?";
        List<Object> params = new ArrayList();
        params.add(idPessoa);

        List<ResidenciaModal> retornoMoradia = DAO.runExecuteQuery(sql, params, "ResidenciaModal");
        ResidenciaModal moradia = null;

        if (!retornoMoradia.isEmpty()) {
            moradia = retornoMoradia.get(0);
        }

        return moradia;
    }

    public int updateDados(String nome, Date dt_nascimento, String cpf, String rg, int id) throws SQLException {
        String sql = "UPDATE pessoas SET "
                + "nome = ?, "
                + "dt_nasc = ?, "
                + "cpf = ?, "
                + "rg = ? "
                + "WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(nome);
        params.add(dt_nascimento.toString());
        params.add(cpf);
        params.add(rg);
        params.add(id);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int inserir(String nome, Date dt_nascimento, String cpf, String rg) throws SQLException {
        String sql = "INSERT INTO pessoas (nome, dt_nasc, cpf, rg) VALUE (?, ?, ?, ?)";
        List<Object> params = new ArrayList();
        params.add(nome);
        params.add(dt_nascimento.toString());
        params.add(cpf);
        params.add(rg);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int delete(int idPessoa) throws SQLException {
        String sql = "DELETE FROM pessoas WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idPessoa);

        return DAO.runExecuteUpdate(sql, params);
    }
}
