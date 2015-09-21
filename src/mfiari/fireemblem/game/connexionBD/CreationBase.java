/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.connexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mfiari.lib.game.connexionBD.Methode;
import mfiari.lib.game.util.Log;

/**
 *
 * @author mike
 */
public class CreationBase extends Methode {

    public CreationBase(Connection connection) {
        super(connection);
    }

    public void createDatabase() {
        if (this.estConnecter()) {
            if (!this.tableExist()) {
                this.createAllTable();
            }
            if (this.getDateTableMiseAJour().before(this.getDateDerniereMiseAJour())) {
                this.miseAJourDatabaseScript();
            }
        }
    }

    private void createAllTable() {
        this.creationTableMiseAJour();
        this.creationTablePartie();
        this.creationTablePerso();
        this.creationTablePersoObjet();
    }

    private void dropAllTable() {
        this.exexuteUpdate("DROP TABLE IF EXISTS mise_a_jour CASCADE");
        this.exexuteUpdate("DROP TABLE IF EXISTS perso_objet CASCADE");
        this.exexuteUpdate("DROP TABLE IF EXISTS perso CASCADE");
        this.exexuteUpdate("DROP TABLE IF EXISTS partie CASCADE");
    }

    private boolean tableExist() {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeQuery("SELECT * FROM partie");
        } catch (SQLException ex) {
            Log.writeLog(ex.getMessage());
            return false;
        }
        return true;
    }

    private Date getDateTableMiseAJour() {
        PreparedStatement prepare = this.prepareStatement("SELECT date_mise_a_jour FROM mise_a_jour");
        ResultSet result = this.executePreparedStatement(prepare);
        Date date = null;
        if (this.aResult(result)) {
            String string = this.getStringResultByString(result, "date_mise_a_jour");
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(string);
            } catch (ParseException ex) {
                Logger.getLogger(CreationBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.closeResultSet(result);
        if (date == null) {
            Calendar dateMiseAJour = Calendar.getInstance();
            dateMiseAJour.set(2010, Calendar.JANUARY, 01, 00, 00);
            date = dateMiseAJour.getTime();
        }
        return date;
    }

    private Date getDateDerniereMiseAJour() {
        Calendar dateMiseAJour = Calendar.getInstance();
        dateMiseAJour.set(2015, Calendar.JUNE, 27, 15, 0);
        return dateMiseAJour.getTime();
    }

    private void miseAJourDatabaseScript() {
        this.pcsControlleurVue.firePropertyChange("miseAJour", null, null);
        this.dropAllTable();
        this.createAllTable();
        this.pcsControlleurVue.firePropertyChange("miseAJourTerminer", null, null);
    }

    private void creationTableMiseAJour() {
        String requete = "CREATE TABLE mise_a_jour (date_mise_a_jour TIMESTAMP)";
        if (this.exexuteUpdate(requete)) {
            this.insertionDonneeTableMiseAJour();
        }
    }

    private void insertionDonneeTableMiseAJour() {
        this.exexuteUpdate("INSERT INTO mise_a_jour (date_mise_a_jour) VALUES (NOW())");
    }

    private void creationTablePartie() {
        String requete = "CREATE TABLE partie ("
                + "id INT PRIMARY KEY,"
                + "temps_heure INT,"
                + "temps_min INT,"
                + "temps_seconde INT"
                + ")";
        if (this.exexuteUpdate(requete)) {
            this.insertionDonneePartie();
        }
    }

    private void insertionDonneePartie() {
        this.exexuteUpdate("INSERT INTO partie (id) VALUES (1)");
        this.exexuteUpdate("INSERT INTO partie (id) VALUES (2)");
        this.exexuteUpdate("INSERT INTO partie (id) VALUES (3)");
    }

    private void creationTablePerso() {
        String requete = "CREATE TABLE perso ("
                + "id_partie INT,"
                + "id INT,"
                + "nom VARCHAR(32),"
                + "type VARCHAR(32),"
                + "niv INT,"
                + "pv INT,"
                + "puissance INT,"
                + "magie INT,"
                + "capacite INT,"
                + "vitesse INT,"
                + "chance INT,"
                + "defense INT,"
                + "resistance INT,"
                + "constitution INT,"
                + "experience INT,"
                + "PRIMARY KEY (id_partie, id),"
                + "FOREIGN KEY (id_partie) REFERENCES partie(id)"
                + ")";
        this.exexuteUpdate(requete);
    }
    
    private void creationTablePersoObjet () {
        String requete = "CREATE TABLE perso_objet ("
                + "id_partie INT,"
                + "id_perso INT,"
                + "position INT,"
                + "nom VARCHAR(32),"
                + "type VARCHAR(32),"
                + "use INT,"
                + "PRIMARY KEY (id_partie, id_perso, position),"
                + "FOREIGN KEY (id_partie) REFERENCES partie(id),"
                + "FOREIGN KEY (id_perso, id_partie) REFERENCES perso(id, id_partie)"
                + ")";
        this.exexuteUpdate(requete);
    }
}
