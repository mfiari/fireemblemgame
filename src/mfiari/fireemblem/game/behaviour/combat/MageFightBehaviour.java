/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.behaviour.combat;

import mfiari.fireemblem.game.object.WeaponType;

/**
 *
 * @author mike
 */
public class MageFightBehaviour extends FightBehaviour {

    public MageFightBehaviour () {
        this.weaponTypes = new WeaponType[3];
        this.weaponTypes[0] = WeaponType.feu;
        this.weaponTypes[1] = WeaponType.foudre;
        this.weaponTypes[2] = WeaponType.vent;
    }
}
