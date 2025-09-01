package View;

import DAO.FuncionarioDAO;
import Model.ModelFuncionario;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;

public class telaFuncionarios extends JFrame {
    private JPanel painelFuncionarios;
    private JPanel painelTitulo;
    private JLabel lbTitulo;
    private JPanel painelBotoes;
    private JButton buttonCadastrar;
    private JButton buttonCancelar;
    private JButton buttonSalvar;
    private JPanel painelDados;
    private JLabel lbNome;
    private JLabel lbFunção;
    private JLabel lbCpf;
    private JLabel lbTelefone;
    private JTextField textFieldNome;
    private JTextField textFieldCargo;
    private JTextField textFieldCpf;
    private JTextField textFieldTelefone;
    private JButton buttonEditar;
    private JButton buttonDeletar;
    private JPanel painelTable;
    private JScrollPane JScrollPane;
    private JTable table1;

    private Integer funcionarioSelecionadoId = null;

    public telaFuncionarios() {
        setTitle("Tela Funcionários");
        setContentPane(painelFuncionarios);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarTabela();
        setVisible(true);

        table1.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
                int linha = table1.getSelectedRow();
                funcionarioSelecionadoId = (Integer) table1.getValueAt(linha, 0);
                textFieldNome.setText((String) table1.getValueAt(linha, 1));
                textFieldCargo.setText((String) table1.getValueAt(linha, 2));
                textFieldCpf.setText((String) table1.getValueAt(linha, 3));
                textFieldTelefone.setText((String) table1.getValueAt(linha, 4));
            }
        });

        buttonCadastrar.addActionListener((ActionEvent e) -> {
            limparCampos();
            funcionarioSelecionadoId = null;
            textFieldNome.requestFocus();
        });

        buttonSalvar.addActionListener((ActionEvent e) -> {
            if (validarCampos()) {
                ModelFuncionario funcionario = new ModelFuncionario();
                funcionario.setNome(textFieldNome.getText());
                funcionario.setCargo(textFieldCargo.getText());
                funcionario.setCpf(textFieldCpf.getText());
                funcionario.setTelefone(textFieldTelefone.getText());

                FuncionarioDAO dao = new FuncionarioDAO();
                dao.create(funcionario);

                JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!");
                limparCampos();
                carregarTabela();
            }
        });

        buttonEditar.addActionListener((ActionEvent e) -> {
            if (funcionarioSelecionadoId == null) {
                JOptionPane.showMessageDialog(null, "Selecione um funcionário para editar.");
                return;
            }

            if (validarCampos()) {
                ModelFuncionario funcionario = new ModelFuncionario();
                funcionario.setId(funcionarioSelecionadoId);
                funcionario.setNome(textFieldNome.getText());
                funcionario.setCargo(textFieldCargo.getText());
                funcionario.setCpf(textFieldCpf.getText());
                funcionario.setTelefone(textFieldTelefone.getText());

                FuncionarioDAO dao = new FuncionarioDAO();
                dao.update(funcionario);

                JOptionPane.showMessageDialog(null, "Funcionário atualizado com sucesso!");
                limparCampos();
                carregarTabela();
                funcionarioSelecionadoId = null;
                table1.clearSelection();
            }
        });

        buttonDeletar.addActionListener((ActionEvent e) -> {
            if (funcionarioSelecionadoId == null) {
                JOptionPane.showMessageDialog(null, "Selecione um funcionário para deletar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente deletar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                FuncionarioDAO dao = new FuncionarioDAO();
                dao.delete(funcionarioSelecionadoId);
                JOptionPane.showMessageDialog(null, "Funcionário deletado com sucesso!");
                carregarTabela();
                limparCampos();
                funcionarioSelecionadoId = null;
            }
        });

        buttonCancelar.addActionListener((ActionEvent e) -> {
            limparCampos();
            funcionarioSelecionadoId = null;
            table1.clearSelection();
            dispose();

        });
    }

    private void carregarTabela() {
        FuncionarioDAO dao = new FuncionarioDAO();
        List<ModelFuncionario> funcionarios = dao.read();

        String[] colunas = {"ID", "Nome", "Cargo", "CPF", "Telefone"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (ModelFuncionario f : funcionarios) {
            Object[] linha = {
                    f.getId(),
                    f.getNome(),
                    f.getCargo(),
                    f.getCpf(),
                    f.getTelefone()
            };
            model.addRow(linha);
        }

        table1.setModel(model);
        table1.getColumnModel().getColumn(0).setPreferredWidth(30);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void limparCampos() {
        textFieldNome.setText("");
        textFieldCargo.setText("");
        textFieldCpf.setText("");
        textFieldTelefone.setText("");
        table1.clearSelection();
    }

    private boolean validarCampos() {
        if (textFieldNome.getText().trim().isEmpty() ||
                textFieldCargo.getText().trim().isEmpty() ||
                textFieldCpf.getText().trim().isEmpty() ||
                textFieldTelefone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
            return false;
        }
        return true;
    }
}
