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
public class SoldierBehaviour extends NonPromotedBehaviour {

    public SoldierBehaviour() {

        this.name = "soldier";
        this.charactersType = CharacterType.soldat;

        this.pvBase = 20;
        this.puissanceBase = 3;
        this.magieBase = 0;
        this.capaciteBase = 2;
        this.vitesseBase = 2;
        this.defBase = 2;
        this.resistanceBase = 0;
        this.constitutionBase = 6;
    }
}
