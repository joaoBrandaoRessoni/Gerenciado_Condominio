/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.controller;

import com.mycompany.gerenciadorcondominio.model.DAO;
import com.mycompany.gerenciadorcondominio.model.PessoaModal;
import com.mycompany.gerenciadorcondominio.model.ResidenciaModal;
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
public class ResidenciaController {

    public JTable index(JTable jTable) throws SQLException {
        String sql = "SELECT r.id, r.logradouro, r.numero,  pp.nome AS proprietario,  COUNT(distinct p.id) AS moradores\n"
                + "FROM residencias r\n"
                + "JOIN pessoas pp ON r.id_proprietario = pp.id\n"
                + "JOIN morador_residencia mr ON r.id = mr.id_residencia\n"
                + "JOIN pessoas p ON mr.id_pessoa = p.id\n"
                + "GROUP BY p.id";
        ResultSet residencias = DAO.runExecuteQuery(sql, new ArrayList<>());

        int rows = 0;

        if (residencias.last()) {
            rows = residencias.getRow();
            residencias.beforeFirst();
        }

        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(rows);
        jTable.setModel(dtm);

        int posicaoLinha = 0;

        while (residencias.next()) {
            jTable.setValueAt(residencias.getInt("id"), posicaoLinha, 0);
            jTable.setValueAt(residencias.getString("logradouro"), posicaoLinha, 1);
            jTable.setValueAt(residencias.getInt("numero"), posicaoLinha, 2);
            jTable.setValueAt(residencias.getString("proprietario"), posicaoLinha, 3);
            jTable.setValueAt(residencias.getInt("moradores"), posicaoLinha, 3);
            
            posicaoLinha++;
        }

        residencias.close();

        return jTable;
    }

    public PessoaModal showProprietario(int idResidencia) throws SQLException {
        //Busca o proprietario
        String sql = "SELECT p.* FROM pessoas AS p "
                + "JOIN morador_residencia ON id_pessoa = p.id "
                + "WHERE id_residencia = ?";
        List<Object> params = new ArrayList();
        params.add(idResidencia);
        List<PessoaModal> retornoProprietario = DAO.runExecuteQuery(sql, params, "PessoaModal");
        PessoaModal proprietario = null;

        if (!retornoProprietario.isEmpty()) {
            proprietario = retornoProprietario.get(0);
        }

        return proprietario;
    }

    public ResidenciaModal showResidencia(int idResidencia) throws SQLException {
        //Buscar a residencia
        String sql = "SELECT * FROM residencias WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idResidencia);
        List<ResidenciaModal> retornoResidencia = DAO.runExecuteQuery(sql, params, "ResidenciaModal");
        ResidenciaModal residencia = null;

        if (!retornoResidencia.isEmpty()) {
            residencia = retornoResidencia.get(0);
        }

        return residencia;
    }

    public JTable showMoradores(int idResidencia, JTable jTable) throws SQLException {
        //Busca as pessoas que moram na casa
        String sql = "SELECT p.* FROM pessoas AS p "
                + "JOIN morador_residencia ON id_pessoa = p.id "
                + "WHERE id_residencia = ?";
        List<PessoaModal> moradores = DAO.runExecuteQuery(sql, new ArrayList<>(), "PessoaModal");

        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(moradores.size());
        jTable.setModel(dtm);

        int posicaoLinha = 0;

        for(int i = 0; i< moradores.size(); i++){
            jTable.setValueAt(((PessoaModal)moradores.get(i)).getNome(), posicaoLinha, 0);
            jTable.setValueAt(((PessoaModal)moradores.get(i)).getCpf(), posicaoLinha, 1);
            
            sql = "SELECT count(*) as cont FROM pessoas p "
                    + "JOIN residencias r ON id_proprietario = p.id "
                    + "WHERE p.id = ?";
            List<Object> params = new ArrayList();
            params.add(((PessoaModal)moradores.get(i)).getId());
            ResultSet dados = DAO.runExecuteQuery(sql, params);
            
            boolean existe = false;
            while(dados.next()){
                if(dados.getInt("cont") > 0){
                    existe = true;
                }
            }
            
            dados.close();
            
            jTable.setValueAt(existe ? "Sim" : "Não", posicaoLinha, 2);
            
            posicaoLinha++;
        }

        return jTable;
    }

    public int alterarProprietario(int idNovoProprietario, int idResidencia) throws SQLException {
        String sql = "UPDATE residencias SET id_proprietario = ? WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idNovoProprietario);
        params.add(idResidencia);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int adicionarMorador(int idNovoProprietario, int idResidencia) throws SQLException {
        String sql = "INSERT INTO morador_residencia (id_pessoa, id_residencia) VALUE (? ,?)";
        List<Object> params = new ArrayList();
        params.add(idNovoProprietario);
        params.add(idResidencia);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int removerMorador(int idMorador, int idResidencia) throws SQLException {
        String sql = "DELETE FROM morador_residencia WHERE id_pessoa = ? AND id_residencia = ?";
        List<Object> params = new ArrayList();
        params.add(idMorador);
        params.add(idResidencia);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int updateResidencia(int numero, String cep, String logradouro, int idResidencia) throws SQLException {
        String sql = "UPDATE residencias SET "
                + "numero = ?, "
                + "cep = ?, "
                + "logradouro = ?, "
                + "WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(numero);
        params.add(cep);
        params.add(logradouro);
        params.add(idResidencia);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int insertResidencia(int idProprietario, int numero, String cep, String logradouro) throws SQLException {
        String sql = "INSERT INTO residencias (id_proprietario, numero, cep, logradouro) "
                + "VALUE (?, ?, ?, ?)";
        List<Object> params = new ArrayList();
        params.add(idProprietario);
        params.add(numero);
        params.add(cep);
        params.add(logradouro);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int deleteResidencia(int idResidencia) throws SQLException {
        String sql = "DELETE FROM residencias WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idResidencia);

        return DAO.runExecuteUpdate(sql, params);
    }
}
