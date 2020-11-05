import java.sql.Date;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date gbdatum;
    private Adres adres;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String reizigerStringMetTussenVoegsel =     "Reiziger {" +
                                                    "#" + id + " " +
                                                    voorletters + ". " +
                                                    tussenvoegsel + " " +
                                                    achternaam + " geb. " +
                                                    gbdatum;

        String reizigerStringZonderTussenVoegsel =  "Reiziger {" +
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
            sb.append("}");
        }
        else{
            sb.append(", Adres {" +
                      "#" + adres.getId() + " " +
                      adres.getPostcode() + " " +
                      adres.getHuisnummer() + "}}");
        }
        return sb.toString();
    }
}