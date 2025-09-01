package DAO;

import Model.ModelVendaProduto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class VendaProdutoDAO {

    public void inserirProdutosDoPedido(int idPedido, List<ModelVendaProduto> produtos) {
        Connection conexao = FabricaConexao.conectar();
        String sql = "INSERT INTO pedido_produto (id_pedido, id_produto, quantidade, valor) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);

            for (ModelVendaProduto p : produtos) {
                stmt.setInt(1, idPedido);
                stmt.setInt(2, p.getIdProduto());
                stmt.setInt(3, p.getQuantidade());
                stmt.setDouble(4, p.getValor());
                stmt.addBatch();
            }

            stmt.executeBatch();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }
    }
}
