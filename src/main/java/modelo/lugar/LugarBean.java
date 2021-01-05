package modelo.lugar;

public class LugarBean {

    private int idlugar;
    private String lugar;
    private int estado;
    private int funcion_idfunciones;


    public int getIdlugar() {
        return idlugar;
    }

    public void setIdlugar(int idlugar) {
        this.idlugar = idlugar;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getFuncion_idfunciones() {
        return funcion_idfunciones;
    }

    public void setFuncion_idfunciones(int funcion_idfunciones) {
        this.funcion_idfunciones = funcion_idfunciones;
    }
}
