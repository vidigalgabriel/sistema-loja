package View;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class telaRelatorios extends JFrame {
    private JPanel painelRelatorios;
    private JPanel telaRelatorios;
    private JLabel lbTitulo;
    private JPanel telaBotoes;
    private JButton buttonGerar;
    private JScrollPane jScrollPane;
    private JTable table1;

    private void gerarRelatorioVendas() {
        String url = "jdbc:mysql://localhost:3306/lojaTech";
        String user = "root";
        String password = "123456";

        String sql = "SELECT " +
                "v.id AS venda_id, " +
                "v.codigo, " +
                "v.data_venda, " +
                "c.nome AS nome_cliente, " +
                "f.nome AS nome_funcionario, " +
                "p.nome AS nome_produto, " +
                "v.quantidade, " +
                "v.valor_unitario, " +
                "v.total, " +
                "v.forma_pagamento " +
                "FROM vendas v " +
                "JOIN clientes c ON v.id_cliente = c.id " +
                "JOIN funcionarios f ON v.id_funcionario = f.id " +
                "JOIN produtos p ON v.id_produto = p.id " +
                "ORDER BY v.data_venda DESC";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Código");
            model.addColumn("Data");
            model.addColumn("Cliente");
            model.addColumn("Funcionário");
            model.addColumn("Produto");
            model.addColumn("Quantidade");
            model.addColumn("Valor Unitário");
            model.addColumn("Total");
            model.addColumn("Pagamento");

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("venda_id"),
                        rs.getString("codigo"),
                        rs.getDate("data_venda"),
                        rs.getString("nome_cliente"),
                        rs.getString("nome_funcionario"),
                        rs.getString("nome_produto"),
                        rs.getInt("quantidade"),
                        rs.getDouble("valor_unitario"),
                        rs.getDouble("total"),
                        rs.getString("forma_pagamento")
                };
                model.addRow(row);
            }

            table1.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public telaRelatorios() {
        setTitle("Tela Relatórios");
        setContentPane(painelRelatorios);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        buttonGerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarRelatorioVendas();
            }
        });
    }
}
