package DAO;

import Model.ModelFuncionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void create(ModelFuncionario funcionario) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "INSERT INTO funcionarios (nome, cargo, cpf, telefone) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getCpf());
            stmt.setString(4, funcionario.getTelefone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }
    }

    public List<ModelFuncionario> read() {
        Connection conexao = FabricaConexao.conectar();
        List<ModelFuncionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ModelFuncionario f = new ModelFuncionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCargo(rs.getString("cargo"));
                f.setCpf(rs.getString("cpf"));
                f.setTelefone(rs.getString("telefone"));
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }

        return lista;
    }

    public void update(ModelFuncionario funcionario) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "UPDATE funcionarios SET nome = ?, cargo = ?, cpf = ?, telefone = ? WHERE id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getCpf());
            stmt.setString(4, funcionario.getTelefone());
            stmt.setInt(5, funcionario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }
    }

    public void delete(int id) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "DELETE FROM funcionarios WHERE id = ?";

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
