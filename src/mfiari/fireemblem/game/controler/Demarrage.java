/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.controler;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.character.CharacterType;
import mfiari.fireemblem.game.connexionBD.Connexion;
import mfiari.fireemblem.game.connexionBD.Partie;
import mfiari.fireemblem.game.factory.CharacterFactory;
import mfiari.fireemblem.game.media.son.MusiqueUtil;
import mfiari.fireemblem.game.object.Objet;
import mfiari.fireemblem.game.object.ObjetFactory;
import mfiari.fireemblem.game.object.ObjetType;
import mfiari.fireemblem.game.organizations.Organization;
import java.util.ArrayList;
import java.util.List;
import mfiari.lib.game.connexionBD.ConnexionBD;
import mfiari.lib.game.controlleur.ControlleurVue;
import mfiari.lib.game.media.Musique;
import mfiari.lib.game.texte.Langue;
import mfiari.lib.game.util.Config;

/**
 *
 * @author mike
 */
public class Demarrage extends ControlleurVue {
    
    
    public final String CHOIX_LANGUE = "choixLangue";
    public final String CHOIX_NOM = "choixNom";
    public final String CHOIX_PARTIE = "choixPartie";
    public final String MENU = "menu";
    public final String PROLOGUE = "prologue";
    
    public final int NOUVELLE_PARTIE_ACTION = 1;
    public final int CONTINUER_ACTION = 2;
    public final int RECOMMENCER_ACTION = 3;
    public final int EFFACER_ACTION = 4;
    public final int EXTRA_ACTION = 5;
    
    private Musique musique;
    
    private String yourName;

    public Demarrage() {
        super(true);
    }
    
    public void setNom (String nom) {
        this.yourName = nom;
    }
    
    public void jeu () {
        MusiqueUtil musiqueUtil = new MusiqueUtil();
        this.musique = musiqueUtil.getMusique(MusiqueUtil.THEME);
        this.musique.start(true);
        this.debutJeu();
        this.menu();
    }
    
    private void debutJeu() {
        String availableLangues = Config.getPropertie("langues");
        Langue[] langues;
        if (availableLangues == null || availableLangues.isEmpty()) {
            langues = Langue.values();
        } else {
            String[] languesTab = availableLangues.split(",");
            langues = new Langue[languesTab.length];
            for (int i =  0 ; i < languesTab.length ; i++) {
                langues[i] = Langue.valueOf(languesTab[i]);
            }
        }
        do {
            this.pcsControlleurVue.firePropertyChange(CHOIX_LANGUE, langues, null);
        } while (this.choix < 1 && this.choix > langues.length);
    }
    
    private void menu () {
        ConnexionBD connexionBD = new ConnexionBD();
        Connexion connexion = new Connexion(connexionBD.getConnexionHSQL(Config.getPropertie("hsql_location"), Config.getPropertie("hsql_user"), Config.getPropertie("hsql_password")));
        List<Partie> parties = connexion.getParties();
        connexionBD.fermerConnexionHSQL();
        do {
            this.pcsControlleurVue.firePropertyChange(MENU, parties, null);
        } while (this.choix < 1 && this.choix > 5);
        switch (this.choix) {
            case 1 :
                nouvellePartie(parties);
                break;
            case 2 :
                break;
            case 3 :
                break;
            case 4 :
                break;
            case 5 :
                extra();
                break;
        }
    }
    
    private void nouvellePartie (List<Partie> parties) {
        do {
            this.pcsControlleurVue.firePropertyChange(CHOIX_PARTIE, parties, null);
        } while (this.choix < 1 && this.choix > parties.size());
        Partie choosenPartie = null;
        for (Partie partie : parties) {
            if (partie.getId() == this.choix) {
                choosenPartie = partie;
                break;
            }
        }
        if (!choosenPartie.isEmpty()) {
            
        }
        //this.pcsControlleurVue.firePropertyChange(CHOIX_NOM, null, null);
        this.musique.stop();
        //this.pcsControlleurVue.firePropertyChange(PROLOGUE, null, null);
        List<Character> characters = new ArrayList<>();
        CharacterFactory characterFactory = new CharacterFactory();
        ObjetFactory objetFactory = new ObjetFactory();
        Organization organization = new Organization();
        Character eliwood = characterFactory.createCharacter("eliwood", organization, CharacterType.lord_eliwood);
        Objet epee_fer_eliwood = objetFactory.createObjet("epee-fer", ObjetType.epee_fer);
        eliwood.ajouterObjet(epee_fer_eliwood);
        characters.add(eliwood);
        Character hector = characterFactory.createCharacter("hector", organization, CharacterType.lord_hector);
        Objet hache_fer_hector = objetFactory.createObjet("hache-fer", ObjetType.hache_fer);
        hector.ajouterObjet(hache_fer_hector);
        characters.add(hector);
        Character lyn = characterFactory.createCharacter("lyn", organization, CharacterType.lord_lyn);
        Objet epee_fer_lyn = objetFactory.createObjet("epee-fer", ObjetType.epee_fer);
        lyn.ajouterObjet(epee_fer_lyn);
        characters.add(lyn);
        Character roy = characterFactory.createCharacter("roy", organization, CharacterType.lord_roy);
        Objet epee_fer_roy = objetFactory.createObjet("epee-fer", ObjetType.epee_fer);
        roy.ajouterObjet(epee_fer_roy);
        characters.add(roy);
        Character ike = characterFactory.createCharacter("ike", organization, CharacterType.ranger);
        Objet epee_fer_ike = objetFactory.createObjet("epee-fer", ObjetType.epee_fer);
        ike.ajouterObjet(epee_fer_ike);
        characters.add(ike);
        Character mist = characterFactory.createCharacter("mist", organization, CharacterType.clerc_mist);
        Objet soin_mist = objetFactory.createObjet("soin", ObjetType.soin);
        mist.ajouterObjet(soin_mist);
        characters.add(mist);
        choosenPartie.setCharacters(characters);
        ConnexionBD connexionBD = new ConnexionBD();
        Connexion connexion = new Connexion(connexionBD.getConnexionHSQL(Config.getPropertie("hsql_location"), Config.getPropertie("hsql_user"), Config.getPropertie("hsql_password")));
        connexion.nouvellePartie(characters, choosenPartie);
        Game game = new Game(choosenPartie);
        game.start();
    }
    
    private void extra () {
        Extra extra = new Extra();
        extra.menu();
    }
     
}
