/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.controller;

import com.mycompany.gerenciadorcondominio.model.DAO;
import com.mycompany.gerenciadorcondominio.model.MensalidadeModal;
import com.mycompany.gerenciadorcondominio.model.ResidenciaModal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joao_
 */
public class MensalidadeController {

    public JTable index(int mes, int ano, JTable jTable) throws SQLException {
        String sql = "SELECT "
                + "    m.id, r.id AS id_residencia, m.vencimento, m.valor, m.status "
                + "FROM residencias r "
                + "LEFT JOIN mensalidade m ON m.id_residencia = r.id "
                + "WHERE MONTH(m.vencimento) = ? AND YEAR(m.vencimento) = ?";
        List<Object> params = new ArrayList();
        params.add(mes);
        params.add(ano);

        List<MensalidadeModal> mensalidades = DAO.runExecuteQuery(sql, params, "MensalidadeModal");

        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
        dtm.setRowCount(mensalidades.size());
        jTable.setModel(dtm);

        int posicaoLinha = 0;

        for (int i = 0; i < mensalidades.size(); i++) {
            jTable.setValueAt(((MensalidadeModal) mensalidades.get(i)).getId(), posicaoLinha, 0);
            jTable.setValueAt(((MensalidadeModal) mensalidades.get(i)).getId_residencia(), posicaoLinha, 1);
            jTable.setValueAt(((MensalidadeModal) mensalidades.get(i)).getVencimento(), posicaoLinha, 2);
            jTable.setValueAt(((MensalidadeModal) mensalidades.get(i)).getValor(), posicaoLinha, 3);
            jTable.setValueAt(((MensalidadeModal) mensalidades.get(i)).getStatus(), posicaoLinha, 4);
            posicaoLinha++;
        }

        return jTable;
    }
    
    public MensalidadeModal show(int id) throws SQLException {
        String sql = "SELECT * FROM mensalidade WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(id);

        List<MensalidadeModal> retornoMensalidade = DAO.runExecuteQuery(sql, params, "MensalidadeModal");
        MensalidadeModal mensalidade = null;

        if (!retornoMensalidade.isEmpty()) {
            mensalidade = retornoMensalidade.get(0);
        }

        return mensalidade;
    }

    public int pagar(int idMensalidade) throws SQLException {
        String sql = "UPDATE mensalidade SET status = 1 "
                + "WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idMensalidade);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int editarMensalidade(Date dataNascimento, double valor, int status, int idMensalidade) throws SQLException {
        String sql = "UPDATE mensalidade SET "
                + "vencimento = ?, "
                + "valor = ?, "
                + "status = ?, "
                + "WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(dataNascimento.toString());
        params.add(valor);
        params.add(status);
        params.add(idMensalidade);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int deletarMensalidade(int idMensalidade) throws SQLException {
        String sql = "DELETE FROM mensalidade WHERE id = ?";
        List<Object> params = new ArrayList();
        params.add(idMensalidade);

        return DAO.runExecuteUpdate(sql, params);
    }

    public int criarMensalidades(int mes, int ano) throws SQLException {
        String sql = "SELECT r.* FROM residencias r\n"
                + "WHERE NOT EXISTS (\n"
                + "    SELECT 1\n"
                + "    FROM mensalidade m\n"
                + "    WHERE m.id_residencia = r.id\n"
                + "      AND MONTH(m.vencimento) = ?\n"
                + "      AND YEAR(m.vencimento) = ?\n"
                + ");";
        List<Object> params = new ArrayList();
        params.add(mes);
        params.add(ano);
        List<ResidenciaModal> dados = DAO.runExecuteQuery(sql, params, "ResidenciaModal");

        dados.forEach(dado -> {
            String insert = "INSERT INTO mensalidade (id_residencia, vencimento, valor, status) "
                    + "VALUE (?, ?, 200, 1)";
            params.clear();
            params.add(((ResidenciaModal)dado).getId());
            params.add(ano + "-" + mes + "-" + "10");
            
            try{
                DAO.runExecuteUpdate(sql, params);
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        });
        
        return 1;
    }
}
