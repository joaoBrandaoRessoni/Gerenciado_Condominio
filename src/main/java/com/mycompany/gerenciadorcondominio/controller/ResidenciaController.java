/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.controller;

import com.mycompany.gerenciadorcondominio.model.DAO;
import com.mycompany.gerenciadorcondominio.model.PessoaModal;
import com.mycompany.gerenciadorcondominio.model.ResidenciaModal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao_
 */
public class ResidenciaController {

    public List index() throws SQLException {
        String sql = "SELECT * FROM residencias";

        return DAO.runExecuteQuery(sql, new ArrayList<>(), "ResidenciaModal");
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

    public List<PessoaModal> showMoradores(int idResidencia) throws SQLException {
        //Busca as pessoas que moram na casa
        String sql = "SELECT p.* FROM pessoas AS p "
                + "JOIN morador_residencia ON id_pessoa = p.id "
                + "WHERE id_residencia = ?";
        List<Object> params = new ArrayList();
        params.add(idResidencia);
        return DAO.runExecuteQuery(sql, params, "PessoaModal");
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
    
    public int updateResidencia(ResidenciaModal dados) throws SQLException{
        String sql = "UPDATE pessoas SET " +
                    "numero = ?, " +
                    "cep = ?, " +
                    "logradouro = ?, " +
                    "WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(dados.getNumero());
        params.add(dados.getCep());
        params.add(dados.getLogradouro());
        params.add(dados.getId());
        
        return DAO.runExecuteUpdate(sql, params);
    }
    
    public int insertResidencia(ResidenciaModal dados) throws SQLException{
        String sql = "INSERT INTO residencias (id_proprietario, numero, cep, logradouro) "+
                "VALUE (?, ?, ?, ?)";
        List<Object> params = new ArrayList();
        params.add(dados.getId_proprietario());
        params.add(dados.getNumero());
        params.add(dados.getCep());
        params.add(dados.getLogradouro());
        
        return DAO.runExecuteUpdate(sql, params);
    }
    
    public int deleteResidencia(int idResidencia) throws SQLException{
        String sql = "DELETE FROM residencias WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idResidencia);
        
        return DAO.runExecuteUpdate(sql, params);
    }
}
