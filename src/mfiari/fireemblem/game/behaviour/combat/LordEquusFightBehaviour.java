package mfiari.fireemblem.game.behaviour.combat;

import mfiari.fireemblem.game.object.WeaponType;

public class LordEquusFightBehaviour extends FightBehaviour {

    public LordEquusFightBehaviour() {
        this.weaponTypes = new WeaponType[2];
        this.weaponTypes[0] = WeaponType.epee;
        this.weaponTypes[1] = WeaponType.lance;
    }

}
