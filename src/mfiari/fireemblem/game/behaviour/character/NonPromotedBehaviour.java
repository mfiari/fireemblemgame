/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.behaviour.character;

/**
 *
 * @author mike
 */
public class NonPromotedBehaviour extends CharacterBehaviour {

    public NonPromotedBehaviour() {
        
        this.chanceBase = 0;

        this.pvMax = 40;
        this.puissanceMax = 20;
        this.magieMax = 15;
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
