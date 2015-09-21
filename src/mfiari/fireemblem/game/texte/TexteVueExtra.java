/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.texte;

import mfiari.lib.game.texte.Texte;
import static mfiari.lib.game.texte.Texte.getInstance;

/**
 *
 * @author mike
 */
public class TexteVueExtra extends Texte {
    
    public String enLigne;
    public String map;
    public String arene;
    public String musiqueVideo;
    public String configuration;
    public String retour;
    public String cbParticipant;
    public String ok;
    public String cbJoueur;
    public String transferDonneeSite;
    public String configServeur;
    public String configAdresseSite;
    public String modifier;
    public String annuler;
    public String play;
    public String pause;
    public String choixLangue;
    
    public static TexteVueExtra getInstance () {
        return (TexteVueExtra) getInstance(TexteVueExtra.class);
    }
    
}
