package dao;

import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {

    boolean save(OVChipkaart ovChipkaart) throws SQLException;
    boolean update(OVChipkaart ovChipkaart) throws SQLException;
    boolean delete(OVChipkaart ovChipkaart) throws SQLException;
    OVChipkaart findById(int id) throws SQLException;
    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    List<OVChipkaart> findAll() throws SQLException;

    List<OVChipkaart> getOvChipkaarten(ResultSet rs) throws SQLException;
}
