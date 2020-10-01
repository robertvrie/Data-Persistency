import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    static Connection connection = null;
    public static void main(String[] args) throws SQLException {
        ReizigerDAO reizigerDAO = new ReizigerDaoPsql(getConnection(connection));

        testReizigerDAO(reizigerDAO);

        closeConnection();
    }

    private static Connection getConnection(Connection conn){
        {
            try {
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ov_chipkaart", "postgres", "Lol1234!");
            } catch (SQLException e) {
                System.out.println("Unable to make connection");
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        if(connection != null){
            System.out.println("Shutting down connection...");
            connection.close();
        }
        else{
            System.out.println("Connection was already shutdown.");
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Updaten van sietske, veranderd de achternaam en zoekt deze weer op via de geboortedatum
        Reiziger sietskeNieuw = new Reiziger(77, "S", "", "Jansen", java.sql.Date.valueOf(gbdatum));
        System.out.println("Updaten gelukt? " + rdao.update(sietskeNieuw));
        System.out.println(rdao.findByGbDatum(gbdatum) + "\n");

        // Verwijderd sietske gebaseerd op het id
        System.out.println("Voor verwijderen zijn er " + rdao.findAll().size() + " reizigers");
        System.out.println("Verwijderen gelukt? " + rdao.delete(sietskeNieuw));
        System.out.println("Na verwijderen zijn er " + rdao.findAll().size() + " reizigers");
    }
}