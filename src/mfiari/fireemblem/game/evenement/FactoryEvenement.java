/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.evenement;

import java.util.ArrayList;
import java.util.List;
import mfiari.fireemblem.game.chapters.Chapters;
import mfiari.fireemblem.game.chapters.Level;
import mfiari.fireemblem.game.connexionBD.Partie;
import mfiari.lib.game.evenements.Evenement;
import mfiari.lib.game.evenements.EvenementDialogue;

/**
 *
 * @author mike
 */
public class FactoryEvenement {
    
    private final Level level;
    private final Chapters chapter;
    
    public FactoryEvenement (Level level, Chapters chapter) {
        this.level = level;
        this.chapter = chapter;
    }
    
    public List<Evenement> createEvenement () {
        List<Evenement> evenements = new ArrayList<>();
        switch (level) {
            case binding_blade :
                break;
            case blazing_sword :
                switch (chapter) {
                    case chapter1 :
                        EvenementDebutChapitre debutChapitre = new EvenementDebutChapitre("Debut");
                        EvenementDialogue dialogue = new EvenementDialogue();
                        debutChapitre.addEvenement(dialogue);
                        break;
                }
                break;
            case path_of_radiance :
                break;
        }
        return evenements;
    }
    
}
