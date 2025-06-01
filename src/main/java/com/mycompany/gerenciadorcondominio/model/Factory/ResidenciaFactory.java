/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.model.Factory;

import com.mycompany.gerenciadorcondominio.model.ResidenciaModal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao_
 */
public class ResidenciaFactory implements FactoryModal {
    @Override
    public List createFromResultSet(ResultSet rs) throws SQLException {
        List<ResidenciaModal> dados = new ArrayList();
        while (rs.next()) {
            ResidenciaModal residencia = new ResidenciaModal();
            residencia.setId(rs.getInt("id"));
            residencia.setId_proprietario(rs.getInt("id_proprietario"));
            residencia.setNumero(rs.getInt("numero"));
            residencia.setCep(rs.getString("cep"));
            residencia.setLogradouro(rs.getString("logradouro"));
            residencia.setDataCriacao(rs.getDate("data_criacao"));
            dados.add(residencia);
        }
        
        return dados;
    }
}
