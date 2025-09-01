package DAO;

import Model.ModelUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public ModelUsuario validarLogin(String login, String senha, String perfil) {
        ModelUsuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ? AND perfil = ?";

        Connection conn = FabricaConexao.conectar();

        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, login);
                stmt.setString(2, senha);
                stmt.setString(3, perfil);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        usuario = new ModelUsuario();
                        usuario.setUsuario(rs.getString("usuario"));
                        usuario.setSenha(rs.getString("senha"));
                        usuario.setPerfil(rs.getString("perfil"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                FabricaConexao.desconectar(conn);
            }
        }
        return usuario;
    }
}