/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.controller;

import com.mycompany.gerenciadorcondominio.model.DAO;
import com.mycompany.gerenciadorcondominio.model.MensalidadeModal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao_
 */
public class MensalidadeController {

    public List index(int mes, int ano) throws SQLException {
        String sql = "SELECT "
                + "    m.id, r.id AS id_residencia, m.vencimento, m.valor, m.status "
                + "FROM residencias r "
                + "LEFT JOIN mensalidade m "
                + "  ON m.id_residencia = r.id "
                + " AND MONTH(m.vencimento) = ? "
                + " AND YEAR(m.vencimento) = ? "
                + "WHERE EXISTS ( "
                + "    SELECT 1 FROM mensalidade m "
                + "    WHERE m.id_residencia = r.id "
                + "      AND MONTH(m.vencimento) = ? "
                + "      AND YEAR(m.vencimento) = ? " 
                + ") "
                + "UNION "
                + "SELECT "
                + "    NULL AS id, r.id AS id_residencia, STR_TO_DATE(CONCAT(?, '-', ?, '-10'), '%Y-%m-%d') AS vencimento, "
                + "    200 AS valor, "
                + "    0 AS status "
                + "FROM residencias r "
                + "WHERE NOT EXISTS ( "
                + "    SELECT 1 FROM mensalidade m "
                + "    WHERE m.id_residencia = r.id "
                + "      AND MONTH(m.vencimento) = ? "
                + "      AND YEAR(m.vencimento) = ? " 
                + ")";
        List<Object> params = new ArrayList();
        params.add(mes);
        params.add(ano);
        params.add(mes);
        params.add(ano);
        params.add(ano);
        params.add(mes < 10 ? "0" + mes : mes);
        params.add(mes);
        params.add(ano);
        
        return DAO.runExecuteQuery(sql, params, "MensalidadeModal");
    }
    
    public MensalidadeModal show(int mes, int ano, int idResidencia) throws SQLException{
        String sql = "SELECT "
                + "    m.id, r.id AS id_residencia, "
                + "COALESCE(m.vencimento, CONCAT(?, '-', ?, '-10')) as vencimento, "
                + "COALESCE(m.valor, 200) as valor, "
                + "m.status "
                + "FROM residencias r "
                + "LEFT JOIN mensalidade m "
                + "  ON m.id_residencia = r.id "
                + " AND MONTH(m.vencimento) = ? "
                + " AND YEAR(m.vencimento) = ? "
                + "WHERE r.id = ?";
        List<Object> params = new ArrayList();
        params.add(ano);
        params.add(mes < 10 ? "0" + mes : mes);
        params.add(mes);
        params.add(ano);
        params.add(idResidencia);
        
        List<MensalidadeModal> retornoMensalidade = DAO.runExecuteQuery(sql, params, "MensalidadeModal");
        MensalidadeModal mensalidade = null;
        
        if(!retornoMensalidade.isEmpty()){
            mensalidade = retornoMensalidade.get(0);
        }
        
        return mensalidade;
    }
    
    public int pagar(int mes, int ano, int idResidenca) throws SQLException {
        String sql = "INSERT INTO mensalidade (id_residencia, vencimento, valor, status) VALUE (?, ?, ?, ?)";
        List<Object> params = new ArrayList();
        params.add(idResidenca);
        params.add(ano + "-" + mes + "-10");
        params.add(200);
        params.add(1);
        
        return DAO.runExecuteUpdate(sql, params);
    }
    
    public int editarMensalidade(MensalidadeModal dados) throws SQLException {
        String sql = "UPDATE mensalidade SET " +
                    "vencimento = ?, " +
                    "valor = ?, " +
                    "status = ?, " +
                    "WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(dados.getVencimento());
        params.add(dados.getValor());
        params.add(dados.getStatus());
        params.add(dados.getId());
        
        return DAO.runExecuteUpdate(sql, params);
    }
    
    public int deletarMensalidade(int idMensalidade) throws SQLException {
        String sql = "DELETE FROM mensalidade WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idMensalidade);
        
        return DAO.runExecuteUpdate(sql, params);
    }
}
