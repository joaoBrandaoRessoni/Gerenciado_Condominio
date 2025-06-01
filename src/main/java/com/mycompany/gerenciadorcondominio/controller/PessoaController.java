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
public class PessoaController {
    public List index() throws SQLException {
        String sql = "SELECT * FROM pessoas";
        
        return DAO.runExecuteQuery(sql, new ArrayList<>(), "PessoaModal");
    }
    
    public PessoaModal showProprietario(int idPessoa) throws SQLException{
        //Busca a pessoa
        String sql = "SELECT * FROM pessoas WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idPessoa);
        List<PessoaModal> retornoPessoa = DAO.runExecuteQuery(sql, params, "PessoaModal");
        PessoaModal pessoa = null;
        
        if(!retornoPessoa.isEmpty()){
            pessoa = retornoPessoa.get(0);
        }

        return pessoa;
    }

    public List<ResidenciaModal> showPropriedades(int idPessoa) throws SQLException{
        //Buscas as propriedades
        String sql = "SELECT * FROM residencias WHERE id_proprietario = ?";
        List<Object> params = new ArrayList();
        params.add(idPessoa);

        return DAO.runExecuteQuery(sql, params, "ResidenciaModal");
    }
        
    public ResidenciaModal showMoradia(int idPessoa) throws SQLException{
        //Busca a residencia que ele mora
        String sql = "SELECT r.* FROM residencias AS r " +
              "JOIN morador_residencia ON id_residencia = r.id " +
              "WHERE id_pessoa = ?";
        List<Object> params = new ArrayList();
        params.add(idPessoa);
        
        List<ResidenciaModal> retornoMoradia = DAO.runExecuteQuery(sql, params, "ResidenciaModal");
        ResidenciaModal moradia = null;
        
        if(!retornoMoradia.isEmpty()){
            moradia = retornoMoradia.get(0);
        }
        
        return moradia;
    }
    
    public int updateDados(PessoaModal dados) throws SQLException{
        String sql = "UPDATE pessoas SET " +
                    "nome = ?, " +
                    "dt_nasc = ?, " +
                    "cpf = ?, " +
                    "rg = ? " +
                    "WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(dados.getNome());
        params.add(dados.getDt_nasc());
        params.add(dados.getCpf());
        params.add(dados.getRg());
        params.add(dados.getId());
        
        return DAO.runExecuteUpdate(sql, params);
    }
    
    public int inserir(PessoaModal dados) throws SQLException{
        String sql = "INSERT INTO pessoas (nome, dt_nasc, cpf, rg) VALUE (?, ?, ?, ?)";
        List<Object> params = new ArrayList();
        params.add(dados.getNome());
        params.add(dados.getDt_nasc());
        params.add(dados.getCpf());
        params.add(dados.getRg());
        
        return DAO.runExecuteUpdate(sql, params);
    }
    
    public int delete(int idPessoa) throws SQLException{
        String sql = "DELETE FROM pessoas WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idPessoa);
        
        return DAO.runExecuteUpdate(sql, params);
    }
}
