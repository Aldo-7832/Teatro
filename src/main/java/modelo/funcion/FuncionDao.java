package modelo.funcion;

import modelo.lugar.LugarDao;
import servicioGeneral.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionDao {

    Connection con;
    PreparedStatement pstm;
    ResultSet rs;
    private CallableStatement cstm;

    private final String SQL_REGISTRAR_FUNCION = "INSERT INTO funcion (nombre, nombreArtista, fecha_inicio, hora_inicio, precio, estado) VALUES (?,?,?,?,?,1);";
    private final String SQL_PROCEDIMIENTO_FUNCION = "{CALL cambiar_estados (?)};";
    private final String SQL_FUNCIONES_DISPONIBLES = "SELECT * FROM funcion WHERE estado = 1;";
    private final String SQL_VER_ID = "SELECT(idfunciones) FROM funcion;";
    private final String SQL_ULTIMO_REGISTRO = "SELECT * FROM funcion ORDER by idfunciones DESC LIMIT 1;";

    public List<FuncionBean> funcionesDisponibles(){
        List<FuncionBean> lista  = new ArrayList<>();
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_FUNCIONES_DISPONIBLES);
            rs = pstm.executeQuery();
            while(rs.next()){
                FuncionBean funcion = new FuncionBean();
                funcion.setIdfunciones(rs.getInt(1));
                funcion.setNombre(rs.getString(2));
                funcion.setNombreArtista(rs.getString(3));
                funcion.setFecha_inicio(rs.getString(4));
                funcion.setHora_inicio(rs.getString(5));
                funcion.setPrecio(rs.getFloat(6));
                funcion.setEstado(rs.getInt(7));
                lista.add(funcion);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error en funcionesDisponibles");
        }finally {
            cerrarConexion(1);
        }

        return lista;
    }

    public int verId(){
        int resultado = 0;
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_VER_ID);
            rs = pstm.executeQuery();
            while (rs.next()){
                resultado = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error al verId");
        }finally {
            cerrarConexion(1);
        }

        return resultado;
    }

    public boolean registrarFuncion(String nombre, String nombreArtista, String fecha_inicio, String hora_inicio, double precio){
        boolean resultado = false;
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_REGISTRAR_FUNCION);
            pstm.setString(1, nombre);
            pstm.setString(2,nombreArtista);
            pstm.setString(3, fecha_inicio);
            pstm.setString(4,hora_inicio);
            pstm.setDouble(5,precio);
            resultado = pstm.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error en registrarFuncion");
        }
        return resultado;
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

    public boolean cambiarEstados(int id){
        boolean resultado = false;
        try{
            con = ConexionMySQL.getConnection();
            cstm = con.prepareCall(SQL_PROCEDIMIENTO_FUNCION);
            cstm.setInt("id",id);
            resultado = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Error en cambiarEstados");
        }
        return resultado;
    }

    public int ultimoRegistro(){
        int resultado = 0;
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_ULTIMO_REGISTRO);
            rs = pstm.executeQuery();
            if (rs.next()){
                resultado = rs.getInt(1);
            }
        }catch (SQLException e){
            System.out.println("Hubo un error al consultar el último registro.");
            e.printStackTrace();
        }
        return resultado;
    }
}
