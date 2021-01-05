package modelo.funciones;

import modelo.funcion.FuncionBean;
import servicioGeneral.ConexionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class funcionesDao {

    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;

    private final String SQL_HISTORIALCLIENTE = "select  c.idcliente, concat( c.nombre, ' ', c.aPaterno, ' ', c.aMaterno) as nombreC, f.idfunciones, f.nombre, f.precio, l.idlugar, l.lugar, d.fecha_compra FROM funcion_cliente d INNER JOIN cliente c on idcliente = idcliente INNER JOIN funcion f on funcion_idfunciones = idfunciones INNER JOIN lugar l on lugar_idtable1 = idlugar WHERE idcliente = ?;";
    private final String SQL_AGREGAR_FUNCION = "INSERT INTO funcion_cliente (cliente_idcliente, funcion_idfunciones, fecha_compra, estado, lugar_idtable1) VALUES (?,?,?,?,?);";
    private final String SQL_VER_FUNCIONES = "SELECT * FROM funcion_cliente;";

    public List<HistorialBean> consultarHistorial(int idcliente){
        List<HistorialBean> lista = new ArrayList<>();
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_HISTORIALCLIENTE);
            pstm.setInt(1, idcliente);
            rs = pstm.executeQuery();
            while(rs.next()){
                HistorialBean bean = new HistorialBean();
                bean.setIdcliente(rs.getInt(1));
                bean.setNombreC(rs.getString(2));
                bean.setIdfunciones(rs.getInt(3));
                bean.setNombre(rs.getString(4));
                bean.setPrecio(rs.getFloat(5));
                bean.setIdlugar(rs.getInt(6));
                bean.setLugar(rs.getString(7));
                bean.setFecha_compra(rs.getString(8));
                lista.add(bean);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error en consultarHistorial");
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

    public boolean agregarFuncion(int cliente, int funcion, String fecha_compra, int estado, int lugar_idtable1){
        boolean resultado = false;
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_AGREGAR_FUNCION);
            pstm.setInt(1,cliente);
            pstm.setInt(2,funcion);
            pstm.setString(3,fecha_compra);
            pstm.setInt(4,estado);
            pstm.setInt(5, lugar_idtable1);
            resultado = pstm.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("error en agregar función");
        }finally {
            cerrarConexion(2);
        }
        return resultado;
    }

    public List<funcionesBean> verFunciones(){
        List<funcionesBean> lista = new ArrayList<funcionesBean>();
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_VER_FUNCIONES);
            rs = pstm.executeQuery();
            while(rs.next()){
                funcionesBean funcion = new funcionesBean();
                funcion.setId(rs.getInt(1));
                funcion.setCliente_idcliente(rs.getInt(2));
                funcion.setFuncion_idfuncion(rs.getInt(3));
                funcion.setFecha_compra(rs.getString(4));
                funcion.setEstado(rs.getInt(5));
                funcion.setLugar_idtable1(rs.getInt(6));
                lista.add(funcion);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error al verFunciones");
        }finally {
            cerrarConexion(1);
        }
        return lista;
    }

}
