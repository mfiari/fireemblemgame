package mfiari.fireemblem.game.behaviour.combat;

import mfiari.fireemblem.game.object.WeaponType;

public class KnightFightBehaviour extends FightBehaviour {

    public KnightFightBehaviour() {
        this.weaponTypes = new WeaponType[1];
        this.weaponTypes[0] = WeaponType.lance;
    }

}
