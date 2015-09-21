package mfiari.fireemblem.game.behaviour.combat;

import mfiari.fireemblem.game.object.WeaponType;

public class LordLynFightBehaviour extends FightBehaviour {

    public LordLynFightBehaviour() {
        this.weaponTypes = new WeaponType[1];
        this.weaponTypes[0] = WeaponType.epee;
    }

}
