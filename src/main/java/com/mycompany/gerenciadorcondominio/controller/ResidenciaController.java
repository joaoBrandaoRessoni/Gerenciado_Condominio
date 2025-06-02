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

    public class ResidenciaTableData {
        public JTable table;
        public List<Integer> ids;
    }
    
    public class MoradorTableData {
        public JTable table;
        public List<Integer> ids;
    }

    public ResidenciaTableData index(JTable jTable) throws SQLException {
        String sql = "SELECT r.id, r.logradouro, r.numero, pp.nome AS proprietario, COUNT(DISTINCT p.id) AS moradores "
            + "FROM residencias r "
            + "LEFT JOIN pessoas pp ON r.id_proprietario = pp.id "
            + "LEFT JOIN morador_residencia mr ON r.id = mr.id_residencia "
            + "LEFT JOIN pessoas p ON mr.id_pessoa = p.id "
            + "GROUP BY r.id, r.logradouro, r.numero, pp.nome";
        ResultSet residencias = DAO.runExecuteQuery(sql, new ArrayList<>());

        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Logradouro", "Número", "Proprietário", "Moradores"}, 0);
        List<Integer> ids = new ArrayList<>();

        while (residencias.next()) {
            ids.add(residencias.getInt("r.id"));
            dtm.addRow(new Object[]{
                residencias.getString("logradouro"),
                residencias.getInt("numero"),
                residencias.getString("proprietario"),
                residencias.getInt("moradores")
            });
        }

        residencias.close();
        jTable.setModel(dtm);

        ResidenciaTableData data = new ResidenciaTableData();
        data.table = jTable;
        data.ids = ids;

        return data;
    }


    public PessoaModal showProprietario(int idResidencia) throws SQLException {
        //Busca o proprietario
        String sql = "SELECT p.* FROM pessoas AS p "
           + "JOIN residencias as r ON r.id_proprietario = p.id "
           + "WHERE r.id = ?";
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

    public MoradorTableData showMoradores(int idResidencia, JTable jTable) throws SQLException {
        String sql = "SELECT DISTINCT p.nome, p.cpf, p.id FROM pessoas AS p "
                   + "JOIN morador_residencia mr ON mr.id_pessoa = p.id "
                   + "WHERE mr.id_residencia = ?";

        List<Object> params = new ArrayList<>();
        params.add(idResidencia);

        ResultSet moradores = DAO.runExecuteQuery(sql, params);

        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Nome", "CPF"}, 0);
        List<Integer> ids = new ArrayList<>();

        while (moradores.next()) {
            ids.add(moradores.getInt("p.id"));

            Object[] row = new Object[]{
                moradores.getString("nome"),
                moradores.getString("cpf")
            };
            dtm.addRow(row);
        }

        moradores.close();

        jTable.setModel(dtm);
        
        MoradorTableData data = new MoradorTableData();
        data.table = jTable;
        data.ids = ids;

        return data;
    }

    public int alterarProprietario(int idNovoProprietario, int idResidencia) throws SQLException {
        String sql = "UPDATE residencias SET id_proprietario = ? WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idNovoProprietario == -1 ? null : idNovoProprietario);
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
                + "logradouro = ? "
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
    
    public int searchResidencia(String logradouro, int numero, String cep) throws SQLException {
        String sql = "SELECT id from residencia where logradouro = ? and numero = ? and cep = ?";
        List<Object> params = new ArrayList();
        params.add(logradouro);
        params.add(numero);
        params.add(cep);
        
        ResultSet propriedades = DAO.runExecuteQuery(sql, params);

        if (propriedades.next()) {
            return 1;
        } else {
            return 0;
        }
    }
}
