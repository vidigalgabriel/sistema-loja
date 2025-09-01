package View;

import DAO.LoginDAO;
import Model.ModelUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class telaLogin extends JFrame{
    private JPanel painelLogin;
    private JPanel painelTopo;
    private JLabel lbLogin;
    private JPanel painelBaixo;
    private JLabel lbUsuario;
    private JLabel lbSenha;
    private JLabel lbPerfil;
    private JTextField textFieldUsuario;
    private JPasswordField passwordFieldSenha;
    private JComboBox comboBoxUsuario;
    private JButton buttonCancelar;
    private JButton buttonEntrar;
    private JLabel lbGif;

    public telaLogin() {

        setTitle("Tela Login");
        setContentPane(painelLogin);
        setSize(600,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);



        buttonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textFieldUsuario.getText();
                String senha = new String(passwordFieldSenha.getPassword());
                String perfil = String.valueOf(comboBoxUsuario.getSelectedItem());

                LoginDAO loginDAO = new LoginDAO();
                ModelUsuario resultado = loginDAO.validarLogin(usuario, senha, perfil);

                if (resultado != null) {
                    System.out.println("Login bem sucedido!");

                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(painelLogin);
                    frame.dispose();

                    SwingUtilities.invokeLater(() -> {
                        telaHome home = new telaHome();
                        home.setVisible(true);

                       if (resultado.getPerfil().equals("user")) {
                           /* home.jmiCadastrar.setEnabled(false);
                            home.setVisible(true);*/
                            System.out.println("Usuário comum logado, acesso restrito.");
                        } else {
                            home.setVisible(true);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(painelLogin, "Usuário ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
