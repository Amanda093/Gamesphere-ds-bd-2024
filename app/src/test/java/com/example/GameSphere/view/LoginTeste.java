package com.example.GameSphere.view;

// Importa os métodos de asserção do JUnit para verificar os testes
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.Test;

import com.example.GameSphere.model.Conexao;

// classe teste para validar o login
public class LoginTeste {

    // teste para caso de login bem-sucedido
    @Test
    public void testLoginSucesso() {
        Conexao conexao = new Conexao(); // cria instância do banco
        conexao.conecta(); // chama o metodo do banco de dados
        boolean resultado = false; // variável para guardar o resultado do login

        try {
            // SQL com usuário e senha válidos
            String pesquisa = "select * from funcionario where Usuario like 'Amanda' && Senha = '123'";
            conexao.executaSQL(pesquisa); // executa a query no banco

            // Se encontrou o registro esperado
            if (conexao.resultset.first()) {
                resultado = true; // login válido, exibe mensagem
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!", "Mensagem do Programa",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Caso não encontre, exibe mensagem
                JOptionPane.showMessageDialog(null, "\n Usuário não cadastrado!", "Mensagem do Programa",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException errosql) {
            // Se der erro na execução do SQL, mostra mensagem de erro
            JOptionPane.showMessageDialog(null, "Os dados digitados não foram localizados! \n" + errosql,
                    "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
        }

        // Verifica se o login teve sucesso
        assertTrue(resultado, "O login deve ser bem-sucedido");
    }

    // Teste para caso de login com falha
    @Test
    public void testLoginFalha() {
        Conexao conexao = new Conexao(); // cria instância do banco
        conexao.conecta(); // chama o metodo do banco de dados
        boolean resultado = false; // variável para guardar o resultado do login

        try {
            // SQL com usuário e senha inválidos
            String pesquisa = "select * from funcionario where Usuario like 'UsuarioInvalido' && Senha = 'SenhaInvalida'";
            conexao.executaSQL(pesquisa);

            // Se encontrou algum registro (não deveria)
            if (conexao.resultset.first()) {
                resultado = true; // login aceito (não esperado), exibe mensagem
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!", "Mensagem do Programa",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Caso não encontre registro (esperado), exibe mensagem
                JOptionPane.showMessageDialog(null, "\n Usuário não cadastrado!", "Mensagem do Programa",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException errosql) {
            // Caso ocorra erro de SQL
            JOptionPane.showMessageDialog(null, "Os dados digitados não foram localizados! \n" + errosql,
                    "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
        }

        // Verifica se o login falhou (esperado)
        assertFalse(resultado, "O login deve falhar");
    }
}
