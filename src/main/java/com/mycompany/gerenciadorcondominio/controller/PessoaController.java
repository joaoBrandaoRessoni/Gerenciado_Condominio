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

    public class PessoaTableData {
        public JTable table;
        public List<Integer> ids;
    }
        
    public PessoaTableData index(JTable jTable) throws SQLException {
        String sql = "SELECT p.*, count(*) as casas\n"
                + "FROM pessoas p\n"
                + "LEFT JOIN morador_residencia mr ON p.id = mr.id_pessoa\n"
                + "LEFT JOIN residencias r ON r.id = mr.id\n"
                + "GROUP BY p.id;";
        ResultSet pessoas = DAO.runExecuteQuery(sql, new ArrayList<>());

        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Nome", "CPF", "RG", "Data de nascimento"}, 0);
        List<Integer> ids = new ArrayList<>();

        while (pessoas.next()) {
            ids.add(pessoas.getInt("p.id"));
            dtm.addRow(new Object[]{
                pessoas.getString("nome"),
                pessoas.getString("cpf"),
                pessoas.getString("rg"),
                pessoas.getString("dt_nasc")
            });
        }

        pessoas.close();
        jTable.setModel(dtm);

        PessoaController.PessoaTableData data = new PessoaController.PessoaTableData();
        data.table = jTable;
        data.ids = ids;

        return data;
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
        String sql = "SELECT * FROM residencias WHERE id_proprietario = ?";
        List<Object> params = new ArrayList<>();
        params.add(idPessoa);

        ResultSet propriedades = DAO.runExecuteQuery(sql, params);

        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Rua", "Número", "CEP"}, 0);
        List<Integer> ids = new ArrayList<>();

        while (propriedades.next()) {
            ids.add(propriedades.getInt("id")); 
            dtm.addRow(new Object[]{
                propriedades.getString("logradouro"),
                propriedades.getInt("numero"),
                propriedades.getString("cep")
            });
        }

        propriedades.close();
        jTable.setModel(dtm);

        return jTable;
    }

    public ResidenciaModal showMoradia(int idPessoa) throws SQLException {
        //Busca a residencia que ele mora
        String sql = "SELECT r.* FROM residencias AS r "
                + "LEFT JOIN morador_residencia ON id_residencia = r.id "
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
    
    public int searchPessoa(String campo) throws SQLException  {
        String sql = "SELECT  id from pessoas where ? = ?";
        List<Object> params = new ArrayList();
        params.add(campo);
        params.add(campo);
        
        ResultSet propriedades = DAO.runExecuteQuery(sql, params);

        if (propriedades.next()) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public int verifyRelationship(int id) throws SQLException {
        String sql = "SELECT EXISTS (" 
                    + "   SELECT 1 FROM residencias WHERE id_proprietario = ?"
                    + ")"
                    + "OR EXISTS (" 
                    + "    SELECT 1 FROM morador_residencia WHERE id_pessoa = ?" 
                    + ") AS tem_vinculo";
        
        List<Object> params = new ArrayList();
        params.add(id);
        params.add(id);
        
        ResultSet propriedades = DAO.runExecuteQuery(sql, params);

        if (propriedades.next()) {
            return propriedades.getInt("tem_vinculo");
        } else {
            return 0;
        }
    }
}
