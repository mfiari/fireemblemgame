/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.behaviour.character;

import mfiari.fireemblem.game.character.CharacterType;

/**
 *
 * @author mike
 */
public class ClercMistBehaviour extends NonPromotedBehaviour {

    public ClercMistBehaviour() {

        this.name = "clerc";
        this.charactersType = CharacterType.clerc_mist;

        this.pvBase = 18;
        this.puissanceBase = 1;
        this.magieBase = 4;
        this.capaciteBase = 4;
        this.vitesseBase = 7;
        this.chanceBase = 6;
        this.defBase = 2;
        this.resistanceBase = 7;
        this.constitutionBase = 5;
    }
}