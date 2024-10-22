package br.com.DAO;

import br.com.DTO.ClientesDTO;
import br.com.Views.TelaClientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClientesDAO {

    public static ClientesDAO objClientesDAO; // Instância estática para acessar o DAO
    
    // Declaração dos objetos de conexão e manipulação do banco de dados
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    // Método para inserir um novo cliente no banco de dados
    public void inserirClientes(ClientesDTO objClientesDTO) {
        // Definindo o SQL de inserção
        String sql = "INSERT INTO tb_Clientes(id_clientes, nome, endereco, telefone, email, cpf_cnpj) VALUES(?, ?, ?, ?, ?, ?)";
        
        // Estabelecendo a conexão
        conexao = new ConexaoDAO().conector();

        try {
            // Verificando se o ID do cliente é menor que zero (erro de entrada)
            if (objClientesDTO.getId_clientes() < 0) {
                JOptionPane.showMessageDialog(null, "Erro: ID do cliente não pode ser menor que 0.");
                return; // Encerra o método se houver erro
            }

            // Verificando se todos os campos obrigatórios foram preenchidos
            if (objClientesDTO.getNome_clientes().isEmpty()
                || objClientesDTO.getEndereco().isEmpty()
                || objClientesDTO.getTelefone().isEmpty()
                || objClientesDTO.getEmail_clientes().isEmpty()
                || objClientesDTO.getCpf_cnpj().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Erro: Todos os campos são obrigatórios.");
                return;
            }

            // Preparando a query de inserção
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objClientesDTO.getId_clientes());
            pst.setString(2, objClientesDTO.getNome_clientes());
            pst.setString(3, objClientesDTO.getEndereco());
            pst.setString(4, objClientesDTO.getTelefone());
            pst.setString(5, objClientesDTO.getEmail_clientes());
            pst.setString(6, objClientesDTO.getCpf_cnpj());

            // Executando a query e verificando o resultado
            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro: Cliente já existe ou não foi inserido.");
            }

            pst.close();
        } catch (SQLException e) {
            // Tratamento específico para erros de duplicação de entrada
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Erro: Cliente já existe.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir Cliente: " + e.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        } finally {
            // Fechando a conexão no bloco finally
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
    
    // Método para pesquisar um cliente no banco de dados com base no ID
    public void pesquisar(ClientesDTO objClienteDTO) {
        String sql = "select * from tb_Clientes where id_clientes = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objClienteDTO.getId_clientes());
            rs = pst.executeQuery();
            
            // Se encontrar o cliente, preencher os campos da tela
            if (rs.next()) {
                TelaClientes.txtNomeCli.setText(rs.getString(2));
                TelaClientes.txtEndCli.setText(rs.getString(3));
                TelaClientes.txtTelefone.setText(rs.getString(4));
                TelaClientes.txtEmail.setText(rs.getString(5));
                TelaClientes.txtCpf.setText(rs.getString(6));
                conexao.close();
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não cadastrado!");
                apagarCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método Pesquisar: " + e);
        }
    }

    // Método para editar as informações de um cliente existente
    public void editar(ClientesDTO objClientesDTO) {
        String sql = "update tb_Clientes set nome=?, endereco=?, telefone=?, email=?, cpf_cnpj=? where id_clientes = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(6, objClientesDTO.getId_clientes());
            pst.setString(1, objClientesDTO.getNome_clientes());
            pst.setString(2, objClientesDTO.getEndereco());
            pst.setString(3, objClientesDTO.getTelefone());
            pst.setString(4, objClientesDTO.getEmail_clientes());
            pst.setString(5, objClientesDTO.getCpf_cnpj());

            int add = pst.executeUpdate();
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Cliente Editado com sucesso!");
                apagarCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método editar: " + e);
        }
    }

    // Método para apagar os campos da tela após alguma ação
    public void apagarCampos() {
        TelaClientes.txtId.setText(null);
        TelaClientes.txtNomeCli.setText(null);
        TelaClientes.txtEndCli.setText(null);
        TelaClientes.txtTelefone.setText(null);
        TelaClientes.txtEmail.setText(null);
        TelaClientes.txtCpf.setText(null);
    }
    
    // Método para apagar um cliente do banco de dados com base no ID
    public void apagar(ClientesDTO objClientesDTO) {
        String sql = "delete from tb_Clientes where id_clientes = ?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objClientesDTO.getId_clientes());
            int add = pst.executeUpdate();
            if (add > 0) {
                conexao.close();
                JOptionPane.showMessageDialog(null, "Cliente Excluído com sucesso!");
                apagarCampos();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método apagar: " + e);
        }
    }
}
