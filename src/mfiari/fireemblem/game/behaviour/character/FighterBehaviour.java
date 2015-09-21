package mfiari.fireemblem.game.behaviour.character;

import mfiari.fireemblem.game.behaviour.character.CharacterBehaviour;
import mfiari.fireemblem.game.character.CharacterType;


public class FighterBehaviour extends CharacterBehaviour {
    
    public FighterBehaviour () {
    	
    	this.name = "Combattant";
    	this.charactersType = CharacterType.combattant;
    	
        this.pvBase = 20;
        this.puissanceBase = 5;
        this.capaciteBase = 2;
        this.vitesseBase = 4;
        this.chanceBase = 0;
        this.defBase = 2;
        this.resistanceBase = 0;
        this.constitutionBase = 11;

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
