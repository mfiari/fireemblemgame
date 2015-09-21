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
public class BerserkerBehaviour extends CharacterBehaviour {

    public BerserkerBehaviour() {

        this.name = "berserker";
        this.charactersType = CharacterType.berserker;

        this.pvBase = 26;
        this.puissanceBase = 7;
        this.magieBase = 0;
        this.capaciteBase = 6;
        this.vitesseBase = 7;
        this.chanceBase = 0;
        this.defBase = 6;
        this.resistanceBase = 0;
        this.constitutionBase = 13;

        this.pvMax = 60;
        this.puissanceMax = 30;
        this.magieMax = 20;
        this.capaciteMax = 26;
        this.vitesseMax = 28;
        this.chanceMax = 35;
        this.defMax = 24;
        this.resistanceMax = 22;
        this.constitutionMax = 20;
        
        this.promoted = true;
        this.power = 3;
        this.classBonusA = 20;
        this.classBonusB = 60;
    }
}
