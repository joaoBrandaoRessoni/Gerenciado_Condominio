/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao_
 */
public class PessoaFactory implements FactoryModal {

    @Override
    public List createFromResultSet(ResultSet rs) throws SQLException {
        List<PessoaModal> dados = new ArrayList();
        while (rs.next()) {
            PessoaModal pessoa = new PessoaModal();
            pessoa.setId(rs.getInt("id"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setDt_nasc(rs.getDate("dt_nasc"));
            pessoa.setCpf(rs.getString("cpf"));
            pessoa.setRg(rs.getString("rg"));
            dados.add(pessoa);
        }
        
        return dados;
    }

}
