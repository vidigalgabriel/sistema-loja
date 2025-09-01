package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.telaVendas;

public class telaHome extends JFrame {
    public JPanel painelHome;
    private JPanel painelTopo;
    private JLabel labelTitulo;
    private JPanel painelBaixo;
    private JMenuBar jmb1;
    private JMenu jmVendas;
    private JMenu jmProdutos;
    private JMenu jmClientes;
    private JMenu jmFuncionarios;
    private JMenu jmRelatórios;
    private JMenu jmSair;
    private JLabel lbImagem;
    private JPanel painelMeio;
    private JLabel lbgif2;
    private JMenuItem jmiSair;
    private JMenuItem jmiVendas;
    private JMenuItem jmiProdutos;
    private JMenuItem jmiClientes;
    private JMenuItem jmiFuncionarios;
    private JMenuItem jmiRelatorios;


    public telaHome(){
        setTitle("Tela Home");
        setContentPane(painelHome);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        jmiSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(
                        null,
                        "Tem certeza que deseja sair?",
                        "Confirmação de saída",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (resposta == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        jmiVendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new telaVendas();
            }

        });
        jmiProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new telaProdutos();
            }

        });
        jmiClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new telaClientes();
            }

        });
        jmiFuncionarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new telaFuncionarios();
            }

        });
        jmiRelatorios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new telaRelatorios();
            }

        });
    }
}
