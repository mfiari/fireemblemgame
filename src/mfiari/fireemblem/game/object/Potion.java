/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.object;

import mfiari.fireemblem.game.character.Character;

/**
 *
 * @author mike
 */
public class Potion extends Objet {

    private final int pv;

    public Potion(String nom, int utilisation, int prix, ObjetType typeObjet, int pv) {
        super(nom, utilisation, prix, typeObjet);
        this.pv = pv;
    }

    public void use(Character character) {
        character.setPv(character.getPv() + this.pv);
    }
}
