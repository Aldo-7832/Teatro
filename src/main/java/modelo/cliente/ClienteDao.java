package modelo.cliente;

import servicioGeneral.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;
    private CallableStatement cstm;

    private final String SQL_INICIO = "select * from cliente where correo = ? AND pass =  ?;";
    private final String SQL_ACTUALIZAR = "update cliente set nombre = ?, aPaterno = ?, aMaterno = ?, domicilio = ?, fchaN = ?, telefono = ?, correo = ?, pass = ?, estado = '1' where idcliente = ?;";
    private final String SQL_VERDATOS = "SELECT * FROM cliente where idcliente = ?;";
    private final String SQL_ACTUALIZAR_CORREO_CONTRASENA = "UPDATE cliente SET correo = ?, pass = ? WHERE idcliente = ?;";
    private final String SQL_REGISTRAR_USUARIO = "INSERT INTO cliente (nombre, aPaterno, aMaterno, domicilio, fchaN, telefono, correo, pass, estado) VALUES (?,?,?,?,?,?,?,?,1)";

    public boolean actualizarDatosCliente(String nombre,String aPaterno,
                                          String aMaterno, String domicilio,
                                          String fechaN, String telefono, String correo, String pass, int estado,
                                          int idcliente){
        boolean resultado = false;
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_ACTUALIZAR);
            pstm.setString(1,nombre);
            pstm.setString(2,aPaterno);
            pstm.setString(3,aMaterno);
            pstm.setString(4,domicilio);
            pstm.setString(5,fechaN);
            pstm.setString(6,telefono);
            pstm.setString(7, correo);
            pstm.setString(8, pass);
            pstm.setInt(9,idcliente);
            resultado = pstm.executeUpdate() == 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error al actualizar datos del cliente");
        }finally {
            cerrarConexion(0);
        }
        return resultado;
    }

    public List<ClienteBean> verTodosLosClientes(int idcliente){
        List<ClienteBean> lista = new ArrayList<>();
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_VERDATOS);
            pstm.setInt(1,idcliente);
            rs = pstm.executeQuery();
            while (rs.next()){
                ClienteBean cliente = new ClienteBean();
                cliente.setIdcliente(rs.getInt("idcliente"));
                cliente.setNombre(rs.getString("nombre"));
                System.out.println(rs.getString("nombre"));
                cliente.setApellidoPaterno(rs.getString("aPaterno"));
                System.out.println(rs.getString("aPaterno"));
                cliente.setApellidoMaterno(rs.getString("aMaterno"));
                System.out.println(rs.getString("aMaterno"));
                cliente.setDomicilio(rs.getString("domicilio"));
                cliente.setFchaN(rs.getString("fchaN"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setPassword(rs.getString("pass"));
                cliente.setEstado(rs.getInt("estado"));
                lista.add(cliente);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error en verDatosDelCliente");
        }finally {
            cerrarConexion(1);
        }
        return lista;
    }

    public boolean actualizarCorreo_Contraseña(String correo, String password, int cliente_idcliente){
        boolean resultado = false;
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_ACTUALIZAR_CORREO_CONTRASENA);
            pstm.setString(1, correo);
            pstm.setString(2, password);
            pstm.setInt(3, cliente_idcliente);
            resultado = pstm.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error en actualizarCorreo_Contraseña");
        }finally {
            cerrarConexion(0);
        }
        return resultado;
    }

    public boolean registrarUsuario(String nombre, String aPaterno, String aMaterno,
                                    String domicilio, String fchaN, String telefono,
                                    String correo, String password){
        boolean resultado = false;
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_REGISTRAR_USUARIO);
            pstm.setString(1,nombre);
            pstm.setString(2,aPaterno);
            pstm.setString(3,aMaterno);
            pstm.setString(4,domicilio);
            pstm.setString(5,fchaN);
            pstm.setString(6,telefono);
            pstm.setString(7,correo);
            pstm.setString(8,password);
            resultado = pstm.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error en registrarCuenta");
        }finally {
            cerrarConexion(0);
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

    public List<ClienteBean> validarInicio(String correo, String password) {
        List<ClienteBean> lista = new ArrayList<>();
        try{
            con = ConexionMySQL.getConnection();
            pstm = con.prepareStatement(SQL_INICIO);
            pstm.setString(1,correo);
            pstm.setString(2,password);
            rs = pstm.executeQuery();
            if (rs.next()){
                ClienteBean cliente = new ClienteBean();
                cliente.setIdcliente(rs.getInt("idcliente"));
                cliente.setNombre(rs.getString("nombre"));
                System.out.println(rs.getString("nombre"));
                cliente.setApellidoPaterno(rs.getString("aPaterno"));
                System.out.println(rs.getString("aPaterno"));
                cliente.setApellidoMaterno(rs.getString("aMaterno"));
                System.out.println(rs.getString("aMaterno"));
                cliente.setDomicilio(rs.getString("domicilio"));
                cliente.setFchaN(rs.getString("fchaN"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setPassword(rs.getString("pass"));
                cliente.setEstado(rs.getInt("estado"));
                lista.add(cliente);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error en validarInicio");
        }finally {
            cerrarConexion(1);
        }
        return lista;
    }

}
