/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.model;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao_
 */
public class DAO {
    public static List runExecuteQuery(String sql, List<Object> params, String nomeModal){
        DatabaseSingleton bdInfo = DatabaseSingleton.getInstance();
        try (Connection connection = DriverManager.getConnection(bdInfo.getUrl(), bdInfo.getUser(), bdInfo.getPassword())) {
            // Criar um Statement para executar a consulta
            PreparedStatement statement = connection.prepareStatement(sql);
            
            //Roda parametro por parametro para adcionar no sql
            for(int i = 0; i < params.size(); i++){
                statement.setObject(i + 1, params.get(i));
            }
            try { 
                // Executar a consulta e obter o resultado
                ResultSet result = statement.executeQuery();
                FactoryModal factory = SimpleFactoryModal.getFactory(nomeModal);
                List dados = factory.createFromResultSet(result);
                
                result.close();
                statement.close();
                return dados;
            } catch(SQLException e){
                statement.close();
                return new ArrayList();
            }
        }catch (Exception e) {
            return new ArrayList();
        }
    }
    
    public static int runExecuteUpdate(String sql, List<Object> params){
        DatabaseSingleton bdInfo = DatabaseSingleton.getInstance();
        try (Connection connection = DriverManager.getConnection(bdInfo.getUrl(), bdInfo.getUser(), bdInfo.getPassword())) {
            // Criar um Statement para executar a consulta
            PreparedStatement statement = connection.prepareStatement(sql);
            
            //Roda parametro por parametro para adcionar no sql
            for(int i = 0; i < params.size(); i++){
                statement.setObject(i + 1, params.get(i));
            }
            
            try {
                // Executar a consulta
                statement.execute();
               
                //Verifica o número de linhas afetadas
                int result = statement.getUpdateCount();
                
                //Fecha o statement e retonar o número de linhas afetadas
                statement.close();
                return result;
            } catch(SQLException e){
                statement.close();
                return 0;
            }
        }catch (Exception e) {
            return 0;
        }
    }
}
