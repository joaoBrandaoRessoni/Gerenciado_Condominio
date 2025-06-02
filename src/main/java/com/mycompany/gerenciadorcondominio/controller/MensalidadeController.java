/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.controller;

import com.mycompany.gerenciadorcondominio.model.DAO;
import com.mycompany.gerenciadorcondominio.model.MensalidadeModal;
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
public class MensalidadeController {
    
    public class MensalidadeTableData {
        public JTable table;
        public List<Integer> ids;
    }

    public MensalidadeTableData  index(int mes, int ano, JTable jTable) throws SQLException {
        String sql;
        List<Object> params = new ArrayList<>();

        if (mes == 0 && ano == 0) {
            // Buscar as últimas 15 mensalidades ordenadas por vencimento decrescente
            sql = "SELECT " 
                    + "    m.id, r.id AS id_residencia, r.logradouro AS rua, r.numero AS numero,"
                    + "    p.nome AS nome, p.cpf AS cpf," 
                    + "    m.vencimento, m.valor, m.status" 
                    + " FROM mensalidade m" 
                    + " LEFT JOIN residencias r ON r.id = m.id_residencia" 
                    + " LEFT JOIN pessoas p ON p.id = r.id_proprietario" 
                    + " ORDER BY m.vencimento DESC" 
                    + " LIMIT 15";
        } else {
            // Busca filtrada por mês e ano
            sql = "SELECT " 
                    + "    m.id, r.id AS id_residencia, r.logradouro AS rua, r.numero AS numero," 
                    + "    p.nome AS nome, p.cpf AS cpf," 
                    + "    m.vencimento, m.valor, m.status" 
                    + " FROM mensalidade m" 
                    + " LEFT JOIN residencias r ON r.id = m.id_residencia"
                    + " LEFT JOIN pessoas p ON p.id = r.id_proprietario" 
                    + " WHERE MONTH(m.vencimento) = ? AND YEAR(m.vencimento) = ?";
            params.add(mes);
            params.add(ano);
        }

        ResultSet mensalidades = DAO.runExecuteQuery(sql, params);

        DefaultTableModel dtm = new DefaultTableModel(
            new Object[]{"Residência", "Proprietário", "Vencimento", "Valor", "Status"}, 0
        );

        List<Integer> ids = new ArrayList<>();

        while (mensalidades.next()) {
            ids.add(mensalidades.getInt("m.id"));
            String rua = mensalidades.getString("rua");     
            int numero = mensalidades.getInt("numero");     
            String nome = mensalidades.getString("nome");  
            String cpf = mensalidades.getString("cpf");
            String status = mensalidades.getInt("status") == 1 ? "À Pagar" : "Pago";
                    
            String ruaNumero = "";
            if (rua != null) {
                ruaNumero = rua + " - " + numero;
            }
             
            String nomeCpf = "";
            if (cpf != null) {
                nomeCpf = nome + " - " + cpf;
            }

            dtm.addRow(new Object[] {
                ruaNumero,
                nomeCpf,
                mensalidades.getDate("vencimento"),
                mensalidades.getDouble("valor"),
                status
            });
        }

        mensalidades.close();
        jTable.setModel(dtm);
        
        MensalidadeController.MensalidadeTableData data = new MensalidadeController.MensalidadeTableData();
        data.table = jTable;
        data.ids = ids;

        return data;
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
                + "status = ? "
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
