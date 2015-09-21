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
public class SageBehaviour extends CharacterBehaviour {

    public SageBehaviour() {

        this.name = "sage";
        this.charactersType = CharacterType.sage;

        this.pvBase = 20;
        this.puissanceBase = 2;
        this.magieBase = 5;
        this.capaciteBase = 4;
        this.vitesseBase = 5;
        this.defBase = 5;
        this.resistanceBase = 6;
        this.constitutionBase = 7;

        this.pvMax = 60;
        this.puissanceMax = 15;
        this.magieMax = 29;
        this.capaciteMax = 29;
        this.vitesseMax = 26;
        this.chanceMax = 35;
        this.defMax = 20;
        this.resistanceMax = 26;
        this.constitutionMax = 20;
        
        this.promoted = true;
        this.power = 3;
        this.classBonusA = 20;
        this.classBonusB = 60;
    }
}
