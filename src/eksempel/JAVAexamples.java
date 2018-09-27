package eksempel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JAVAexamples {


    /**
     * @param args
     */
    static Connection minConnection;
    static Statement stmt;
    static BufferedReader inLine;

    public static void selectudenparm() {
        try {
            // Laver sql-saetning og foer den udfoert
            String sql = "select navn,stilling from person";
            System.out.println("SQL-streng er "+sql);
            ResultSet res=stmt.executeQuery(sql);
            // gennemlloeber svaret
            while (res.next()) {
                String s;
                s = res.getString("navn");
                System.out.println(s + "    " + res.getString(2));
            }
            // paen lukning
            if (!minConnection.isClosed()) minConnection.close();
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    }

    public static void selectmedparm() {
        try {
            // Indloeser soegestreng
            System.out.println("Indtast s�gestreng");
            String inString = inLine.readLine();
            // Laver sql-sartning og foer den udfoert
            String sql = "select navn,stilling from person where navn like '" + inString + "%'";
            System.out.println("SQL-streng er "+ sql);
            ResultSet res=stmt.executeQuery(sql);
            //gennemloeber svaret
            while (res.next()) {
                System.out.println(res.getString(1) + "    " + res.getString(2));
            }
            // paen lukning
            if (!minConnection.isClosed()) minConnection.close();
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    }

    public static void insertmedstring() {
        try {
            // indloesning
            System.out.println("Vi vil nu oprette et nyt ansaettelsesfforhold");
            System.out.println("Indtast cpr (personen skal vaere oprettet paa forhaand");
            String cprstr=inLine.readLine();
            System.out.println("Indtast firmanr (firma skal vaere oprettet paa forhaand");
            String firmastr=inLine.readLine();

            // sender insert'en til db-serveren
            String sql = "insert into ansati values ('" + cprstr + "'," + firmastr + ")";
            System.out.println("SQL-streng er "+ sql);
            stmt.execute(sql);
            // paent svar til brugeren
            System.out.println("Ansaettelsen er nu registreret");
            if (!minConnection.isClosed()) minConnection.close();
        }
        catch (SQLException e) {
            switch (e.getErrorCode())
            // fejl-kode 547 svarer til en foreign key fejl
            { case 547 : {if (e.getMessage().contains("cprforeign"))
                System.out.println("cpr-nummer er ikke oprettet");
                if (e.getMessage().contains("firmaforeign"))
                    System.out.println("firmaet er ikke oprettet");
                break;
            }
            // fejl-kode 2627 svarer til primary key fejl
                case 2627: {System.out.println("den paagaeldende ansaettelse er allerede oprettet");
                    break;
                }
                default: System.out.println("fejlSQL:  "+e.getMessage());
            };
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    };

    public static void insertprepared() {
        try {
            // indloesning
            System.out.println("Vi vil nu oprette et nyt ansaettelsesfforhold");
            System.out.println("Indtast cpr (personen skal vaere oprettet paa forhaand");
            String cprstr=inLine.readLine();
            System.out.println("Indtast firmanr (firma skal vaere oprettet paa forhaand");
            String firmastr=inLine.readLine();
            // Anvendelse af prepared statement
            String sql = "insert into ansati values (?,?)";
            PreparedStatement prestmt = minConnection.prepareStatement(sql);
            prestmt.clearParameters();
            prestmt.setString(1,cprstr);
            prestmt.setInt(2,Integer.parseInt(firmastr));
            // Udfoerer saetningen
            prestmt.execute();
            // paent svar til brugeren
            System.out.println("Ans�ttelsen er nu registreret");
            if (!minConnection.isClosed()) minConnection.close();
        }
        catch (SQLException e) {
            switch (e.getErrorCode())
            // fejl-kode 547 svarer til en foreign key fejl
            { case 547 : {if (e.getMessage().contains("cprforeign"))
                System.out.println("cpr-nummer er ikke oprettet");
                if (e.getMessage().contains("firmaforeign"))
                    System.out.println("firmaet er ikke oprettet");
                break;
            }
            // fejl-kode 2627 svarer til primary key fejl
                case 2627: {System.out.println("den paagaeldende ans�aettelse er allerede oprettet");
                    break;
                }
                default: System.out.println("fejlSQL:  "+e.getMessage());
            };
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    };

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            inLine = new BufferedReader(new InputStreamReader(System.in));
            //generel opsaetning
            //via native driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=PersonFirmaDB1;user=sa;password=reallyStrongPwd123;");
            stmt = minConnection.createStatement();
            //Indloesning og kald af den rigtige metode
            System.out.println("Indtast  ");
            System.out.println("s for select uden parameter  ");
            System.out.println("sp for select med parameter  ");
            System.out.println("i for insert med strengmanipulation");
            System.out.println("ps for insert med prepared statement ");
            String in=inLine.readLine();
            switch (in)
            {case "s"  : {selectudenparm();break;}
                case "sp" : {selectmedparm();break;}
                case "i"  : {insertmedstring();break;}
                case "ps"  : {insertprepared();break;}
                default : System.out.println("ukendt indtastning");
            }
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    }

}
