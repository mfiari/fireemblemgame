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
public class ClassicWeapon extends Weapon {
    
    
    private final int power;
    private final int critical;
    
    public ClassicWeapon (String nom, int utilisation, int prix, Rang rang, int minRange, int maxRange, int weight, int power, int accuracy, int critical, 
            WeaponType typeArme, ObjetType typeObjet) {
        super(nom, utilisation, prix, rang, minRange, maxRange, weight, accuracy, typeArme, typeObjet);
        this.power = power;
        this.critical = critical;
    }

    public int getPower() {
        return power;
    }

    public int getCritical() {
        return critical;
    }
    
}
