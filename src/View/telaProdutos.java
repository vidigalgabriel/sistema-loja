package View;

import DAO.ProdutoDAO;
import Model.ModelProduto;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class telaProdutos extends JFrame {
    private JPanel painelProdutos;
    private JPanel PainelTopo;
    private JLabel lbProdutos;
    private JPanel painelBotoes;
    private JButton buttonCadastrar;
    private JButton buttonCancelar;
    private JButton buttonSalvar;
    private JButton buttonEstoque;
    private JPanel painelBaixo;
    private JLabel lbDescricao;
    private JLabel lbQuantidade;
    private JLabel lbValor;
    private JTextField textFieldNome;
    private JTextField textFieldQuantidade;
    private JTextField textFieldValor;
    private JPanel painelTable;
    private JScrollPane JScrollPane;
    private JTable table1;
    private JButton buttonEditar;
    private JButton buttonDeletar;

    private ProdutoDAO produtoDAO;
    private DefaultTableModel tableModel;
    private Integer produtoSelecionadoId = null;

    public telaProdutos() {
        setContentPane(painelProdutos);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        produtoDAO = new ProdutoDAO();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Quantidade", "Valor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);
        table1.getColumnModel().getColumn(0).setPreferredWidth(30);
        carregarTabela();

        table1.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
                int linha = table1.getSelectedRow();
                produtoSelecionadoId = (int) tableModel.getValueAt(linha, 0);
                textFieldNome.setText((String) tableModel.getValueAt(linha, 1));
                textFieldQuantidade.setText(String.valueOf(tableModel.getValueAt(linha, 2)));
                textFieldValor.setText(String.format("%.2f", (double) tableModel.getValueAt(linha, 3)));
            }
        });

        buttonCadastrar.addActionListener((ActionEvent e) -> {
            limparCampos();
            produtoSelecionadoId = null;
            textFieldNome.requestFocus();
        });

        buttonSalvar.addActionListener((ActionEvent e) -> {
            if (validarCampos()) {
                ModelProduto produto = new ModelProduto();
                produto.setNome(textFieldNome.getText().trim());
                produto.setQuantidade(Integer.parseInt(textFieldQuantidade.getText().trim()));
                produto.setPreco(Double.parseDouble(textFieldValor.getText().trim().replace(",", ".")));

                if (produtoSelecionadoId == null) {
                    produtoDAO.create(produto);
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                } else {
                    produto.setId(produtoSelecionadoId);
                    produtoDAO.update(produto);
                    JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
                }

                carregarTabela();
                limparCampos();
                produtoSelecionadoId = null;
            }
        });

        buttonEditar.addActionListener((ActionEvent e) -> {
            if (produtoSelecionadoId == null) {
                JOptionPane.showMessageDialog(null, "Selecione um produto para editar.");
                return;
            }

            if (validarCampos()) {
                ModelProduto produto = new ModelProduto();
                produto.setId(produtoSelecionadoId);
                produto.setNome(textFieldNome.getText().trim());
                produto.setQuantidade(Integer.parseInt(textFieldQuantidade.getText().trim()));
                produto.setPreco(Double.parseDouble(textFieldValor.getText().trim().replace(",", ".")));

                produtoDAO.update(produto);
                JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
                carregarTabela();
                limparCampos();
                produtoSelecionadoId = null;
            }
        });

        buttonDeletar.addActionListener((ActionEvent e) -> {
            if (produtoSelecionadoId == null) {
                JOptionPane.showMessageDialog(null, "Selecione um produto para deletar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar o produto selecionado?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                produtoDAO.delete(produtoSelecionadoId);
                JOptionPane.showMessageDialog(null, "Produto deletado com sucesso!");
                carregarTabela();
                limparCampos();
                produtoSelecionadoId = null;
            }
        });

        buttonCancelar.addActionListener((ActionEvent e) -> {
            limparCampos();
            produtoSelecionadoId = null;
            table1.clearSelection();
            dispose();

        });
    }

    private void carregarTabela() {
        List<ModelProduto> produtos = produtoDAO.read();
        tableModel.setRowCount(0);
        for (ModelProduto p : produtos) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getQuantidade(),
                    p.getPreco()
            });
        }
    }

    private void limparCampos() {
        textFieldNome.setText("");
        textFieldQuantidade.setText("");
        textFieldValor.setText("");
        table1.clearSelection();
    }

    private boolean validarCampos() {
        String nome = textFieldNome.getText().trim();
        String qtdStr = textFieldQuantidade.getText().trim();
        String valorStr = textFieldValor.getText().trim().replace(",", ".");

        if (nome.isEmpty() || qtdStr.isEmpty() || valorStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
            return false;
        }

        try {
            int qtd = Integer.parseInt(qtdStr);
            double valor = Double.parseDouble(valorStr);
            if (qtd < 0 || valor < 0) {
                JOptionPane.showMessageDialog(null, "Quantidade e valor devem ser positivos.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Quantidade e valor devem ser válidos.");
            return false;
        }

        return true;
    }
}
