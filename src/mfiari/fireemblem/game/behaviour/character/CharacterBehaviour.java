package mfiari.fireemblem.game.behaviour.character;

import mfiari.fireemblem.game.character.CharacterType;
import java.util.Objects;

public class CharacterBehaviour {

    protected String name;

    protected int pvBase;
    protected int puissanceBase;
    protected int magieBase;
    protected int capaciteBase;
    protected int vitesseBase;
    protected int chanceBase;
    protected int defBase;
    protected int resistanceBase;
    protected int constitutionBase;

    protected int pvMax;
    protected int puissanceMax;
    protected int magieMax;
    protected int capaciteMax;
    protected int vitesseMax;
    protected int chanceMax;
    protected int defMax;
    protected int resistanceMax;
    protected int constitutionMax;

    protected CharacterType charactersType;
    protected boolean promoted;
    protected int power;
    protected int classBonusA;
    protected int classBonusB;

    private CharacterType promtedClass;
    
    public CharacterBehaviour () {
        this.chanceBase = 0;
    }

    public String getName() {
        return this.name;
    }

    public CharacterType getType() {
        return this.charactersType;
    }

    public int getPvBase() {
        return pvBase;
    }

    public int getPuissanceBase() {
        return puissanceBase;
    }

    public int getMagieBase() {
        return magieBase;
    }

    public int getCapaciteBase() {
        return capaciteBase;
    }

    public int getVitesseBase() {
        return vitesseBase;
    }

    public int getChanceBase() {
        return chanceBase;
    }

    public int getDefBase() {
        return defBase;
    }

    public int getResistanceBase() {
        return resistanceBase;
    }

    public int getConstitutionBase() {
        return this.constitutionBase;
    }

    public int getPvMax() {
        return pvMax;
    }

    public int getPuissanceMax() {
        return puissanceMax;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public int getVitesseMax() {
        return vitesseMax;
    }

    public int getChanceMax() {
        return chanceMax;
    }

    public int getDefMax() {
        return defMax;
    }

    public int getResistanceMax() {
        return resistanceMax;
    }

    public int getConstitutionMax() {
        return this.constitutionMax;
    }

    public boolean isPromoted() {
        return this.promoted;
    }

    public int getPower() {
        return this.power;
    }

    public int getClassBonusA() {
        return this.classBonusA;
    }

    public int getClassBonusB() {
        return this.classBonusB;
    }

    public void setPromotedClass(CharacterType characterType) {
        this.promtedClass = characterType;
    }

    public CharacterType getPromotedClass() {
        return this.promtedClass;
    }

    public boolean hasPromotedClass() {
        return this.promtedClass != null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.charactersType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CharacterBehaviour other = (CharacterBehaviour) obj;
        return this.charactersType == other.charactersType;
    }

}
