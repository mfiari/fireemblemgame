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
public class BerserkerFightBehaviour extends FightBehaviour {

    public BerserkerFightBehaviour () {
        this.weaponTypes = new WeaponType[1];
        this.weaponTypes[0] = WeaponType.hache;
    }
}
