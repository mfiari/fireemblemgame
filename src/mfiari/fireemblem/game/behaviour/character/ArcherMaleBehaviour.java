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
public class ArcherMaleBehaviour extends NonPromotedBehaviour {

    public ArcherMaleBehaviour() {

        this.name = "archer";
        this.charactersType = CharacterType.archer_male;

        this.pvBase = 18;
        this.puissanceBase = 4;
        this.magieBase = 0;
        this.capaciteBase = 4;
        this.vitesseBase = 3;
        this.defBase = 3;
        this.resistanceBase = 0;
        this.constitutionBase = 7;
    }
}