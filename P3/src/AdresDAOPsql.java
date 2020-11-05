import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{
    private Connection conn;

    public AdresDAOPsql(Connection conn){this.conn = conn;}

    @Override
    public boolean save(Adres adres) throws SQLException {
        String saveString = "insert into adres values(?,?,?,?,?,?)";
        PreparedStatement saveQuery = conn.prepareStatement(saveString);
        saveQuery.setInt(1, adres.getId());
        saveQuery.setString(2, adres.getPostcode());
        saveQuery.setString(3, adres.getHuisnummer());
        saveQuery.setString(4, adres.getStraat());
        saveQuery.setString(5, adres.getWoonplaats());
        saveQuery.setInt(6,  adres.getReizigerId());
        return !saveQuery.execute();
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        String updateString = "update adres set postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? where adres_id = ?";
        PreparedStatement updateQuery = conn.prepareStatement(updateString);
        updateQuery.setString(1, adres.getPostcode());
        updateQuery.setString(2, adres.getHuisnummer());
        updateQuery.setString(3, adres.getStraat());
        updateQuery.setString(4, adres.getWoonplaats());
        updateQuery.setInt(5, adres.getId());
        return !updateQuery.execute();
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        String deleteString = "delete from adres where adres_id = ?";
        PreparedStatement deleteQuery = conn.prepareStatement(deleteString);
        deleteQuery.setInt(1, adres.getId());
        return !deleteQuery.execute();
    }

    @Override
    public Adres findById(int id) throws SQLException {
        String readString = "select * from adres where adres_id = ?";
        PreparedStatement readQuery = conn.prepareStatement(readString);
        readQuery.setInt(1, id);
        return getAdressen(readQuery.executeQuery()).get(0);
    }

    @Override
    public List<Adres> findByReiziger(Reiziger reiziger) throws SQLException {
        String readString = "select * from adres where reiziger_id = ?";
        PreparedStatement readQuery = conn.prepareStatement(readString);
        readQuery.setInt(1, reiziger.getId());
        return getAdressen(readQuery.executeQuery());
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        String readString = "select * from adres";
        PreparedStatement readQuery = conn.prepareStatement(readString);
        return getAdressen(readQuery.executeQuery());
    }

    private List<Adres> getAdressen(ResultSet rs) throws SQLException {
        List<Adres> adressen = new ArrayList<>();
            while(rs.next()){
                Adres adres = new Adres(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6));
                adressen.add(adres);
            }
        return adressen;
    }
}