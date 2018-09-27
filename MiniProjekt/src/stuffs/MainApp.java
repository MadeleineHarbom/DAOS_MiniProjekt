package stuffs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp {
    static Connection minConnection;
    static Statement stmt;
    static BufferedReader inLine;

    public static void selectAllStudents() {
        try {
            // Laver sql-saetning og foer den udfoert
            String sql = "select navn, id from studenter";
            System.out.println("SQL-streng er "+sql);
            ResultSet res=stmt.executeQuery(sql);
            // gennemlloeber svaret
            while (res.next()) {
                String s;
                s = res.getString("navn");
                System.out.println(s + "    id: " + res.getString(2));
            }
            // paen lukning
            if (!minConnection.isClosed()) minConnection.close();
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    }

    public static void registrerKaraktaer() {
        try {
            System.out.println("Indtast studienr paa studerende");
            String stustr=inLine.readLine();
            int stu = 0;
            try{
                stu = Integer.parseInt(stustr);
            }
            catch (Exception e) {
                System.out.println("Studeie skal vaere et tal");
            }
            System.out.println("Indtaste eksamenskoden");
            String eksstr = inLine.readLine();
            int eks = 0;
            try{
                eks = Integer.parseInt(stustr);
            }
            catch (Exception e) {
                System.out.println("ID vaere et tal");
            }
            System.out.println("Inttast karaktaer");
            String karstr = inLine.readLine();
            int kar = 0;
            try{
                kar = Integer.parseInt(stustr);
            }
            catch (Exception e) {
                System.out.println("Karakaerskal vaere et tal");
            }
            System.out.println("TEMPLATE: UPDATE eksamensforsoeg SET karakter = 3 WHERE (eksamenid = 2 AND studentid = 1)");
            String sql = "update eksamensforsoeg SET karakter = " + kar + "WHERE (eksamenid =" + eks + " AND studentid = " + stu + ")";
            System.out.println("SQL-streng er "+sql);
            ResultSet res=stmt.executeQuery(sql);
            while (res.next()) {
                String s;
                s = res.getString(1);
                System.out.println(s + " (" + res.getString(2) + ")");
            }
            if (!minConnection.isClosed()) minConnection.close();
        }
        catch (Exception e) {
            System.out.println("POOP");
        }
    }


    public static void findStuderendeId() {
        try {
            System.out.println("Indtast studienr paa studerende");
            String stustr=inLine.readLine();
            int stu =0;
            try{
                stu = Integer.parseInt(stustr);
            }
            catch (Exception e) {
                System.out.println("Studeie skal vaere et tal");
            }
            String sql = "select navn, id from studenter where studenter.id = " + stu;
            System.out.println("SQL-streng er "+sql);
            ResultSet res=stmt.executeQuery(sql);
            while (res.next()) {
                String s;
                s = res.getString(1);
                System.out.println(s + " (" + res.getString(2) + ")");
            }
            if (!minConnection.isClosed()) minConnection.close();

        }
        catch (Exception e) {
            System.out.println("Sumtin gun rong, nab");
        }
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            inLine = new BufferedReader(new InputStreamReader(System.in));
            //generel opsaetning
            //via native driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=eaaadb;user=sa;password=reallyStrongPwd123;");
            stmt = minConnection.createStatement();
            //Indloesning og kald af den rigtige metode
            System.out.println("Indtast  ");
            System.out.println("as for select uden parameter  ");
            System.out.println("sk for select med parameter  ");
            System.out.println("i for insert med strengmanipulation");
            System.out.println("ps for insert med prepared statement ");
            String in=inLine.readLine();
            switch (in)
            {case "as"  : {selectAllStudents();break;}
                case "sk" : {registrerKaraktaer();break;}
                //case "i"  : {insertmedstring();break;}
                //case "ps"  : {insertprepared();break;}
                default : System.out.println("ukendt indtastning");
            }
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    }



}
