package mfiari.fireemblem.game.behaviour.character;

import mfiari.fireemblem.game.behaviour.character.CharacterBehaviour;
import mfiari.fireemblem.game.character.CharacterType;

public class GeneralBehaviour extends CharacterBehaviour {
    
    public GeneralBehaviour () {
    	
    	this.name = "General";
    	this.charactersType = CharacterType.general;
        
        this.pvBase = 21;
        this.puissanceBase = 7;
        this.capaciteBase = 4;
        this.vitesseBase = 3;
        this.chanceBase = 0;
        this.defBase = 11;
        this.resistanceBase = 3;
        this.constitutionBase = 15;

        this.pvMax = 60;
        this.puissanceMax = 29;
        this.capaciteMax = 27;
        this.vitesseMax = 24;
        this.chanceMax = 30;
        this.defMax = 30;
        this.resistanceMax = 25;
        this.constitutionMax = 20;
        
        this.promoted = true;
        this.power = 3;
        this.classBonusA = 20;
        this.classBonusB = 60;
    }
}