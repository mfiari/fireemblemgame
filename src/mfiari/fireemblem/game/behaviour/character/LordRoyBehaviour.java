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
public class LordRoyBehaviour extends CharacterBehaviour {
    
    public LordRoyBehaviour () {
    	
    	this.name = "Lord";
    	this.charactersType = CharacterType.lord_roy;
    	
        this.pvBase = 18;
        this.puissanceBase = 5;
        this.capaciteBase = 5;
        this.vitesseBase = 7;
        this.chanceBase = 0;
        this.defBase = 5;
        this.resistanceBase = 0;
        this.constitutionBase = 7;

        this.pvMax = 60;
        this.puissanceMax = 20;
        this.capaciteMax = 20;
        this.vitesseMax = 20;
        this.chanceMax = 30;
        this.defMax = 20;
        this.resistanceMax = 20;
        this.constitutionMax = 20;
        
        this.promoted = false;
        this.power = 3;
        this.classBonusA = 0;
        this.classBonusB = 0;
    }
}