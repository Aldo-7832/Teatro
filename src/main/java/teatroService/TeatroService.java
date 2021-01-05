package teatroService;

import modelo.cliente.ClienteBean;
import modelo.cliente.ClienteDao;
import modelo.funcion.FuncionBean;
import modelo.funcion.FuncionDao;
import modelo.funciones.funcionesDao;
import modelo.lugar.LugarBean;
import modelo.lugar.LugarDao;
import org.json.JSONObject;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/servicios")
public class TeatroService {

    ClienteDao cliente = new ClienteDao();
    FuncionDao funcion = new FuncionDao();
    LugarDao lugar = new LugarDao();
    funcionesDao funciones = new funcionesDao();
    JSONObject objJson = new JSONObject();

    @GET
    @Path("/historial/{idcliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarHistorial(@PathParam("idcliente") int idcliente){
        objJson.put("historial",funciones.consultarHistorial(idcliente));
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/actualizarDatos/{nombre}/{aPaterno}/{aMaterno}/{domicilio}/{fechaN}/{telefono}/{correo}/{pass}/{estado}/{idcliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarDatos(@PathParam("nombre") String nombre,@PathParam("aPaterno") String aPaterno,
    @PathParam("aMaterno") String aMaterno, @PathParam("domicilio") String domicilio,
    @PathParam("fechaN") String fechaN, @PathParam("telefono") String telefono, @PathParam("correo") String correo, @PathParam("pass") String pass, @PathParam("estado") int estado,
    @PathParam("idcliente") int idcliente){
        objJson.put("actualizarDatos", cliente.actualizarDatosCliente(nombre, aPaterno, aMaterno, domicilio, fechaN, telefono, correo, pass, estado, idcliente));
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/cliente/{idcliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verClientes(@PathParam("idcliente") int idcliente){
        objJson.put("cliente",cliente.verTodosLosClientes(idcliente));
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/actualizarContraseña/{correo}/{password}/{cliente_idcliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarContrasena(@PathParam("correo") String correo,
    @PathParam("password") String password, @PathParam("cliente_idcliente") int cliente_idcliente){
        objJson.put("resultadoRegistro", cliente.actualizarCorreo_Contraseña(correo, password, cliente_idcliente));
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/registrarUsuario/{nombre}/{aPaterno}/{aMaterno}/{domicilio}/{fchaN}/{telefono}/{correo}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarUsuario(@PathParam("nombre") String nombre,
    @PathParam("aPaterno") String aPaterno, @PathParam("aMaterno") String aMaterno,
    @PathParam("domicilio") String domicilio, @PathParam("fchaN") String fchaN,
    @PathParam("telefono") String telefono, @PathParam("correo") String correo, @PathParam("password") String password){
        objJson.put("registrarUsuario", cliente.registrarUsuario(nombre, aPaterno, aMaterno, domicilio, fchaN, telefono, correo, password));
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/inicio/{correo}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validarInicio(@PathParam("correo") String correo, @PathParam("password") String password){
        System.out.println("Llego al servicio");
        objJson.put("validarInicio",  cliente.validarInicio(correo,password));
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/lugaresOcupados/{funcion_idfuncion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response lugaresOcupados(@PathParam("funcion_idfuncion") int funcion_idfuncion){
        objJson.put("lugaresOcupados", lugar.verLugaresOcupados(funcion_idfuncion));
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/registrarFuncion/{nombre}/{nombreArtista}/{fecha_inicio}/{hora_inicio}/{precio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarFuncion(@PathParam("nombre") String nombre,
    @PathParam("nombreArtista") String nombreArtista, @PathParam("fecha_inicio") String fecha_inicio,
    @PathParam("hora_inicio") String hora_inicio, @PathParam("precio") float precio){
        objJson.put("registrarFuncion", funcion.registrarFuncion(nombre, nombreArtista, fecha_inicio, hora_inicio, precio));
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/cambiarEstados/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarEstados(@PathParam("id") int id){
        objJson.put("cambiarEstados", funcion.cambiarEstados(id));
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/definirLugar/{fila}/{columna}/{estado}/{funcion_idfunciones}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response definirLugar(@PathParam("fila") int fila, @PathParam("columna") int columna,
    @PathParam("estado") int estado, @PathParam("funcion_idfunciones") int funcion_idfunciones){
        for(int i = 1; i <= fila; i++){
            for(int j = 1; j <= columna; j++){
                String asiento = "";
                asiento = i+"-"+j;
                objJson.put("definirLugar", lugar.definirLugar(asiento, estado, funcion_idfunciones));
            }
        }
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/verFuncionesDisponibles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verFuncionesDisponibles(){
        objJson.put("verFuncionesDisponibles", funcion.funcionesDisponibles());
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/verFunciones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verFunciones(){
        objJson.put("verFunciones", funciones.verFunciones());
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/agregarFuncionCliente/{cliente_idcliente}/{funcion_idfunciones}/{fecha_compra}/{estado}/{cantAsientos}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarFuncionCliente(@PathParam("cliente_idcliente") int cliente_idcliente,
    @PathParam("funcion_idfunciones") int funcion_idfunciones,
    @PathParam("fecha_compra") String fecha_compra, @PathParam("estado") int estado,
    @PathParam("cantAsientos") int cantAsientos){
        List<LugarBean> lista = new ArrayList<>();
        lista = lugar.verLugaresOcupados(funcion_idfunciones);
        if(cantAsientos > lista.size()){
            objJson.put("agregarFuncionCliente", false);
        }else{
            for(int i = 0; i < cantAsientos; i++){
                objJson.put("agregarFuncionCliente", funciones.agregarFuncion(cliente_idcliente, funcion_idfunciones, fecha_compra, estado, lista.get(i).getIdlugar()));
            }
        }
        return Response.ok(objJson.toString()).build();
    }

    @GET
    @Path("/ultimaFuncion")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarUltimaFuncion(){
        objJson.put("ultimaFuncion", funcion.ultimoRegistro());
        return Response.ok(objJson.toString()).build();
    }
}
