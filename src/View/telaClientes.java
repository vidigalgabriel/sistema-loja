package View;

import DAO.ClienteDAO;
import Model.ModelCliente;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class telaClientes extends JFrame {
    private JPanel painelClientes;
    private JPanel painelTopo;
    private JLabel lbClientes;
    private JPanel painelBotoes;
    private JButton buttonAdicionar;
    private JButton buttonCancelar;
    private JButton buttonSalvar;
    private JButton buttonLista;
    private JButton buttonEditar;
    private JButton buttonDeletar;
    private JPanel painelBaixo;
    private JLabel lbNome;
    private JLabel lbCpf;
    private JLabel lbTelefone;
    private JLabel lbEmail;
    private JTextField textFieldNome;
    private JTextField textFieldCpf;
    private JTextField textFieldTelefone;
    private JTextField textFieldEmail;
    private JPanel painelTable;
    private JScrollPane JScrollPane;
    private JTable table1;

    private Integer clienteSelecionadoId = null;

    public telaClientes() {
        setTitle("Tela Clientes");
        setContentPane(painelClientes);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarTabela();
        setVisible(true);

        buttonAdicionar.addActionListener(e -> {
            limparCampos();
            clienteSelecionadoId = null;
            textFieldNome.requestFocus();
        });

        buttonSalvar.addActionListener(e -> {
            if (validarCampos()) {
                ModelCliente cliente = new ModelCliente();
                cliente.setNome(textFieldNome.getText());
                cliente.setCpf(textFieldCpf.getText());
                cliente.setTelefone(textFieldTelefone.getText());
                cliente.setEmail(textFieldEmail.getText());

                ClienteDAO dao = new ClienteDAO();
                dao.create(cliente);

                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                limparCampos();
                carregarTabela();
            }
        });

        buttonEditar.addActionListener(e -> {
            if (clienteSelecionadoId == null) {
                JOptionPane.showMessageDialog(null, "Selecione um cliente para editar.");
                return;
            }
            if (validarCampos()) {
                ModelCliente cliente = new ModelCliente();
                cliente.setId(clienteSelecionadoId);
                cliente.setNome(textFieldNome.getText());
                cliente.setCpf(textFieldCpf.getText());
                cliente.setTelefone(textFieldTelefone.getText());
                cliente.setEmail(textFieldEmail.getText());

                ClienteDAO dao = new ClienteDAO();
                dao.update(cliente);

                JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");
                limparCampos();
                carregarTabela();
                clienteSelecionadoId = null;
                table1.clearSelection();
            }
        });

        buttonDeletar.addActionListener(e -> {
            if (clienteSelecionadoId == null) {
                JOptionPane.showMessageDialog(null, "Selecione um cliente para deletar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Tem certeza que deseja deletar o cliente selecionado?", "Confirmar deleção",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                ClienteDAO dao = new ClienteDAO();
                dao.delete(clienteSelecionadoId);

                JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");
                limparCampos();
                carregarTabela();
                clienteSelecionadoId = null;
                table1.clearSelection();
            }
        });

        buttonCancelar.addActionListener(e -> {
            limparCampos();
            table1.clearSelection();
            clienteSelecionadoId = null;
            dispose();

        });

        table1.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
                int linha = table1.getSelectedRow();
                clienteSelecionadoId = (Integer) table1.getValueAt(linha, 0);
                textFieldNome.setText((String) table1.getValueAt(linha, 1));
                textFieldCpf.setText((String) table1.getValueAt(linha, 2));
                textFieldTelefone.setText((String) table1.getValueAt(linha, 3));
                textFieldEmail.setText((String) table1.getValueAt(linha, 4));
            }
        });
    }

    private void carregarTabela() {
        ClienteDAO dao = new ClienteDAO();
        List<ModelCliente> lista = dao.read();

        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Email"};
        DefaultTableModel model = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (ModelCliente cliente : lista) {
            Object[] linha = {
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEmail()
            };
            model.addRow(linha);
        }

        table1.setModel(model);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void limparCampos() {
        textFieldNome.setText("");
        textFieldCpf.setText("");
        textFieldTelefone.setText("");
        textFieldEmail.setText("");
    }

    private boolean validarCampos() {
        if (textFieldNome.getText().trim().isEmpty() ||
                textFieldCpf.getText().trim().isEmpty() ||
                textFieldTelefone.getText().trim().isEmpty() ||
                textFieldEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
            return false;
        }
        return true;
    }
}
