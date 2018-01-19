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
public class BanditBehaviour extends NonPromotedBehaviour {

    public BanditBehaviour() {

        this.name = "bandit";
        this.charactersType = CharacterType.bandit;

        this.pvBase = 22;
        this.puissanceBase = 5;
        this.magieBase = 0;
        this.capaciteBase = 2;
        this.vitesseBase = 4;
        this.defBase = 3;
        this.resistanceBase = 0;
        this.constitutionBase = 13;
    }
}