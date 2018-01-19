/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.controler;

import java.util.ArrayList;
import java.util.List;
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
import mfiari.lib.game.connexionBD.ConnexionBD;
import mfiari.lib.video.ListeDeVideo;
import mfiari.lib.video.Video;
import mfiari.lib.game.controlleur.ControlleurVue;
import mfiari.lib.game.liste.ListeDeMusique;
import mfiari.lib.game.media.Musique;
import mfiari.lib.game.util.Config;

/**
 *
 * @author mike
 */
public class Extra extends ControlleurVue {
    
    public final String AFFICHE_MENU = "afficherMenu";
    
    private ListeDeMusique musiques;
    private int musiqueEnCour;
    private ListeDeVideo videos;
    private int videoEnCour;

    public Extra() {
        super(true);
    }
    
    public void menu () {
        do {
            this.pcsControlleurVue.firePropertyChange("afficherMenu", null, null);
            switch (this.choix) {
                case (1):
                    this.enLigne();
                    break;
                case (2):
                    this.map();
                    break;
                case (3):
                    this.arene();
                    break;
                case (4):
                    this.musique();
                    break;
                case (5):
                    this.video();
                    break;
                case (6):
                    this.configuration();
                    break;
            }
        } while (choix != 0);
    }
    
    private ListeDeMusique getMusiques () {
        return this.musiques;
    }
    
    private Musique getMusique () {
        return this.musiques.getMusique(this.musiqueEnCour);
    }
    
    private ListeDeVideo getVideos () {
        return this.videos;
    }
    
    private Video getVideo () {
        return this.videos.getVideo(this.videoEnCour);
    }
    
    private void enLigne() {
        
    }
    
    private void map() {
        ConnexionBD connexionBD = new ConnexionBD();
        Connexion connexion = new Connexion(connexionBD.getConnexionHSQL(Config.getPropertie("hsql_location"), Config.getPropertie("hsql_user"), Config.getPropertie("hsql_password")));
        List<Partie> parties = connexion.getParties();
        Partie choosenPartie = null;
        for (Partie partie : parties) {
            if (partie.getId() == this.choix) {
                choosenPartie = partie;
                break;
            }
        }
        if (!choosenPartie.isEmpty()) {
            
        }
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
        connexion.nouvellePartie(characters, choosenPartie);
        connexionBD.fermerConnexionHSQL();
        Game game = new Game(choosenPartie);
        game.start();
    }
    
    private void arene () {
        
    }

    private void musique() {
        MusiqueUtil musiqueUtil = new MusiqueUtil();
        this.musiques = musiqueUtil.getAll();
        do {
            this.pcsControlleurVue.firePropertyChange("musique", null, null);
            if (this.choix != -1) {
                this.musiqueEnCour = this.choix -1;
                if (this.getMusique() != null) {
                    this.getMusique().start(true);
                    do {
                        this.pcsControlleurVue.firePropertyChange("playMusique", null, null);
                        if (this.choix == 1) {
                            this.getMusique().stop();
                        }
                    } while (this.choix != 0);
                    this.getMusique().terminer();
                }
            }
        } while (this.choix != -1);
    }

    private void video() {
        //this.videos = Videos.getListeDeVideo();
        do {
            this.pcsControlleurVue.firePropertyChange("video", null, null);
            if (this.choix != -1) {
                this.videoEnCour = this.choix -1;
                this.pcsControlleurVue.firePropertyChange("playVideo", null, null);
            }
        } while (this.choix != -1);
    }
    
    private void configuration() {
        
    }
    
}
