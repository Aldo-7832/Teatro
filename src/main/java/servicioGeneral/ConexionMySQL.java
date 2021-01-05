package servicioGeneral;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    public static Connection getConnection() throws SQLException {
        Connection con;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }catch (Exception e){
            System.out.println(e);
        }
        con = DriverManager.getConnection("jdbc:mysql://" + "localhost" + "/" + "teatrov3" +
                "?user=" + "root" + "&password=" + "Integradora4832" + "&useSSL=false");
        return  con;
        /*return DriverManager.getConnection("jdbc:mysql://" + "localhost" + ":" +
                3306 + "/" + "teatrov3?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" ,"root" ,"Integradora4832");*/
    }

    public static void main(String[] args) {
        try {
            Connection c = new ConexionMySQL().getConnection();
            System.out.println("conexion ->" + c);
            c.close();
        }catch (Exception e){
            System.out.println("Algo salió mal :("+e);
        }
    }
}
