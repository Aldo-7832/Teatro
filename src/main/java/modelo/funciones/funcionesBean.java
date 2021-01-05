package modelo.funciones;

public class funcionesBean {

    private int id;
    private int cliente_idcliente;
    private int funcion_idfuncion;
    private String fecha_compra;
    private int estado;
    private int lugar_idtable1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente_idcliente() {
        return cliente_idcliente;
    }

    public void setCliente_idcliente(int cliente_idcliente) {
        this.cliente_idcliente = cliente_idcliente;
    }

    public int getFuncion_idfuncion() {
        return funcion_idfuncion;
    }

    public void setFuncion_idfuncion(int funcion_idfuncion) {
        this.funcion_idfuncion = funcion_idfuncion;
    }

    public String getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getLugar_idtable1() {
        return lugar_idtable1;
    }

    public void setLugar_idtable1(int lugar_idtable1) {
        this.lugar_idtable1 = lugar_idtable1;
    }
}
