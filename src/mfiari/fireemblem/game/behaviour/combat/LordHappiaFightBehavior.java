package mfiari.fireemblem.game.behaviour.combat;

import mfiari.fireemblem.game.object.WeaponType;

public class LordHappiaFightBehavior extends FightBehaviour {

    public LordHappiaFightBehavior() {
        this.weaponTypes = new WeaponType[2];
        this.weaponTypes[0] = WeaponType.hache;
        this.weaponTypes[1] = WeaponType.epee;
    }

}
