package mfiari.fireemblem.game.behaviour.combat;

import mfiari.fireemblem.game.object.WeaponType;

public class LordEliwoodFightBehaviour extends FightBehaviour {

    public LordEliwoodFightBehaviour() {
        this.weaponTypes = new WeaponType[1];
        this.weaponTypes[0] = WeaponType.epee;
    }

}
