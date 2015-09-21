package mfiari.fireemblem.game.behaviour.character;

import mfiari.fireemblem.game.behaviour.character.CharacterBehaviour;
import mfiari.fireemblem.game.character.CharacterType;

public class LordSparthaBehaviour extends CharacterBehaviour {
    
    public LordSparthaBehaviour () {
    	
    	this.name = "Lord Spartha";
    	this.charactersType = CharacterType.lord_spatha;
    	
        this.pvBase = 19;
        this.puissanceBase = 6;
        this.capaciteBase = 9;
        this.vitesseBase = 9;
        this.chanceBase = 0;
        this.defBase = 5;
        this.resistanceBase = 5;
        this.constitutionBase = 6;

        this.pvMax = 60;
        this.puissanceMax = 24;
        this.capaciteMax = 29;
        this.vitesseMax = 30;
        this.chanceMax = 30;
        this.defMax = 22;
        this.resistanceMax = 22;
        this.constitutionMax = 25;
        
        this.promoted = true;
        this.power = 3;
        this.classBonusA = 20;
        this.classBonusB = 60;
    }
}