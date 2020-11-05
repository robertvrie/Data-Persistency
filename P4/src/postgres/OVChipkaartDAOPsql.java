package postgres;

import dao.OVChipkaartDAO;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;

    public OVChipkaartDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        String saveString = "insert into ov_chipkaart values(?,?,?,?,?)";
        PreparedStatement saveQuery = conn.prepareStatement(saveString);
        saveQuery.setInt(1, ovChipkaart.getKaartnummer());
        saveQuery.setDate(2, ovChipkaart.getGeldigTot());
        saveQuery.setInt(3, ovChipkaart.getKlasse());
        saveQuery.setDouble(4, ovChipkaart.getSaldo());
        saveQuery.setInt(5, ovChipkaart.getReizigerId());
        return !saveQuery.execute();
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        String updateString = "update ov_chipkaart set geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? where kaart_nummer = ?";
        PreparedStatement updateQuery = conn.prepareStatement(updateString);
        updateQuery.setDate(1, ovChipkaart.getGeldigTot());
        updateQuery.setInt(2, ovChipkaart.getKlasse());
        updateQuery.setDouble(3, ovChipkaart.getSaldo());
        updateQuery.setInt(4, ovChipkaart.getReizigerId());
        updateQuery.setInt(5, ovChipkaart.getKaartnummer());
        return !updateQuery.execute();
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        String deleteString = "delete from ov_chipkaart where kaart_nummer = ?";
        PreparedStatement deleteQuery = conn.prepareStatement(deleteString);
        deleteQuery.setInt(1, ovChipkaart.getKaartnummer());
        return !deleteQuery.execute();
    }

    @Override
    public OVChipkaart findById(int id) throws SQLException {
        String readString = "select * from ov_chipkaart where kaart_nummer = ?";
        PreparedStatement readQuery = conn.prepareStatement(readString);
        readQuery.setInt(1, id);
        return getOvChipkaarten(readQuery.executeQuery()).get(0);
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        String readString = "select * from ov_chipkaart where reiziger_id = ?";
        PreparedStatement readQuery = conn.prepareStatement(readString);
        readQuery.setInt(1, reiziger.getId());
        return getOvChipkaarten(readQuery.executeQuery());
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        String readString = "select * from ov_chipkaart";
        PreparedStatement readQuery = conn.prepareStatement(readString);
        return getOvChipkaarten(readQuery.executeQuery());
    }

    @Override
    public List<OVChipkaart> getOvChipkaarten(ResultSet rs) throws SQLException {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        while(rs.next()){
            OVChipkaart ovChipkaart = new OVChipkaart(rs.getInt(1),
                    rs.getDate(2),
                    rs.getInt(3),
                    rs.getDouble(4),
                    rs.getInt(5));
            ovChipkaarten.add(ovChipkaart);
        }
        return ovChipkaarten;
    }

}
