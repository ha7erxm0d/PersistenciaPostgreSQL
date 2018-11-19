package com.company;
import com.company.Reader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main{
    static Connection connection = null;
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        initProgram();




    }
    public static void borrarTablas(){

        String Statement;

        Statement = "drop table if exists paises cascade";
        try(PreparedStatement stm = connection.prepareStatement(Statement)){
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement = "drop table if exists provincias cascade";
        try(PreparedStatement stm = connection.prepareStatement(Statement)){
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement = "drop table if exists ciudades cascade";
        try(PreparedStatement stm = connection.prepareStatement(Statement)){
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void iniciarConnexion(){
        try {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1/world", "postgres", "jupiter");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }
    }
    public static void crearTablas(){
        String Statement;
        if (connection != null) {
            Statement = "create table paises(" +
                    "ID_country SERIAL PRIMARY KEY," +
                    "city VARCHAR(40))";
            try(PreparedStatement stm = connection.prepareStatement(Statement)){
                stm.executeUpdate();


            }catch (Exception e){
                System.out.println("Error tabla paises");
            }
            Statement = "create table provincias(" +
                    "ID_provincia INT PRIMARY KEY," +
                    "provincia VARCHAR(40)," +
                    "ID_City INTEGER REFERENCES paises(ID_country))";
            try(PreparedStatement stm = connection.prepareStatement(Statement)){
                stm.executeUpdate();
            }catch (Exception e){
                System.out.println("Error tabla provincias");
            }
            Statement ="create table ciudades(" +
                    "ID_ciudad INT PRIMARY KEY," +
                    "ciudad VARCHAR(40)," +
                    "ID_provincia INTEGER REFERENCES provincias(ID_Provincia)," +
                    "ID_country INTEGER REFERENCES paises(ID_country))";
            try(PreparedStatement stm = connection.prepareStatement(Statement)){
                stm.executeUpdate();
            }catch (Exception e){
                System.out.println("Error tabla ciudades");
            }


        } else {
            System.out.println("Failed to make connection!");
        }
    }
    public static void inserarDatos(){
        Reader dR = null;
        String Statement;
        ArrayList<String> countries = null;
        try{
            dR = new Reader("data/world.xml");
            countries = (ArrayList<String>) dR.nombreCountry();
        } catch (Exception e) {
            e.printStackTrace();
        }
        char OLD_CHAR='\'';
        char NEW_CHAR='`';
        for (int i = 0; i< countries.size(); i++){
            OLD_CHAR='\'';
            NEW_CHAR='`';
            String countryStr = countries.get(i);
            countryStr = countryStr.replace(OLD_CHAR, NEW_CHAR);
            Statement = "insert into paises values (?,?)";
            try(PreparedStatement stm = connection.prepareStatement(Statement)){
                stm.setInt(1, i);
                stm.setString(2,countryStr);
                stm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initProgram(){


        iniciarConnexion();

        borrarTablas();

        crearTablas();

        inserarDatos();


    }

}
