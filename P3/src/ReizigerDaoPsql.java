import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDaoPsql implements ReizigerDAO {
    private Connection conn;
    private AdresDAO aDAO;

    public ReizigerDaoPsql(Connection conn){
        this.conn = conn;
        aDAO = new AdresDAOPsql(conn);
    }


    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        String saveString = "insert into reiziger values(?,?,?,?,?)";
        PreparedStatement saveQuery = conn.prepareStatement(saveString);
        saveQuery.setInt(1, reiziger.getId());
        saveQuery.setString(2, reiziger.getVoorletters());
        saveQuery.setString(3, checkTussenvoegsel(reiziger.getTussenvoegsel()));
        saveQuery.setString(4, reiziger.getAchternaam());
        saveQuery.setDate(5, reiziger.getGbdatum());
        return !saveQuery.execute();
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        String updateString = "update reiziger set voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? where reiziger_id = ?";
        PreparedStatement updateQuery = conn.prepareStatement(updateString);
        updateQuery.setString(1, reiziger.getVoorletters());
        updateQuery.setString(2, reiziger.getTussenvoegsel());
        updateQuery.setString(3, reiziger.getAchternaam());
        updateQuery.setDate(4, reiziger.getGbdatum());
        updateQuery.setInt(5, reiziger.getId());
        return !updateQuery.execute();
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        String deleteString = "delete from reiziger where reiziger_id = ?";
        PreparedStatement deleteQuery = conn.prepareStatement(deleteString);
        deleteQuery.setInt(1, reiziger.getId());
        return !deleteQuery.execute();
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        String readString = "select * from reiziger where reiziger_id = ?";
        PreparedStatement readQuery = conn.prepareStatement(readString);
        readQuery.setInt(1, id);
        return getReizigers(readQuery.executeQuery()).get(0);
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) throws SQLException {
        String readString = "select * from reiziger where geboortedatum = ?";
        PreparedStatement readQuery = conn.prepareStatement(readString);
        readQuery.setDate(1, Date.valueOf(datum));
        return getReizigers(readQuery.executeQuery());
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        String readString = "select * from reiziger";
        PreparedStatement readQuery = conn.prepareStatement(readString);
        return getReizigers(readQuery.executeQuery());
    }

    private String checkTussenvoegsel(String tussenvoegsel){
        if(tussenvoegsel.equals("")){
            return null;
        }
        else{
            return tussenvoegsel;
        }
    }

    private List<Reiziger> getReizigers(ResultSet rs) throws SQLException {
        List<Reiziger> reizigers = new ArrayList<Reiziger>();
        while(rs.next()){
            Reiziger reiziger = new Reiziger(rs.getInt(1),
                                             rs.getString(2 ),
                                             rs.getString(3),
                                             rs.getString(4),
                                             rs.getDate(5));
            if(aDAO.findByReiziger(reiziger).isEmpty()){
                System.err.println("Er is geen adres met reizigerId van: " + rs.getInt(1));
            }
            else{
                reiziger.setAdres(aDAO.findByReiziger(reiziger).get(0));
            }
            reizigers.add(reiziger);
        }
        return reizigers;
    }
}
