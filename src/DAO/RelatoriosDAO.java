package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Model.ModelVendaRelatorio;

public class RelatoriosDAO {

    public List<ModelVendaRelatorio> listarVendasComDetalhes() {
        List<ModelVendaRelatorio> lista = new ArrayList<>();
        String sql = "SELECT " +
                "v.id AS venda_id, " +
                "v.codigo, " +
                "v.data_venda, " +
                "v.id_cliente, " +
                "c.nome AS nome_cliente, " +
                "v.id_funcionario, " +
                "f.nome AS nome_funcionario, " +
                "v.id_produto, " +
                "v.quantidade, " +
                "v.valor_unitario, " +
                "v.total, " +
                "v.forma_pagamento " +
                "FROM vendas v " +
                "JOIN clientes c ON v.id_cliente = c.id " +
                "JOIN funcionarios f ON v.id_funcionario = f.id " +
                "ORDER BY v.data_venda DESC";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelVendaRelatorio venda = new ModelVendaRelatorio();
                venda.setId(rs.getInt("venda_id"));
                venda.setCodigo(rs.getString("codigo"));
                venda.setDataVenda(rs.getString("data_venda"));
                venda.setIdCliente(rs.getInt("id_cliente"));
                venda.setNomeCliente(rs.getString("nome_cliente"));
                venda.setIdFuncionario(rs.getInt("id_funcionario"));
                venda.setNomeFuncionario(rs.getString("nome_funcionario"));
                venda.setIdProduto(rs.getInt("id_produto"));
                venda.setQuantidade(rs.getInt("quantidade"));
                venda.setValorUnitario(rs.getDouble("valor_unitario"));
                venda.setTotal(rs.getDouble("total"));
                venda.setFormaPagamento(rs.getString("forma_pagamento"));

                lista.add(venda);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
