package com.example.GameSphere.view;

// Importa os métodos de asserção do JUnit
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.Test;

import com.example.GameSphere.model.Conexao;

// Classe de testes para validar inserções no banco
public class InsertTest {

    // Teste para inserção bem-sucedida
    @Test
    public void testInsertSucesso() {
        Conexao conexao = new Conexao(); // cria instância da classe de conexão
        conexao.conecta(); // abre a conexão com o banco
        boolean resultado = false; // guarda se o insert foi bem-sucedido

        try {
            // Executa um insert válido: Nome (string), Preço (número),
            // CodTipoProd e CodFornecedor (chaves inteiras válidas)
            conexao.statement.executeUpdate(
                    "insert into produto (Nome, Preco, CodTipoProd, CodFornecedor) values ('Produto Teste', 10, 1, 1)");

            resultado = true; // se não houve exceção, considera sucesso

            // Mensagem para o usuário (não ideal em testes automatizados)
            JOptionPane.showMessageDialog(null, "Gravação realizada com sucesso!",
                    "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException errosql) {
            // Caso ocorra erro SQL, exibe mensagem
            JOptionPane.showMessageDialog(null, "\n Erro na Gravação: \n " + errosql,
                    "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
        }

        // Verifica se o insert foi bem-sucedido (esperado)
        assertTrue(resultado, "O insert deve ser bem-sucedido");
    }

    // Teste para inserção que deve falhar
    @Test
    public void testInsertFalha() {
        Conexao conexao = new Conexao(); // cria instância
        conexao.conecta(); // abre conexão
        boolean resultado = false; // guarda se o insert foi bem-sucedido

        try {
            // Executa um insert inválido:
            // Nome recebe número (93) em vez de string
            // Preco recebe string ('R$10.23') em vez de número
            conexao.statement.executeUpdate(
                    "insert into produto (Nome, Preco, CodTipoProd, CodFornecedor) values (93, 'R$10.23', 1, 1)");

            resultado = true; // se não der erro, considera sucesso (mas não deveria)

            // Mensagem para o usuário
            JOptionPane.showMessageDialog(null, "Gravação realizada com sucesso!",
                    "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException errosql) {
            // O esperado é cair aqui, porque os tipos dos campos estão errados
            JOptionPane.showMessageDialog(null, "\n Erro na Gravação: \n " + errosql,
                    "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
        }

        // Verifica que o insert falhou (esperado)
        assertFalse(resultado, "O insert deve falhar");
    }

}
