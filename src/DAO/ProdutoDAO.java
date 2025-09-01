package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.ModelProduto;

public class ProdutoDAO {

    public void create(ModelProduto produto) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "INSERT INTO produtos (nome, quantidade, valor) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getPreco());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }
    }

    public List<ModelProduto> read() {
        Connection conexao = FabricaConexao.conectar();
        List<ModelProduto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ModelProduto p = new ModelProduto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setPreco(rs.getDouble("valor"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }

        return lista;
    }

    public void update(ModelProduto produto) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "UPDATE produtos SET nome = ?, quantidade = ?, valor = ? WHERE id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }
    }

    public void delete(int id) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "DELETE FROM produtos WHERE id = ?";

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
