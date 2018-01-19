/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.controler;

import mfiari.fireemblem.game.connexionBD.Partie;
import mfiari.lib.game.controlleur.ControlleurVue;
import mfiari.fireemblem.game.character.Character;
import mfiari.lib.game.controlleur.Vues;
import mfiari.lib.game.position.Position;

/**
 *
 * @author mike
 */
public class Menu extends ControlleurVue {
    
    public final String MENU = "menu";
    public final String PICK_UNIT = "pickUnit";
    private final Chapter chapter;
    private final Partie partie;
    
    public Menu (Chapter chapter, Partie partie) {
        super(true);
        this.chapter = chapter;
        this.partie = partie;
    }
    
    public Chapter getChapter () {
        return this.chapter;
    }
    
    public Partie getPartie () {
        return this.partie;
    }
    
    public void init () {
        /* On ajoute les perso obligatoire */
        this.chapter.getPlateauDeJeu().getPersonnagesObligatoire().stream().filter((character) -> (this.partie.getPersonnages().contains(character))).forEach((character) -> {
            Character myCharacter = this.partie.getPersonnages().get(this.partie.getPersonnages().indexOf(character));
            myCharacter.setPosition(character.getPosition());
            this.chapter.getPlateauDeJeu().ajouterPersonnage(myCharacter);
        }); 
        /* On ajoute les autres perso */
        int minAvailableCharacter = this.chapter.getPlateauDeJeu().getMaxUnits() > this.partie.getPersonnages().size() ? this.partie.getPersonnages().size() : this.chapter.getPlateauDeJeu().getMaxUnits();
        for (int i = 0 ; i < this.partie.getPersonnages().size() ; i++) {
            if (this.getChapter().getPlateauDeJeu().getPersonnages().size() == minAvailableCharacter) {
                break;
            }
            if (!this.chapter.getPlateauDeJeu().getPersonnages().contains(this.partie.getPersonnages().get(i))) {
                Character character = this.partie.getPersonnages().get(i);
                for (Position position : this.chapter.getPlateauDeJeu().getPositions()) {
                    boolean isPositionAvailable = true;
                    for (Character perso : this.chapter.getPlateauDeJeu().getPersonnages()) {
                        if (perso.getPosition().equalsXY(position)) {
                            isPositionAvailable = false;
                            break;
                        }
                    }
                    if (isPositionAvailable) {
                        character.setPosition(position);
                        break;
                    }
                }
                this.chapter.getPlateauDeJeu().getPersonnages().add(character);
            }
        }
    }
    
    public void start () {
        this.init();
        do {
            this.pcsControlleurVue.firePropertyChange(MENU, null, null);
            switch (this.choix) {
                case 1 :
                    pickUnits();
                    break;
                case 2 :
                    trade();
                    break;
                case 3 :
                    save();
                    break;
                case 4 :
                    map();
                    break;
                case 5 :
                    playChapter();
                    break;
            }
        } while (true);
    }
    
    private void pickUnits () {
        this.pcsControlleurVue.firePropertyChange(PICK_UNIT, null, null);
    }
    
    private void trade () {
        
    }
    
    private void save () {
        
    }
    
    private void map () {
        
    }
    
    private void playChapter () {
        Vues.createVue(this.chapter);
        this.chapter.run();
    }
    
}
