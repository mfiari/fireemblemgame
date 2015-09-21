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
public class RangerBehaviour extends NonPromotedBehaviour {

    public RangerBehaviour() {

        this.name = "ranger";
        this.charactersType = CharacterType.ranger;

        this.pvBase = 19;
        this.puissanceBase = 5;
        this.magieBase = 1;
        this.capaciteBase = 6;
        this.vitesseBase = 7;
        this.chanceBase = 6;
        this.defBase = 5;
        this.resistanceBase = 0;
        this.constitutionBase = 9;
    }
}