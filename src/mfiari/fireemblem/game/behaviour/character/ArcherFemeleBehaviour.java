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
public class ArcherFemeleBehaviour extends NonPromotedBehaviour {

    public ArcherFemeleBehaviour() {

        this.name = "archer";
        this.charactersType = CharacterType.archer_female;

        this.pvBase = 17;
        this.puissanceBase = 3;
        this.magieBase = 0;
        this.capaciteBase = 4;
        this.vitesseBase = 4;
        this.defBase = 3;
        this.resistanceBase = 0;
        this.constitutionBase = 6;
    }
}
