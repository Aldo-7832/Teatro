package modelo.lugar;

import servicioGeneral.ConexionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LugarDao {

    Connection con;
    PreparedStatement pstm;
    ResultSet rs;

    private final String SQL_LUGARES_DISPONIBLES = "select idlugar, lugar from lugar where idlugar not in (select lugar_idtable1 from funcion_cliente where estado = 1 and funcion_idfunciones = ?) and funcion_idfunciones = ?;";
    private final String SQL_DEFINIR_LUGAR = "INSERT INTO lugar (lugar, estado, funcion_idfunciones) VALUES (?,?,?);";

    public boolean definirLugar(String lugar, int estado, int funcion_idfunciones){
        boolean resultado = false;
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_DEFINIR_LUGAR);
            pstm.setString(1,lugar);
            pstm.setInt(2,estado);
            pstm.setInt(3,funcion_idfunciones);
            resultado = pstm.executeUpdate() == 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error en definirLugar");
        }finally {
            cerrarConexion(2);
        }
        return resultado;
    }

    public List<LugarBean> verLugaresOcupados(int funcion_idfuncion){
        List<LugarBean> lista = new ArrayList<>();
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_LUGARES_DISPONIBLES);
            pstm.setInt(1, funcion_idfuncion);
            pstm.setInt(2, funcion_idfuncion);
            rs = pstm.executeQuery();
            while (rs.next()){
                LugarBean lugar = new LugarBean();
                lugar.setIdlugar(rs.getInt(1));
                lugar.setLugar(rs.getString(2));
                lista.add(lugar);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error en verLugaresOcupados");
        }finally {
            cerrarConexion(1);
        }
        return lista;
    }

    public void cerrarConexion(int valor){
        try{
            con.close();
            pstm.close();
            if (valor == 1)
                rs.close();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error en cerrar conexión");
        }
    }
}
