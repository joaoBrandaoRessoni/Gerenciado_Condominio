/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciadorcondominio.model.Factory;

/**
 *
 * @author joao_
 */
public class SimpleFactoryModal {
    public static FactoryModal getFactory(String nomeModal){
        FactoryModal factory = switch(nomeModal){
            case "PessoaModal" -> new PessoaFactory();
            case "MensalidadeModal" -> new MensalidadeFactory();
            case "ResidenciaModal" -> new ResidenciaFactory();
            default -> throw new IllegalArgumentException("Nenhuma fábrica registrada para ");
        };
        
        return factory;
    }
}
