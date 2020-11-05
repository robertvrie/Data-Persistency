package domain;

import java.sql.Date;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date gbdatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaarten;

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date gbdatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.gbdatum = gbdatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGbdatum() {
        return gbdatum;
    }

    public void setGbdatum(Date gbdatum) {
        this.gbdatum = gbdatum;
    }

    public Adres getAdres(){ return adres; }

    public void setAdres(Adres adres){ this.adres = adres; }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String reizigerStringMetTussenVoegsel =     "\nReiziger {" +
                                                    "#" + id + " " +
                                                    voorletters + ". " +
                                                    tussenvoegsel + " " +
                                                    achternaam + " geb. " +
                                                    gbdatum;

        String reizigerStringZonderTussenVoegsel =  "\nReiziger {" +
                                                    "#" + id + " " +
                                                    voorletters + ". " +
                                                    achternaam + " geb. " +
                                                    gbdatum;
        if(tussenvoegsel != null){
            sb.append(reizigerStringMetTussenVoegsel);
        }
        else{
            sb.append(reizigerStringZonderTussenVoegsel);
        }

        if(adres == null){
            sb.append(", deze reiziger heeft geen adres.} ");
        }
        else{
            sb.append(", Adres {" +
                      "#" + adres.getId() + " " +
                      adres.getPostcode() + " " +
                      adres.getHuisnummer() + "}} ");
        }

        if (ovChipkaarten == null) {
            sb.append(", deze reiziger heeft geen ov kaarten.} ");
        }
        else{
            sb.append(" Deze reiziger heeft de volgende OV kaarten: ");
            for(OVChipkaart o : ovChipkaarten){
                sb.append(o.toString());
            }
            sb.append("} ");
        }
        return sb.toString();
    }
}