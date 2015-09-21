/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.evenement;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.controler.Chapter;
import java.util.HashMap;
import java.util.Map;
import mfiari.lib.game.evenements.Evenement;
import mfiari.lib.game.evenements.EvenementDialogue;
import mfiari.lib.game.jeu.Jeu;

/**
 *
 * @author mike
 */
public class EvenementRecrutement extends Evenement {
    
    private final Character recrue;
    private final Map<Character, EvenementDialogue> recrutement;
    
    public EvenementRecrutement (String title, Character recrue) {
        super(title);
        this.recrue = recrue;
        this.recrutement = new HashMap<>();
    }
    
    public void addAlie (Character character, EvenementDialogue dialogue) {
        this.recrutement.put(character, dialogue);
    }
    
    @Override
    public boolean estActiver (Jeu jeu) {
        return false;
    }
    
    public boolean estActiver (Chapter chapter) {
        Character perso = chapter.getPersoEnCours();
        System.out.println(perso.getName());
        Character cible = chapter.getPersoAttaquer();
        if (cible != null) {
            System.out.println(cible.getName());
        }
        return this.recrue.equals(cible) && this.recrutement.containsKey(perso);
    }

    @Override
    public void activeEvenement(Jeu jeu) {
        
    }
    
    public void activeEvenement (Chapter chapter) {
        Character perso = chapter.getPersoEnCours();
        this.recrutement.get(perso).activeEvenement(chapter);
        Character character = chapter.getPlateauDeJeu().getAnnexes().remove(chapter.getPlateauDeJeu().getAnnexes().indexOf(this.recrue));
        chapter.getPlateauDeJeu().getPersonnages().add(character);
        chapter.deplacePerso(character, character.getPosition());
        this.FinirEvenement();
    }
    
}
