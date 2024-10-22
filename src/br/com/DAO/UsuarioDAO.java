package br.com.DAO;

import br.com.DTO.UsuarioDTO;
import br.com.Views.TelaPrincipal;
import br.com.Views.TelaUsuario;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    public static UsuarioDAO objUsuarioDAO;

    // Conexão com o banco de dados e variáveis para manipular as consultas
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // Método para realizar o login de um usuário
    public void logar(UsuarioDTO objusuarioDTO) {
        String sql = "select * from tb_usuarios where usuario = ? and senha = ?";
        conexao = ConexaoDAO.conector();

        try {
            // Preparar a consulta com base no login e senha fornecidos
            pst = conexao.prepareStatement(sql);
            pst.setString(1, objusuarioDTO.getLogin_usuario());
            pst.setString(2, objusuarioDTO.getSenha_usuario());

            // Executa a query e armazena o resultado
            rs = pst.executeQuery();

            // Verifica se o usuário foi encontrado
            if (rs.next()) {
                String perfil = rs.getString(5); // Obtém o perfil do usuário

                // Verifica o perfil e ajusta as permissões na interface
                if (perfil.equals("admin")) {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    TelaPrincipal.subMenuUsuario.setEnabled(true); // Habilita funções de administrador
                    conexao.close();
                } else {
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    conexao.close();
                }
            } else {
                // Exibe mensagem se login ou senha forem inválidos
                JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválidos");
            }
        } catch (Exception e) {
            // Captura exceções e exibe mensagem de erro
            JOptionPane.showMessageDialog(null, "* Método Logar **" + e);
        }
    }

    // Método para pesquisar um usuário no banco de dados
    public void pesquisar(UsuarioDTO objUsuarioDTO) {
        String sql = "select * from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            rs = pst.executeQuery();

            // Se o usuário for encontrado, preenche os campos na tela
            if (rs.next()) {
                TelaUsuario.txtNomeUsu.setText(rs.getString(2));
                TelaUsuario.txtEmail.setText(rs.getString(3));
                TelaUsuario.txtLoginUsu.setText(rs.getString(4));
                TelaUsuario.txtSenhaUsu.setText(rs.getString(5));
                conexao.close();
            } else {
                // Caso não encontrado, exibe mensagem e limpa os campos
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                apagarCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método Pesquisar: " + e);
        }
    }

    // Método para inserir um novo usuário no banco de dados
    public void inserirUsuario(UsuarioDTO objUsuarioDTO) {
        String sql = "INSERT INTO tb_usuarios(id_usuario, usuario, email, login, senha) VALUES(?, ?, ?, ?, ?)";

        conexao = new ConexaoDAO().conector();

        try {
            // Verifica se o ID do usuário é válido (maior que 0)
            if (objUsuarioDTO.getId_usuario() < 0) {
                JOptionPane.showMessageDialog(null, "Erro: ID do usuário não pode ser menor que 0.");
                return;
            }

            // Verifica se todos os campos obrigatórios estão preenchidos
            if (objUsuarioDTO.getNome_usuario().isEmpty()
                || objUsuarioDTO.getEmail_usuario().isEmpty()
                || objUsuarioDTO.getLogin_usuario().isEmpty()
                || objUsuarioDTO.getSenha_usuario().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Erro: Todos os campos são obrigatórios.");
                return;
            }

            // Prepara a inserção com os dados fornecidos
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            pst.setString(2, objUsuarioDTO.getNome_usuario());
            pst.setString(3, objUsuarioDTO.getEmail_usuario());
            pst.setString(4, objUsuarioDTO.getLogin_usuario());
            pst.setString(5, objUsuarioDTO.getSenha_usuario());

            // Executa a inserção
            int resultado = pst.executeUpdate();

            // Exibe mensagem de sucesso ou falha
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Usuário inserido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Usuário já existe ou não foi inserido.");
            }

            pst.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Código de erro para duplicação
                JOptionPane.showMessageDialog(null, "Erro: Usuário já existe.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir usuário: " + e.getMessage());
            }
        } finally {
            // Fecha a conexão
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // Método para editar um usuário no banco de dados
    public void editar(UsuarioDTO objUsuarioDTO) {
        String sql = "update tb_usuarios set usuario=?,email=?, login=?, senha=? where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(5, objUsuarioDTO.getId_usuario());
            pst.setString(1, objUsuarioDTO.getNome_usuario());
            pst.setString(2, objUsuarioDTO.getEmail_usuario());
            pst.setString(3, objUsuarioDTO.getLogin_usuario());
            pst.setString(4, objUsuarioDTO.getSenha_usuario());

            // Executa a atualização
            int add = pst.executeUpdate();
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Usuário editado com sucesso!");
                apagarCampos(); // Limpa os campos após a edição
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método Editar: " + e);
        }
    }

    // Método para limpar os campos do formulário
    public void apagarCampos() {
        TelaUsuario.txtIdUsu.setText(null);
        TelaUsuario.txtNomeUsu.setText(null);
        TelaUsuario.txtEmail.setText(null);
        TelaUsuario.txtLoginUsu.setText(null);
        TelaUsuario.txtSenhaUsu.setText(null);
    }

    // Método para apagar um usuário do banco de dados
    public void apagar(UsuarioDTO objUsuarioDTO) {
        String sql = "delete from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());

            // Executa a exclusão
            int add = pst.executeUpdate();
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!");
                apagarCampos(); // Limpa os campos após a exclusão
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método Apagar: " + e);
        }
    }
}
