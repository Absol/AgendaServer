/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.cinvestav.agendaColab.server.logica.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.cinvestav.agendaColab.comun.beans.BeanUsuario;

/**
 * Dao para usuario
 * @author absol
 */
public class UsuarioDao extends DaoPadre{


    /**
     * @param login: login del usuairo a buscar
     * @return usuario: bean del usuario encontrado o null si no existe
     */
    public BeanUsuario getUsuarioByLogin(String login)
    {
        BeanUsuario result = null;
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM usuarios "
                    + "WHERE login = '"
                    + login + "'";
            log.debug(query);
            ResultSet rs = stmt.executeQuery(query);
            int idUsu;
            String pass;
            if(rs.next()) {
                idUsu = rs.getInt("id_usuario");
                pass = rs.getString("password");
                log.debug(
                        "leido(" + idUsu
                        + ", " + login
                        + ")");
                result = new BeanUsuario(idUsu, login, pass);
            }
            if(rs.next())
                log.warn("Se obtuvieron dos usuario con login " + login);
            rs.close();
        } catch (SQLException ex) {
            log.error(ex);
        } finally {
            if (stmt != null) {
                try {
                stmt.close();
                } catch (SQLException ex2) {
                    log.error(ex2);
                }
            }
        }

        return result;
    }

}
