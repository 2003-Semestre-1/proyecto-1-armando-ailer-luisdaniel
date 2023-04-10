/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/* @author Ailer Alvarado - Armando Arce - Daniel Rojas*/

enum EstadoProceso{
    NUEVO,
    PREPARADO,
    EN_EJECUCION,
    EN_ESPERA,
    FINALIZADO
}

class Registros {
    public String AC;
    public String AX;
    public String BX;
    public String CX;
    public String DX;
    
  public String toString(){
      return "AC: " + AC + " AX: " + AX + " BX: " + BX + " CX: " + CX + " DX: " + DX;
  }
}

public class Proceso {
    public Integer id;
    public EstadoProceso estado;
    public Registros registros;
    public Integer prioridad;
    public Proceso siguiente;

    public Proceso(String linea, Integer prioridad){
       this.estado = EstadoProceso.NUEVO;
       this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Proceso{" + "id=" + id + ", estado=" + estado + ", registros=" + registros + ", prioridad=" + prioridad + ", siguiente=" + siguiente + '}';
    }
    
    


}
