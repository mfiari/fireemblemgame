/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.connexionBD;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.object.Objet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mfiari.lib.game.connexionBD.Methode;

/**
 *
 * @author mike
 */
public class Connexion extends Methode {
    
    private int partie;

    public Connexion(Connection conn) {
        super(conn);
        this.partie = 1;
    }
    
    public List<Partie> getParties () {
        List<Partie> parties = new ArrayList<>();
        PreparedStatement prepare = this.prepareStatement("SELECT id, temps_heure, temps_min, temps_seconde FROM partie");
        ResultSet result = this.executePreparedStatement(prepare);
        while (this.aResult(result)) {
            Partie partie = new Partie(this.getIntResultByString(result, "id"));
            int temps_heure = this.getIntResultByString(result, "temps_heure");
            int temps_min = this.getIntResultByString(result, "temps_min");
            int temps_seconde = this.getIntResultByString(result, "temps_seconde");
            if (temps_heure == 0 && temps_min == 0 && temps_seconde == 0) {
                partie.setName("-- Vide --");
                partie.setEmpty(true);
            } else {
                partie.setName("Fire emblem");
                partie.setEmpty(false);
            }
            partie.setEnCours(false);
            partie.setHour(temps_heure);
            partie.setMinute(temps_min);
            partie.setSeconde(temps_seconde);
            parties.add(partie);
        }
        this.closeResultSet(result);
        return parties;
    } 
    
    public void nouvellePartie (List<Character> characters, Partie partie) {
        PreparedStatement prepare = this.prepareStatement("DELETE FROM perso_objet WHERE id_partie = ?");
        this.setParameterInt(prepare, partie.getId(), 1);
        this.executeUpdatePreparedStatement(prepare);
        prepare = this.prepareStatement("DELETE FROM perso WHERE id_partie = ?");
        this.setParameterInt(prepare, partie.getId(), 1);
        this.executeUpdatePreparedStatement(prepare);
        prepare = this.prepareStatement("UPDATE partie SET temps_heure = ?, temps_min = ?, temps_seconde = ? WHERE id = ?");
        this.setParameterInt(prepare, 0, 1);
        this.setParameterInt(prepare, 0, 2);
        this.setParameterInt(prepare, 0, 3);
        this.setParameterInt(prepare, partie.getId(), 4);
        this.executeUpdatePreparedStatement(prepare);
        this.enregistrerPerso(characters);
    }
    
    private void enregistrerPerso (List<Character> characters) {
        int id = 1;
        for (Character character : characters) {
            PreparedStatement prepare = this.prepareStatement("insert into perso values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            this.setParameterInt(prepare, this.partie, 1);
            this.setParameterInt(prepare, id, 2);
            this.setParameterString(prepare, character.getName(), 3);
            this.setParameterString(prepare, character.getComportementPersonnage().getType().name(), 4);
            this.setParameterInt(prepare, character.getNiv(), 5);
            this.setParameterInt(prepare, character.getPvGagne(), 6);
            this.setParameterInt(prepare, character.getPuissanceGagne(), 7);
            this.setParameterInt(prepare, character.getMagieGagne(), 8);
            this.setParameterInt(prepare, character.getCapaciteGagne(), 9);
            this.setParameterInt(prepare, character.getVitesseGagne(), 10);
            this.setParameterInt(prepare, character.getChanceGagner(), 11);
            this.setParameterInt(prepare, character.getDefGagne(), 12);
            this.setParameterInt(prepare, character.getResistanceGagne(), 13);
            this.setParameterInt(prepare, 0, 14);
            this.setParameterInt(prepare, character.getExperience(), 15);
            this.executeUpdatePreparedStatement(prepare);
            this.enregistrerObjetPerso(character.getObjets(), id);
            id++;
        }
    }
    
    private void enregistrerObjetPerso (Objet[] objets, int id_perso) {
        for (int i = 0 ; i < objets.length ; i++) {
            if (objets[i] != null) {
                PreparedStatement prepare = this.prepareStatement("insert into perso_objet values(?,?,?,?,?,?)");
                this.setParameterInt(prepare, this.partie, 1);
                this.setParameterInt(prepare, id_perso, 2);
                this.setParameterInt(prepare, i+1, 3);
                this.setParameterString(prepare, objets[i].getName(), 4);
                this.setParameterString(prepare, objets[i].getType().name(), 5);
                this.setParameterInt(prepare, objets[i].getUtilisation(), 6);
                this.executeUpdatePreparedStatement(prepare);
            }
        }
    }
}
