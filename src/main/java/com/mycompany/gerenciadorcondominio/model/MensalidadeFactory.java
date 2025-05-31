/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao_
 */
public class MensalidadeFactory implements FactoryModal {
    @Override
    public List createFromResultSet(ResultSet rs) throws SQLException {
        List<MensalidadeModal> dados = new ArrayList();
        while (rs.next()) {
            MensalidadeModal mensalidade = new MensalidadeModal();
            mensalidade.setId(rs.getInt("id"));
            mensalidade.setId_residencia(rs.getInt("id_residencia"));
            mensalidade.setVencimento(rs.getDate("vencimento"));
            mensalidade.setValor(rs.getDouble("valor"));
            mensalidade.setStatus(rs.getInt("status"));
            dados.add(mensalidade);
        }
        
        return dados;
    }
}
