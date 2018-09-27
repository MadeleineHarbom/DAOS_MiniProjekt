package opgave13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CykelRytter {

    /**
     * @param args
     */
    static Connection minConnection;
    static Statement stmt;
    static BufferedReader inLine;

    public static void selectAllRytter() {
        try {
            // Laver sql-saetning og foer den udfoert
            String sql = "select rytternavn from rytter";
            System.out.println("SQL-streng er "+sql);
            ResultSet res=stmt.executeQuery(sql);
            // gennemlloeber svaret
            while (res.next()) {
                String s;
                s = res.getString("rytternavn");
                System.out.println(s);
                //System.out.println(s + "    " + res.getString(2)); //Skal ikke vare med, da det kug bliver spurgt om en kolonne
            }
            // paen lukning
            if (!minConnection.isClosed()) minConnection.close();
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    }

    public static void selectRytterMedParm() {
        try {
            // Indloeser soegestreng
            System.out.println("Indtast soegestreng");
            String inString = inLine.readLine();
            // Laver sql-saetning og foer den udfoert
            String sql = "select init, rytternavn from rytter where rytternavn like '" + inString + "%'";
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

    public static void insertRytter() {
        try {
            // indloesning
            System.out.println("Vi vil nu oprette et nyt ansaettelsesfforhold");
            System.out.println("Indtast initialer (personen skal vaere oprettet paa forhaand");
            String initstr=inLine.readLine();
            System.out.println("Indtast navn ");
            String navnstr=inLine.readLine();

            // sender insert'en til db-serveren
            String sql = "insert into rytter values ('" + initstr + "','" + navnstr + "')";
            System.out.println("SQL-streng er "+ sql);
            stmt.execute(sql);
            // paent svar til brugeren
            System.out.println("Rytteren er nu registreret");
            if (!minConnection.isClosed()) minConnection.close();
        }

        catch (SQLException e) {
            switch (e.getErrorCode())
            // fejl-kode 547 svarer til en foreign key fejl
            {
            // fejl-kode 2627 svarer til primary key fejl
                case 2627: {System.out.println("En rytter med de initialer findes allereden");
                    break;
                }
                default: System.out.println("fejlSQL:  "+e.getMessage());
            };
        }

        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    };

    public static void selectAllVM() {
        try {
            // Laver sql-saetning og foer den udfoert
            String sql = "select aarstal, land, bynavn from vm";
            System.out.println("SQL-streng er "+sql);
            ResultSet res=stmt.executeQuery(sql);
            // gennemlloeber svaret
            while (res.next()) {
                String s;
                System.out.println(res.getString(1) + "     " + res.getString(2) + "    \t " + res.getString(3));
                //s = res.getString("");
                //System.out.println(s);
                //System.out.println(s + "    " + res.getString(2)); //Skal ikke vare med, da det kug bliver spurgt om en kolonne
            }
            // paen lukning
            if (!minConnection.isClosed()) minConnection.close();
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    }

    public static void insertVM() {
        try {
            // indloesning
            System.out.println("Vi vil nu oprette et nyt VM");
            System.out.println("Indtast iaarstal (maa ikke findes i forvejen)");
            String aarstr=inLine.readLine();

            System.out.println("Indtast land ");
            String landstr=inLine.readLine();
            System.out.println("Indtast bynavn ");
            String bystr=inLine.readLine();
            // sender insert'en til db-serveren
            String sql = "insert into vm values (" + Integer.parseInt(aarstr) + ",'" + bystr + "','" + landstr + "') ";
            System.out.println("SQL-streng er "+ sql);
            stmt.execute(sql);
            // paent svar til brugeren
            System.out.println("vm er nu registreret");
            if (!minConnection.isClosed()) minConnection.close();
        }

        catch (SQLException e) {
            switch (e.getErrorCode())
            // fejl-kode 547 svarer til en foreign key fejl
            {
                // fejl-kode 2627 svarer til primary key fejl
                case 2627: {System.out.println("En rytter med de initialer findes allereden");
                    break;
                }
                default: System.out.println("fejlSQL:  "+e.getMessage());
            };
        }

        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    };

    public static void insertPlacering() {
        try {
            System.out.println("Intast aar (skal findes VM paa angivne aar");
            String aarstr=inLine.readLine();
            System.out.println("Initialer paa rytter");
            String initstr=inLine.readLine();
            System.out.println("Placering paa rytter");
            String placstr=inLine.readLine();
            int aar = 0;
            try {
                aar = Integer.parseInt(aarstr);

            }
            catch (Exception e) {
                System.out.println("Aar must be number");
            }
            try {
                int plac = Integer.parseInt(placstr);
                String sql = "insert into vm values (" + aar + ",'" + initstr + "'," + plac + ") ";
                System.out.println("SQL-streng er "+ sql);

            }
            catch (Exception e) {
                String sql = "insert into vm values (" + aar + ",'" + initstr + "', NULL)";
                System.out.println("SQL-streng er "+ sql);
            }

        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }


    }

    public static void findPlacFraInit() {
        try {
            System.out.println("Indtast initiaker paa rytteren");
            String initstr=inLine.readLine();
            String sql = "SELECT * FROM placering WHERE placering.init = '" + initstr + "'";
            System.out.println("SQL-streng er "+ sql);
            ResultSet res=stmt.executeQuery(sql);
            // gennemlloeber svaret
            while (res.next()) {
                String s;
                if (res.getString(3).equals("NULL")) {
                    System.out.println(res.getString(1) + "     " + res.getString(2) + "    \t UDGAAET");

                }
                else {
                    System.out.println(res.getString(1) + "     " + res.getString(2) + "    \t " + res.getString(3));
                }


            }

        }
        catch (Exception e) {
            System.out.println("fejl: " + e.getMessage());
        }



    }

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
            minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=CykelRytterDB5;user=sa;password=reallyStrongPwd123;");
            stmt = minConnection.createStatement();
            //Indloesning og kald af den rigtige metode
            System.out.println("Indtast  ");
            System.out.println("r for alle rytter  ");
            System.out.println("spr for select rytter med parameter  ");
            System.out.println("ir for oprette ny rytter med strengmanipulation");
            System.out.println("ps for insert med prepared statement "); //HUH??
            System.out.println("vm for alle vm-kampe ");
            System.out.println("vmi for att lave my vm");
            System.out.println("p for alle placeringer");
            System.out.println("pr for placerig paa rytter");
            String in=inLine.readLine();
            switch (in)
            {case "r"  : { selectAllRytter();break;}
                case "spr" : {
                    selectRytterMedParm();break;}
                case "ir"  : {
                    insertRytter();break;}
                case "ps"  : {insertprepared();break;}
                case "vm"  : {selectAllVM();break;}
                case "vmi" : {insertVM();break;}
                case "p"   : {insertPlacering();break;}
                case "pr"  : {findPlacFraInit();break;}
                default : System.out.println("ukendt indtastning");
            }
        }
        catch (Exception e) {
            System.out.println("fejl:  "+e.getMessage());
        }
    }
}
