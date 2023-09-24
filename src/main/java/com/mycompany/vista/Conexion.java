package com.mycompany.vista;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author raul
 */
public class Conexion {
    
    private Connection cn;
    
    public  Connection  conectar(){
        try{
           Class.forName("com.mysql.jdbc.Driver");
           cn=(Connection)  DriverManager.getConnection("jdbc:mysql://localhost/bdgatogame","root","");
           System.out.println("CONECTADO");
           
        } catch (Exception e){
            System.out.println("ERROR DE CONEXION"+e);
        
        
    }
        return cn;
    }
}
   
    
    
  

