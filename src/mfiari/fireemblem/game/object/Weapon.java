/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.object;

/**
 *
 * @author mike
 */
public abstract class Weapon extends Objet {
    
    public enum Rang {
        Prf, S, A, B, C, D, E, none;
    }
    
    private final Rang rang;
    private final int minRange;
    private final int maxRange;
    private final int weight;
    private final int accuracy;
    private final WeaponType weaponType;
    
    public Weapon (String nom, int utilisation, int prix, Rang rang, int minRange, int maxRange, int weight, int accuracy, 
            WeaponType typeArme, ObjetType typeObjet) {
        super(nom, utilisation, prix, typeObjet);
        this.rang = rang;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.weight = weight;
        this.accuracy = accuracy;
        this.weaponType = typeArme;
    }

    public Rang getRang() {
        return rang;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getWeight() {
        return weight;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}