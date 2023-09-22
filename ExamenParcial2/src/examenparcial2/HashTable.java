/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenparcial2;

/**
 *
 * @author dell
 */
public class HashTable {
    private Entry inicio;

    public HashTable() {
        this.inicio = null;
    }

    public void add(String username, long posicion) {
        Entry nuevo = new Entry(username, posicion);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            Entry actual = inicio;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }
    
    public void remove(String username) {
        if (inicio == null) {
            return;
        }

        if (inicio.getUsername().equals(username)) {
            inicio = inicio.siguiente;
            return;
        }

        Entry actual = inicio;
        while (actual.siguiente != null) {
            if (actual.siguiente.getUsername().equals(username)) {
                actual.siguiente = actual.siguiente.siguiente;
                return;
            }
            actual = actual.siguiente;
        }
    }
    
    public long search(String username) {
        Entry actual = inicio;
        while (actual!=null) {
            if(actual.getUsername().equals(username)) {
                return actual.getPosicion();
                
            }
            actual = actual.siguiente;
            
        }
            return -1;
            
    }
}
