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
public class HealWeapon extends Weapon {
    
    private final int baseHeal;
    private final int exp;
    
    public HealWeapon (String nom, int utilisation, int prix, Rang rang, int minRange, int maxRange, int weight, int baseHeal, int accuracy, int exp,
            WeaponType typeArme, ObjetType typeObjet) {
        super(nom, utilisation, prix, rang, minRange, maxRange, weight, accuracy, typeArme, typeObjet);
        this.baseHeal = baseHeal;
        this.exp = exp;
    }
    
    public int getBaseHeal () {
        return this.baseHeal;
    }
    
    public int getExp () {
        return this.exp;
    }
    
}
