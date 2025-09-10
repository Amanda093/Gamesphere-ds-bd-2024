package com.example.GameSphere.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.*;

public class ConexaoTest { // Classe teste

    // teste para caso a conexão seja bem sucedida
    @Test
    public void testConexaoSucesso() {
        Conexao conexao = new Conexao(); // cria instância
        boolean conectado = conexao.conecta(); // chama o metodo do banco de dados
        assertTrue(conectado, "Deveria conectar ao banco com sucesso"); // verifica se retornou true e se sim, passa
    }

    // teste para caso a conexão falhe
    @Test
    public void testConexaoFalha() {
        Conexao conexaoClasse = new Conexao() {
            final protected String url = "jdbc:mysql://localhost/banco_inexistente";
            // força uma URL errada para simular falha

            @Override
            public boolean conecta() { // tenta conectar mas como banco não existe, DriverManager faz uma exceção
                try {
                    Class.forName(driver);
                    conexao = DriverManager.getConnection(url, usuario, senha);
                } catch (Exception e) { //
                    return false;
                }
                return true;
            }
        };
        boolean conectado = conexaoClasse.conecta();
        assertFalse(conectado, "Deveria falhar a conexão com banco inexistente");
    }
}