/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examenparcial2;

/**
 *
 * @author dell
 */
public class Entry {
    String username;
    long posicion;
    Entry siguiente;
    
    public Entry(String username, long posicion) {
        this.username=username;
        this.posicion=posicion;
        siguiente=null;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public long getPosicion() {
        return this.posicion;
    }
    
}
