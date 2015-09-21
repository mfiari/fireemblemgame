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
public class MyrmidonBehaviour extends NonPromotedBehaviour {

    public MyrmidonBehaviour() {

        this.name = "myrmidon";
        this.charactersType = CharacterType.myrmidon;

        this.pvBase = 16;
        this.puissanceBase = 4;
        this.magieBase = 0;
        this.capaciteBase = 7;
        this.vitesseBase = 7;
        this.defBase = 2;
        this.resistanceBase = 0;
        this.constitutionBase = 8;
    }
}
