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
public class MageBehaviour extends NonPromotedBehaviour {

    public MageBehaviour() {

        this.name = "mage";
        this.charactersType = CharacterType.mage;

        this.pvBase = 16;
        this.puissanceBase = 0;
        this.magieBase = 3;
        this.capaciteBase = 2;
        this.vitesseBase = 3;
        this.defBase = 3;
        this.resistanceBase = 3;
        this.constitutionBase = 6;
    }
}