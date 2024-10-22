package br.com.DAO;

import br.com.DTO.AgendaDTO;
import br.com.Views.TelaAgenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

// Classe responsável por manipular os dados da agenda no banco de dados
public class AgendaDAO {
    // Instância estática da classe AgendaDAO
    public static AgendaDAO objAgendaDAO;

    // Variáveis para conexão com o banco de dados
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    // Método para inserir uma nova agenda no banco de dados
    public void inserirAgenda(AgendaDTO objAgendaDTO) {
        // Comando SQL para inserir dados na tabela tb_agenda
        String sql = "INSERT INTO tb_agenda(id, cliente, data, hora, descricao) VALUES(?, ?, ?, ?, ?)";

        // Estabelece a conexão com o banco de dados
        conexao = new ConexaoDAO().conector();

        try {
            // Verifica se o ID da agenda é válido (não pode ser menor que 0)
            if (objAgendaDTO.getId() < 0) {
                JOptionPane.showMessageDialog(null, "Erro: ID da agenda não pode ser menor que 0.");
                return; // Encerra o método se o ID for inválido
            }

            // Verifica se todos os campos obrigatórios foram preenchidos
            if (objAgendaDTO.getCliente().isEmpty()
                    || objAgendaDTO.getData().isEmpty()
                    || objAgendaDTO.getHora().isEmpty()
                    || objAgendaDTO.getDescricao().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Erro: Todos os campos são obrigatórios.");
                return; // Encerra o método se algum campo estiver vazio
            }

            // Prepara o comando SQL com os valores dos campos da agenda
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objAgendaDTO.getId());
            pst.setString(2, objAgendaDTO.getCliente());
            pst.setString(3, objAgendaDTO.getData());
            pst.setString(4, objAgendaDTO.getHora());
            pst.setString(5, objAgendaDTO.getDescricao());

            // Executa a inserção no banco de dados
            int resultado = pst.executeUpdate();

            // Verifica se a inserção foi bem-sucedida
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Agenda inserida com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Agenda já existe ou não foi inserido.");
            }

            // Fecha o PreparedStatement após a operação
            pst.close();
        } catch (SQLException e) {
            // Tratamento de erro para entradas duplicadas
            if (e.getErrorCode() == 1062) { // Código de erro para entrada duplicada
                JOptionPane.showMessageDialog(null, "Erro: Agenda já existe.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir agenda: " + e.getMessage());
            }
        } catch (Exception e) {
            // Tratamento de qualquer outro erro
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        } finally {
            // Fecha a conexão com o banco de dados
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
    
    // Método para editar uma agenda existente no banco de dados
    public void editar(AgendaDTO objAgendaDTO) {
        // Comando SQL para atualizar os dados da agenda com base no ID
        String sql = "update tb_agenda set cliente=?, data=?, hora=?, descricao=? where id = ?";
        conexao = ConexaoDAO.conector();

        try {
            // Prepara o comando SQL com os novos valores
            pst = conexao.prepareStatement(sql);
            pst.setInt(5, objAgendaDTO.getId());
            pst.setString(1, objAgendaDTO.getCliente());
            pst.setString(2, objAgendaDTO.getData());
            pst.setString(3, objAgendaDTO.getHora());
            pst.setString(4, objAgendaDTO.getDescricao());

            // Executa a atualização
            int add = pst.executeUpdate();

            // Verifica se a atualização foi bem-sucedida
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Agenda editada com sucesso!");
                apagarCampos(); // Limpa os campos da tela
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar agenda: " + e.getMessage());
        }
    }
    
    // Método para apagar os campos da interface gráfica após uma operação
    public void apagarCampos() {
        // Limpa os campos da tela de agenda
        TelaAgenda.txtId.setText(null);
        TelaAgenda.txtCliente.setText(null);
        TelaAgenda.txtData.setText(null);
        TelaAgenda.txtHora.setText(null);
        TelaAgenda.txtDescricao.setText(null);
    }
    
    // Método para excluir uma agenda do banco de dados
    public void apagar(AgendaDTO objAgendaDTO) {
        // Comando SQL para excluir uma agenda com base no ID
        String sql = "delete from tb_agenda where id = ?";
        conexao = ConexaoDAO.conector();

        try {
            // Prepara o comando SQL para exclusão
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objAgendaDTO.getId());
            int add = pst.executeUpdate();

            // Verifica se a exclusão foi bem-sucedida
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Agenda excluída com sucesso!");
                apagarCampos(); // Limpa os campos da tela
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar agenda: " + e.getMessage());
        }
    }
    
    // Método para pesquisar uma agenda no banco de dados com base no ID
    public void pesquisar(AgendaDTO objAgendaDTO) {
        // Comando SQL para buscar uma agenda
        String sql = "select * from tb_Clientes where id_clientes = ?";
        conexao = ConexaoDAO.conector();

        try {
            // Prepara o comando SQL para pesquisa
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objAgendaDTO.getId());
            rs = pst.executeQuery();

            // Se encontrar o registro, preenche os campos da interface
            if (rs.next()) {
                TelaAgenda.txtCliente.setText(rs.getString(2));
                TelaAgenda.txtData.setText(rs.getString(3));
                TelaAgenda.txtHora.setText(rs.getString(4));
                TelaAgenda.txtDescricao.setText(rs.getString(5));
                conexao.close();
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não cadastrado!");
                apagarCampos(); // Limpa os campos se não encontrar o cliente
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar agenda: " + e.getMessage());
        }
    }
}
