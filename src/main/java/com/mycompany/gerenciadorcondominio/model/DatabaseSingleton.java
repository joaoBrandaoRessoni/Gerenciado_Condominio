/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.model;

/**
 *
 * @author joao_
 */
public class DatabaseSingleton {
    private static DatabaseSingleton instance;
    private String url;
    private String user;
    private String password;
    
    private DatabaseSingleton(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    public static DatabaseSingleton getInstance(){
        if(instance == null){
            instance = new DatabaseSingleton("jdbc:mysql://localhost:3306/condominio", "root", "1234");
        }
        
        return instance;
    }
    
    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
