package View;

import DAO.ClienteDAO;
import DAO.FuncionarioDAO;
import DAO.ProdutoDAO;
import Model.ModelCliente;
import Model.ModelFuncionario;
import Model.ModelProduto;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class telaVendas extends JFrame {
    public JPanel painelVendas;
    public JPanel painelLateral;
    private JButton buttonAdicionar;
    private JLabel lbTitulo;
    private JPanel painelBotoes;
    private JButton buttonNovaVenda;
    private JButton buttonCancelar;
    private JButton buttonVender;
    private JLabel lbVendedor;
    private JComboBox<ModelFuncionario> comboBoxVendedor;
    private JLabel lbData;
    private JTextField textFieldData;
    private JLabel lbCodigo;
    private JTextField textFieldCodigo;
    private JPanel painelCliente;
    private JLabel lbCliente;
    private JComboBox<ModelCliente> comboBoxCliente;
    private JLabel lbPagamento;
    private JRadioButton radioButtonPix;
    private JRadioButton radioButtonDinheiro;
    private JRadioButton radioButtonDebito;
    private JRadioButton radioButtonCrédito;
    private JPanel painelProduto;
    private JLabel lbDescricao;
    private JComboBox<ModelProduto> comboBoxDescricao;
    private JLabel lbCodig;
    private JTextField textFieldCodigoPro;
    private JLabel lbValor;
    private JTextField textFieldValor;
    private JLabel lbQuantidade;
    private JTextField textFieldQuantidade;
    private JLabel lbTotal;
    private JTextField textFieldTotal;
    private JLabel labelCarrinho;

    private double acumulado = 0.0;
    private StringBuilder itensSelecionados = new StringBuilder("Itens selecionados:<br>");

    public telaVendas() {
        setTitle("Tela Vendas");
        setContentPane(painelVendas);
        setSize(1000, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarProdutos();
        carregarClientes();
        carregarVendedores();
        gerarCodigoVenda();

        textFieldData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));

        setCamposAtivos(false);
        setVisible(true);

        buttonCancelar.addActionListener(e -> dispose());

        buttonNovaVenda.addActionListener(e -> {
            setCamposAtivos(true);
            gerarCodigoVenda();
            textFieldData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
        });

        buttonVender.addActionListener(e -> salvarVenda());

        buttonAdicionar.addActionListener(e -> {
            String nomeProduto = comboBoxDescricao.getSelectedItem().toString();
            String preco = textFieldValor.getText();
            String quantidade = textFieldQuantidade.getText();

            if (!preco.isEmpty() && !quantidade.isEmpty()) {
                double precoUnit = Double.parseDouble(preco.replace(",", "."));
                int qtd = Integer.parseInt(quantidade);
                double totalItem = precoUnit * qtd;

                acumulado += totalItem;
                labelCarrinho.setText("<html>" + itensSelecionados.toString()
                        + nomeProduto + " - " + qtd + " x R$" + String.format("%.2f", precoUnit) + " = R$" + String.format("%.2f", totalItem) + "<br>"
                        + "<br><b>Total: R$" + String.format("%.2f", acumulado) + "</b></html>");

                itensSelecionados.append(nomeProduto).append(" - ").append(qtd)
                        .append(" x R$").append(String.format("%.2f", precoUnit))
                        .append(" = R$").append(String.format("%.2f", totalItem)).append("<br>");
            }
        });
    }

    private void carregarProdutos() {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<ModelProduto> produtos = produtoDAO.read();

        if (comboBoxDescricao != null) {
            comboBoxDescricao.removeAllItems();
            for (ModelProduto p : produtos) {
                comboBoxDescricao.addItem(p);
            }

            textFieldQuantidade.setText("1");

            textFieldQuantidade.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) { atualizarTotal(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { atualizarTotal(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { atualizarTotal(); }
            });

            comboBoxDescricao.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    atualizarCamposProduto();
                    atualizarTotal();
                }
            });
        }
    }

    private void carregarClientes() {
        ClienteDAO clienteDAO = new ClienteDAO();
        List<ModelCliente> clientes = clienteDAO.read();

        if (comboBoxCliente != null) {
            comboBoxCliente.removeAllItems();
            for (ModelCliente c : clientes) {
                comboBoxCliente.addItem(c);
            }
        }
    }

    private void carregarVendedores() {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<ModelFuncionario> funcionarios = funcionarioDAO.read();

        if (comboBoxVendedor != null) {
            comboBoxVendedor.removeAllItems();
            for (ModelFuncionario f : funcionarios) {
                comboBoxVendedor.addItem(f);
            }
        }
    }

    private void gerarCodigoVenda() {
        String codigoVenda = "VENDA-" + ((int) (Math.random() * 9000) + 1000);
        textFieldCodigo.setText(codigoVenda);
    }

    private void atualizarCamposProduto() {
        ModelProduto produto = (ModelProduto) comboBoxDescricao.getSelectedItem();
        if (produto != null) {
            textFieldCodigoPro.setText(String.valueOf(produto.getId()));
            textFieldValor.setText(String.format("%.2f", produto.getPreco()));
        }
    }

    private void atualizarTotal() {
        try {
            int qtd = Integer.parseInt(textFieldQuantidade.getText());
            ModelProduto produto = (ModelProduto) comboBoxDescricao.getSelectedItem();
            if (produto != null) {
                double total = produto.getPreco() * qtd;
                textFieldTotal.setText(String.format("%.2f", total));
            }
        } catch (NumberFormatException ex) {
            textFieldTotal.setText("0,00");
        }
    }

    private void setCamposAtivos(boolean ativo) {
        textFieldCodigo.setEnabled(false);
        comboBoxVendedor.setEnabled(ativo);
        textFieldData.setEnabled(ativo);
        comboBoxCliente.setEnabled(ativo);
        radioButtonPix.setEnabled(ativo);
        radioButtonDinheiro.setEnabled(ativo);
        radioButtonDebito.setEnabled(ativo);
        radioButtonCrédito.setEnabled(ativo);
        comboBoxDescricao.setEnabled(ativo);
        textFieldCodigoPro.setEnabled(false);
        textFieldValor.setEnabled(false);
        textFieldQuantidade.setEnabled(ativo);
        textFieldTotal.setEnabled(false);
        buttonCancelar.setEnabled(ativo);
        buttonVender.setEnabled(ativo);
        buttonAdicionar.setEnabled(ativo);
    }

    private void salvarVenda() {
        if (comboBoxCliente.getSelectedItem() == null || comboBoxVendedor.getSelectedItem() == null || comboBoxDescricao.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
            return;
        }

        if (textFieldQuantidade.getText().isEmpty() || textFieldData.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a quantidade e a data da venda.");
            return;
        }

        try {
            int quantidade = Integer.parseInt(textFieldQuantidade.getText());
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            return;
        }

        String codigo = textFieldCodigo.getText();
        String dataStr = textFieldData.getText();
        Date dataVenda;

        try {
            java.util.Date dataUtil = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
            dataVenda = new Date(dataUtil.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.");
            return;
        }

        ModelCliente cliente = (ModelCliente) comboBoxCliente.getSelectedItem();
        ModelFuncionario funcionario = (ModelFuncionario) comboBoxVendedor.getSelectedItem();
        ModelProduto produto = (ModelProduto) comboBoxDescricao.getSelectedItem();

        int quantidade = Integer.parseInt(textFieldQuantidade.getText());
        double valorUnitario = produto.getPreco();
        double total;

        try {
            total = Double.parseDouble(textFieldTotal.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            total = valorUnitario * quantidade;
        }

        String formaPagamento = null;
        if (radioButtonPix.isSelected()) formaPagamento = "Pix";
        else if (radioButtonDinheiro.isSelected()) formaPagamento = "Dinheiro";
        else if (radioButtonDebito.isSelected()) formaPagamento = "Débito";
        else if (radioButtonCrédito.isSelected()) formaPagamento = "Crédito";

        if (formaPagamento == null) {
            JOptionPane.showMessageDialog(this, "Selecione a forma de pagamento.");
            return;
        }

        String sql = "INSERT INTO vendas (codigo, data_venda, id_cliente, id_funcionario, id_produto, quantidade, valor_unitario, total, forma_pagamento, nome_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DAO.FabricaConexao.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, codigo);
            pst.setDate(2, dataVenda);
            pst.setInt(3, cliente.getId());
            pst.setInt(4, funcionario.getId());
            pst.setInt(5, produto.getId());
            pst.setInt(6, quantidade);
            pst.setDouble(7, valorUnitario);
            pst.setDouble(8, total);
            pst.setString(9, formaPagamento);
            pst.setString(10, cliente.getNome());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Venda salva com sucesso!");
                setCamposAtivos(false);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a venda.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar no banco: " + e.getMessage());
        }
    }
}
