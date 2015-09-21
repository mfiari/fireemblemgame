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
public class MercenaryBehaviour extends NonPromotedBehaviour {

    public MercenaryBehaviour() {

        this.name = "mercenary";
        this.charactersType = CharacterType.mercenaire;

        this.pvBase = 18;
        this.puissanceBase = 4;
        this.magieBase = 1;
        this.capaciteBase = 8;
        this.vitesseBase = 8;
        this.defBase = 4;
        this.resistanceBase = 0;
        this.constitutionBase = 9;
    }
}
