package mfiari.fireemblem.game.behaviour.combat;

import mfiari.fireemblem.game.object.WeaponType;

public class GeneralFightBehaviour extends FightBehaviour {

    public GeneralFightBehaviour() {
        this.weaponTypes = new WeaponType[2];
        this.weaponTypes[0] = WeaponType.hache;
        this.weaponTypes[1] = WeaponType.lance;
    }

}
