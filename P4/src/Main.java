import dao.AdresDAO;
import dao.OVChipkaartDAO;
import dao.ReizigerDAO;
import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;
import postgres.AdresDAOPsql;
import postgres.OVChipkaartDAOPsql;
import postgres.ReizigerDaoPsql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    static Connection connection = null;
    public static void main(String[] args) throws SQLException {
        ReizigerDAO reizigerDAO = new ReizigerDaoPsql(getConnection(connection));
        AdresDAO adresDAO = new AdresDAOPsql(getConnection(connection));
        OVChipkaartDAO ovDAO = new OVChipkaartDAOPsql(getConnection(connection));

        testReizigerDAO(reizigerDAO);
        testAdresDAO(adresDAO);
        testOvChipkaartDAO(ovDAO);

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
        System.out.println("[TEST] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : rdao.findAll()) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger r1 = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[TEST] Eerst zijn er " + rdao.findAll().size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(r1);
        System.out.println("[TEST] Na ReizigerDAO.save() zijn er " + rdao.findAll().size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Updaten van sietske, veranderd de achternaam en zoekt deze weer op via de geboortedatum
        Reiziger r2 = new Reiziger(77, "S", "", "Jansen", Date.valueOf(gbdatum));
        System.out.println("[TEST] Updaten gelukt? " + rdao.update(r2));
        System.out.println(rdao.findByGbDatum(gbdatum) + "\n");

        // Verwijderd sietske gebaseerd op het id
        System.out.println("[TEST] Voor verwijderen zijn er " + rdao.findAll().size() + " reizigers");
        System.out.println("[TEST] Verwijderen gelukt? " + rdao.delete(r2));
        System.out.println("[TEST] Na verwijderen zijn er " + rdao.findAll().size() + " reizigers");
    }

    private static void testAdresDAO(AdresDAO adresDAO) throws SQLException {
        System.out.println("\n------------- Test AdresDAO -------------");

        //Voor het testen van het adres staat er een reiziger in de database zonder adres, dit is niet sietske van de
        //reiziger test hierboven.

        //Haalt alle adressen op uit de database
        System.out.println("[TEST] Adresdao.findAll() geeft de volgende adressen:");
        for(Adres a : adresDAO.findAll()){
            System.out.println(a);
        }

        //Maak een nieuw adres aan en persisteer deze naar de database
        Adres a1 = new Adres(6, "3704EE", "48", "Kroostweg", "Zeist", 6);
        System.out.print("\n[TEST] Eerst zijn er " + adresDAO.findAll().size() + " adressen.");
        adresDAO.save(a1);
        System.out.println("\n[TEST] Na AdresDAO.save() zijn er " + adresDAO.findAll().size() + " adressen.");
        System.out.println("[TEST] Nieuwe adres is: " + adresDAO.findById(6));

        //Update het adres in de database
        Adres a2 = new Adres(6, "3805AB", "102", "Noordweg", "Utrecht", 6);
        System.out.println("\n[TEST] Updaten gelukt? " + adresDAO.update(a2));
        System.out.println("[TEST] Nieuwe adres van " + a2.getReizigerId() + " is " + adresDAO.findById(6));

        //Verwijderd het adres gebaseerd op ID
        System.out.println("\n[TEST] Voor verwijderen zijn er " + adresDAO.findAll().size() + " adressen");
        System.out.println("[TEST] Verwijderen gelukt? " + adresDAO.delete(a2));
        System.out.println("[TEST] Na verwijderen zijn er " + adresDAO.findAll().size() + " adressen");
    }

    private static void testOvChipkaartDAO(OVChipkaartDAO ovDAO) throws SQLException {
        System.out.println("\n------------- Test OVChipkaartDAO -------------");

        //Haalt alle ov kaarten uit de database en drukt ze af.
        for(OVChipkaart o : ovDAO.findAll()){
            System.out.println(o);
        }

        //Maakt een nieuwe ov kaart aan en voegt deze toe aan de database.
        OVChipkaart o1 = new OVChipkaart(72916, Date.valueOf("2020-12-20"), 2, 50.5, 6);
        System.out.println("\n[TEST] Eerst zijn er " + ovDAO.findAll().size() + " kaarten in de database.");
        ovDAO.save(o1);
        System.out.println("[TEST] Na ovDAO.save() zijn er " + ovDAO.findAll().size() + " kaarten in de database.");
        System.out.println("[TEST] De nieuwe kaart is " + ovDAO.findById(o1.getKaartnummer()) + ".");

        //Update een ovkaart in de database
        OVChipkaart o2 = new OVChipkaart(72916, Date.valueOf("2021-12-20"), 1, 100.5, 6);
        System.out.println("\n[TEST] Is het updaten gelukt? " + ovDAO.update(o2));
        System.out.println("[TEST] De nieuwe ovkaart is: " + ovDAO.findById(o2.getKaartnummer()));

        //Verwijderd de ov kaart gebaseerd op het kaartnummer
        System.out.println("\n[TEST] Voor verwijderen zijn er " + ovDAO.findAll().size() + " kaarten");
        System.out.println("[TEST] Verwijderen gelukt? " + ovDAO.delete(o2));
        System.out.println("[TEST] Na verwijderen zijn er " + ovDAO.findAll().size() + " kaarten");
    }
}