/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenparcial2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class PSNUsers {
    static RandomAccessFile RAF;
    static HashTable users;
    
    public PSNUsers() {
        try {
            File psnF = new File("PSN");
            if (!psnF.exists()) {
                System.out.println("no hay");
            }

            this.RAF = new RandomAccessFile(psnF, "rw");
            this.users = new HashTable();
            reloadHashTable();
        } catch (FileNotFoundException ex) {
            System.out.println("Error");
        } catch (IOException ex) {
            Logger.getLogger(PSNUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void reloadHashTable()  {
        try {
            RAF.seek(0);
            users = new HashTable();

            while (RAF.getFilePointer() < RAF.length()) {
                String username = RAF.readUTF();
                long position = RAF.readLong();
                boolean activated = RAF.readBoolean();

                if (activated) {
                    users.add(username, position);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }
    
    public static void addUsers(String username) {
        try {
            if (users.search(username) != -1) {
                System.out.println("El usuario ya existe.");
                return;
            }
            RAF.seek(RAF.length());
            RAF.writeUTF(username);
            RAF.writeLong(RAF.getFilePointer());
            RAF.writeBoolean(true);
            users.add(username, RAF.getFilePointer());
            System.out.println("Usuario agregado con éxito.");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    
    public static void deactivateUser(String username) throws IOException {
        long posicion=users.search(username);
        if (posicion!=-1) {
            RAF.seek(posicion);
            RAF.writeBoolean(false);
            users.remove(username);
        }
    }
    
    public static void addTrophyTo(String username, String trophyGame, String trophyName, Trophy type) throws IOException {
        long posicion=users.search(username);
        
        if(posicion!=-1) {
            RAF.seek(posicion);
            RAF.writeUTF(username);
            RAF.writeUTF(type.name());
            RAF.writeUTF(trophyGame);
            RAF.writeUTF(trophyName);
            
            LocalDate fecha=LocalDate.now();
            
            RAF.writeUTF(fecha.toString());
            
        }
    }
    
    public static String playerInfo(String username) throws IOException {
         long position = users.search(username);
            if (position != -1) {
                try {
                    RAF.seek(position);

                    StringBuilder userInfo = new StringBuilder();
                    userInfo.append("Datos del usuario:\n");

                    String code = RAF.readUTF();
                    int points = RAF.readInt();
                    int trophyCount = RAF.readInt();
                    boolean isActive = RAF.readBoolean();

                    userInfo.append("Código de Usuario: ").append(code).append("\n");
                    userInfo.append("Puntos de Trofeos: ").append(points).append("\n");
                    userInfo.append("Contador de Trofeos: ").append(trophyCount).append("\n");
                    userInfo.append("Estado Activo: ").append(isActive).append("\n");

                    userInfo.append("Trofeos ganados:\n");
                    while (RAF.getFilePointer() < RAF.length()) {
                        String trophyDate = RAF.readUTF();
                        String trophyType = RAF.readUTF();
                        String trophyGame = RAF.readUTF();
                        String trophyDescription = RAF.readUTF();

                        userInfo.append(trophyDate).append(" - ").append(trophyType).append(" - ").append(trophyGame)
                                .append(" - ").append(trophyDescription).append("\n");
                    }

                    return userInfo.toString();
                } catch (IOException e) {
                    System.out.println("error");
                }
            } else {
                return "El usuario no se encuentra en la base de datos.";
            }
            return null;
    }
    
    public void close() {
        try {
            RAF.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}