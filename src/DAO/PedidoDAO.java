package DAO;

import Model.ModelPedido;
import Model.ModelVendaProduto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoDAO {

    public int inserirPedido(ModelPedido pedido) {
        Connection conexao = FabricaConexao.conectar();
        int idPedido = -1;
        String sql = "INSERT INTO pedidos (id_cliente, id_funcionario, data, valor_total) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdFuncionario());
            stmt.setDate(3, new java.sql.Date(pedido.getData().getTime()));
            stmt.setDouble(4, pedido.getValorTotal());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idPedido = rs.getInt(1);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }

        return idPedido;
    }

    public List<ModelPedido> listarPedidos() {
        Connection conexao = FabricaConexao.conectar();
        List<ModelPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ModelPedido pedido = new ModelPedido();
                pedido.setId(rs.getInt("id"));
                pedido.setIdCliente(rs.getInt("id_cliente"));
                pedido.setIdFuncionario(rs.getInt("id_funcionario"));
                pedido.setData(rs.getDate("data"));
                pedido.setValorTotal(rs.getDouble("valor_total"));

                lista.add(pedido);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FabricaConexao.desconectar(conexao);
        }

        return lista;
    }
}
