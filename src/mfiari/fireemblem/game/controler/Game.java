/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.controler;

import mfiari.fireemblem.game.chapters.Chapters;
import mfiari.fireemblem.game.chapters.Difficulte;
import mfiari.fireemblem.game.character.CharacterType;
import mfiari.fireemblem.game.connexionBD.Partie;
import mfiari.fireemblem.game.evenement.EvenementRecrutement;
import mfiari.fireemblem.game.factory.CharacterFactory;
import mfiari.fireemblem.game.factory.FactoryTerrain;
import mfiari.fireemblem.game.organizations.Organization;
import mfiari.fireemblem.game.terrain.GamePlatform;
import mfiari.fireemblem.game.terrain.Terrain;
import java.util.ArrayList;
import java.util.List;
import mfiari.fireemblem.game.chapters.Level;
import mfiari.lib.game.controlleur.ControlleurVue;
import mfiari.lib.game.evenements.EvenementDialogue;
import mfiari.lib.game.personnage.Gens;
import mfiari.lib.game.personnage.Habitant;
import mfiari.lib.game.position.Orientation;

/**
 *
 * @author mike
 */
public class Game extends ControlleurVue {
    
    public final String CHOIX_MAP = "choixMap";
    public final String CHOIX_DIFFICULTE = "choixDifficulte";
    public final String MENU = "menu";
    public final String OUTFIT = "outfit";
    private final Partie partie;
    
    public Game (Partie partie) {
        super(true);
        this.partie = partie;
    }
    
    public Partie getPartie () {
        return this.partie;
    }
    
    public void start () {
        if (this.partie.isEnCours()) {
            
        } else {
            do {
                this.pcsControlleurVue.firePropertyChange(MENU, null, null);
                switch (this.choix) {
                    case 1 :
                        outfit();
                        break;
                    case 2 :
                        manage();
                        break;
                    case 3 :
                        support();
                        break;
                    case 4 :
                        info();
                        break;
                    case 5 :
                        save();
                        break;
                    case 6 :
                        end();
                        break;
                }
            } while (true);
        }
    }
    
    private void outfit () {
        this.pcsControlleurVue.firePropertyChange(OUTFIT, null, null);
    }
    
    private void manage () {
        
    }
    
    private void support () {
        
    }
    
    private void info () {
        
    }
    
    private void save () {
        
    }
    
    private void end () {
        do {
            this.pcsControlleurVue.firePropertyChange(CHOIX_MAP, null, null);
        } while (this.choix < 1 && this.choix > 3);
        Level level = Level.values()[this.choix-1];
        Chapters[] chapters = Chapters.values();
        do {
            this.pcsControlleurVue.firePropertyChange(CHOIX_DIFFICULTE, level, chapters);
        } while (this.choix < 1 && this.choix > chapters.length);
        Chapters chapter = chapters[this.choix-1];
        FactoryTerrain factoryTerrain = new FactoryTerrain(level, chapter);
        Terrain terrain = factoryTerrain.createGameEnvironnement();
        GamePlatform gamePlatform = terrain.createGamePlatform();
        List<EvenementRecrutement> evenements = this.getEvenements();
        Chapter chap = new Chapter(level.name(), gamePlatform, new Organization(), "battre le boss", evenements);
        Menu menu = new Menu(chap, this.partie);
        menu.start();
    }
    
    private List<EvenementRecrutement> getEvenements () {
        List<EvenementRecrutement> evenements = new ArrayList<>();
        /*CharacterFactory factory = new CharacterFactory();
        mfiari.fireemblem.game.character.Character nino = factory.createCharacter("nino", null, CharacterType.mage_nino);
        mfiari.fireemblem.game.character.Character eliwood = factory.createCharacter("Eliwood", null, CharacterType.lord_eliwood);
        mfiari.fireemblem.game.character.Character hector = factory.createCharacter("hector", null, CharacterType.lord_hector);
        mfiari.fireemblem.game.character.Character lyn = factory.createCharacter("lyn", null, CharacterType.lord_lyn);
        EvenementRecrutement recrutementNino = new EvenementRecrutement("nino", nino);
        EvenementDialogue dialogue = new EvenementDialogue();
        Gens eliwood2 = new Habitant("Eliwood", 0, 0, null, Orientation.droite);
        eliwood2.addListeDialogueEvenement(dialogue, "Nous sommes venu te délivrer", 1);
        dialogue.ajouterGens(eliwood2);
        Gens nino2 = new Habitant("nino", 0, 0, null, Orientation.droite);
        nino2.addListeDialogueEvenement(dialogue, "Merci", 2);
        dialogue.ajouterGens(nino2);
        recrutementNino.addAlie(eliwood, dialogue);
        evenements.add(recrutementNino);*/
        return evenements;
    }
    
}
