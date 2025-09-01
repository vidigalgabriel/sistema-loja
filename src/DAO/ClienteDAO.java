package DAO;

import Model.ModelCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void create(ModelCliente cliente) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "INSERT INTO clientes (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }
    }

    public ModelCliente readById(int id) {
        Connection conexao = FabricaConexao.conectar();
        ModelCliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new ModelCliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }

        return cliente;
    }
    public List<ModelCliente> read() {
        Connection conexao = FabricaConexao.conectar();
        List<ModelCliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ModelCliente p = new ModelCliente();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }

        return lista;
    }

    public void update(ModelCliente cliente) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "UPDATE clientes SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, cliente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }
    }

    public void delete(int id) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "DELETE FROM clientes WHERE id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }
    }
}
