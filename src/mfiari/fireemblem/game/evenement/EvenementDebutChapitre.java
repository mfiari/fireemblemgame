/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.evenement;

import java.util.ArrayList;
import java.util.List;
import mfiari.fireemblem.game.controler.Chapter;
import mfiari.lib.game.evenements.Evenement;
import mfiari.lib.game.jeu.Jeu;

/**
 *
 * @author mike
 */
public class EvenementDebutChapitre extends Evenement {
    
    List<Evenement> evenements;
    
    public EvenementDebutChapitre (String title) {
        super(title);
        this.evenements = new ArrayList<>();
    }
    
    public void addEvenement (Evenement evenement) {
        this.evenements.add(evenement);
    }
    
    @Override
    public boolean estActiver (Jeu jeu) {
        return false;
    }
    
    public void activeEvenement (Chapter chapter) {
        for (Evenement evenement : evenements) {
            evenement.activeEvenement(chapter);
        }
        this.FinirEvenement();
    }
    
}