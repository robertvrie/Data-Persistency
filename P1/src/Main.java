import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/ov_chipkaart";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","Lol1234!");
        Connection dbConnection = DriverManager.getConnection(url, props);
        String queryString = "select * from reiziger";
        PreparedStatement query = dbConnection.prepareStatement(queryString);
        ResultSet rs = query.executeQuery();
        System.out.println("Alle reizigers:");
        while(rs.next()){
            int id = rs.getInt(1);
            String voorletters = rs.getString(2);
            String tussenvoegsel = rs.getString(3);
            String achternaam = rs.getString(4);
            Date geboortedatum = rs.getDate(5);
            if(tussenvoegsel == null){
                System.out.println("    #" + id + ": " + voorletters + " " + achternaam + " " + "(" + geboortedatum + ")");
            }
            else{
                System.out.println("    #" + id + ": " + voorletters + " " + (tussenvoegsel + " ") + achternaam + " " + "(" + geboortedatum + ")");
            }
        }
    }
}