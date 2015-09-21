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
public class CavalierMaleBehaviour extends NonPromotedBehaviour {

    public CavalierMaleBehaviour() {

        this.name = "cavalier";
        this.charactersType = CharacterType.cavalier;

        this.pvBase = 20;
        this.puissanceBase = 5;
        this.magieBase = 0;
        this.capaciteBase = 2;
        this.vitesseBase = 5;
        this.chanceBase = 0;
        this.defBase = 6;
        this.resistanceBase = 0;
        this.constitutionBase = 9;
    }
}
