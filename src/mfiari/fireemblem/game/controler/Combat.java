/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.controler;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.character.Character.Status;
import mfiari.fireemblem.game.media.son.MusiqueUtil;
import mfiari.fireemblem.game.object.HealWeapon;
import mfiari.fireemblem.game.object.Weapon;
import mfiari.fireemblem.game.terrain.Case;
import java.util.ArrayList;
import java.util.List;
import mfiari.lib.game.controlleur.ControlleurVue;
import mfiari.lib.game.media.Musique;

/**
 *
 * @author mike
 */
public class Combat extends ControlleurVue {

    private final Character perso1;
    private final Character perso2;
    private final Chapter chapitre;
    private final Case square;
    private final int range;
    private Musique musique;
    private MusiqueUtil musiqueUtil;

    public final String SIMULER_COMBAT = "simulerCombat";
    public final String COMBAT = "combat";
    public final String ANNIMATION_ATTAQUE_PERSO = "annimation_attaque_perso";
    public final String ANNIMATION_CRITIQUE_PERSO = "annimation_critique_perso";
    public final String ANNIMATION_DISTANCE_PERSO = "annimation_distance_perso";
    public final String ANNIMATION_DISTANCE_CRITIQUE_PERSO = "annimation_distance_critique_perso";
    public final String ANNIMATION_ESQUIVE_PERSO = "annimation_esquive_perso";
    public final String ANNIMATION_SOIN_PERSO = "annimation_soin_perso";
    public final String MODIFY_CLASS_PERSO = "modify_class_perso";
    public final String MODIFY_EXP_PERSO = "modify_exp_perso";
    public final String MODIFY_NIV_PERSO = "modify_niv_perso";
    public final String MODIFY_PV_PERSO1 = "modify_pv_perso1";
    public final String MODIFY_PV_PERSO2 = "modify_pv_perso2";
    public final String FIN_COMBAT = "finCombat";
    public final String FIN_SIMULER_COMBAT = "finSimulerCombat";

    private int statPerso1[];
    private int statPerso2[];
    
    public final int INDICE_PV_STAT = 0;
    public final int INDICE_FORCE_STAT = 1;
    public final int INDICE_NB_ATTAQUE_STAT = 2;
    public final int INDICE_PREC_STAT = 3;
    public final int INDICE_CRITIQUE_STAT = 4;

    private List<Integer> pile;

    private boolean continuer;

    public Combat(Character perso1, Character perso2, Case square, int range, Chapter chapitre) {
        super(true);
        this.perso1 = perso1;
        this.perso2 = perso2;
        this.chapitre = chapitre;
        this.square = square;
        this.range = range;
        this.musiqueUtil = new MusiqueUtil();
    }

    public Character getPerso1() {
        return perso1;
    }

    public Character getPerso2() {
        return perso2;
    }
    
    public int[] calculeStatPerso (Character perso1, Character perso2) {
        int pvPerso = perso1.getPv();
        int forcePerso;
        int nbAttaquePerso;
        int precPerso;
        int critiquePerso;
        if (perso1.hasArme() && perso1.getArme().getMinRange() <= range && perso1.getArme().getMaxRange() >= range) {
            forcePerso = this.calculeForce(perso1, perso2) - this.calculeDefense(perso2);
            if (forcePerso < 0) {
                forcePerso = 0;
            }
            nbAttaquePerso = this.calculeNbAttaque(perso1, perso2);
            precPerso = this.calculePrecision(perso1, perso2);
            critiquePerso = this.calculeCritique(perso1);
        } else {
            forcePerso = -1;
            nbAttaquePerso = -1;
            precPerso = -1;
            critiquePerso = -1;
        }
        int statPerso[] = {pvPerso, forcePerso, nbAttaquePerso, precPerso, critiquePerso};
        return statPerso;
    }

    public void simulerCombat() {
        int statPerso1[] = calculeStatPerso(this.perso1, this.perso2);
        int statPerso2[] = calculeStatPerso(this.perso2, this.perso1);
        this.pcsControlleurVue.firePropertyChange(SIMULER_COMBAT, statPerso1, statPerso2);
    }

    public void finSimulation() {
        this.pcsControlleurVue.firePropertyChange(FIN_SIMULER_COMBAT, null, null);
    }

    public int calculeForce(Character perso1, Character perso2) {
        int weaponTriangleBonus = this.weaponTriangleBonus(perso1, perso2);
        int effectiveCoefficient = 1;
        int supportBonus = 0;
        int force = perso1.getPuissance() + ((perso1.getArme().getPower() + weaponTriangleBonus) * effectiveCoefficient) + supportBonus;
        return force;
    }

    public int weaponTriangleBonus(Character perso1, Character perso2) {
        if (perso2.hasArme()) {
            if (perso1.getArme().getWeaponType().isGood(perso2.getArme().getWeaponType())) {
                return 1;
            } else if (perso1.getArme().getWeaponType().isBad(perso2.getArme().getWeaponType())) {
                return -1;
            }
        }
        return 0;
    }

    public int calculeDefense(Character perso1) {
        int supportBonus = 0;
        int terrainBonus = this.square.getDef();
        return perso1.getDef() + supportBonus + terrainBonus;
    }

    public int calculeVitesseAttaque(Character perso) {
        if (perso.hasArme()) {
            return perso.getVitesse() - (perso.getArme().getWeight() - perso.getConstitution());
        } else {
            return perso.getVitesse() + perso.getConstitution();
        }
    }

    public int calculeNbAttaque(Character perso1, Character perso2) {
        int attackSpeedPerso1 = this.calculeVitesseAttaque(perso1);
        int attackSpeedPerso2 = this.calculeVitesseAttaque(perso2);
        return ((attackSpeedPerso1 - attackSpeedPerso2) >= 4) ? 2 : 1;
    }

    public int calculePrecision(Character perso1, Character perso2) {
        int weaponTriangleBonus = this.weaponTriangleBonus(perso1, perso2) * 15;
        int supportBonus = 0;
        int sRankBonus = perso1.getArme().getRang() == Weapon.Rang.S ? 5 : 0;
        int precision = perso1.getArme().getAccuracy() + (perso1.getCapacite() * 2) + (perso1.getChance() / 2) + supportBonus
                + weaponTriangleBonus + sRankBonus;
        int total = precision - this.calculeEsquive(perso2);
        if (total > 100) {
            total = 100;
        }
        if (total < 0) {
            total = 0;
        }
        return total;
    }

    public int calculeEsquive(Character perso) {
        int supportBonus = 0;
        int terrainBonus = this.square.getEsq();
        int tacticianBonus = 0;
        return (this.calculeVitesseAttaque(perso) * 2) + perso.getChance() + supportBonus + terrainBonus + tacticianBonus;
    }

    public int calculeCritique(Character perso1) {
        int supportBonus = 0;
        int criticalBonus = 0;
        int sRankBonus = perso1.getArme().getRang() == Weapon.Rang.S ? 5 : 0;
        int critique = perso1.getArme().getCritical() + (perso1.getCapacite() / 2) + supportBonus + criticalBonus + sRankBonus;
        return critique;
    }

    public void run() {
        this.musique = this.musiqueUtil.getMusique(MusiqueUtil.COMBAT);
        this.musique.start(true);
        this.statPerso1 = calculeStatPerso(this.perso1, this.perso2);
        this.statPerso2 = calculeStatPerso(this.perso2, this.perso1);

        this.pcsControlleurVue.firePropertyChange(COMBAT, statPerso1, statPerso2);

        int pvPerso1 = this.perso1.getPv();
        int pvPerso2 = this.perso2.getPv();

        this.pile = new ArrayList<>();
        if (this.statPerso1[INDICE_NB_ATTAQUE_STAT] > 0) {
            pile.add(1);
        }
        if (this.statPerso2[INDICE_NB_ATTAQUE_STAT] > 0) {
            pile.add(2);
        }
        if (this.statPerso1[INDICE_NB_ATTAQUE_STAT] > 1) {
            pile.add(1);
        } else if (this.statPerso2[INDICE_NB_ATTAQUE_STAT] > 1) {
            pile.add(2);
        }
        this.continuer = true;
        while (!pile.isEmpty()) {
            if (this.continuer) {
                this.continuer = false;
                this.continuer();
            }
            this.attendre(100);
        }
        if (this.chapitre.getPlateauDeJeu().getPersonnages().contains(this.perso1) && !this.perso1.estKo()) {
            boolean damage = pvPerso2 > this.perso2.getPv();
            this.gagneExp(this.perso1, this.perso2, damage);
        } else if (this.chapitre.getPlateauDeJeu().getPersonnages().contains(this.perso2) && !this.perso2.estKo()) {
            boolean damage = pvPerso1 > this.perso1.getPv();
            this.gagneExp(this.perso2, this.perso1, damage);
        }
        this.musique.stop();
        this.pcsControlleurVue.firePropertyChange(FIN_COMBAT, null, null);
    }

    public void doContinue() {
        this.continuer = true;
    }

    protected void continuer() {
        if (!pile.isEmpty()) {
            if (!this.perso1.estKo() && !this.perso2.estKo()) {
                if (this.pile.get(0) == 1) {
                    this.attaquePerso(statPerso1, this.perso1, this.perso2);
                } else {
                    this.attaquePerso(statPerso2, this.perso2, this.perso1);
                }
            } else {
                this.doContinue();
            }
            this.pile.remove(0);
        }
    }
    
    public void soin () {
        this.musique = this.musiqueUtil.getMusique(MusiqueUtil.SOIN);
        this.musique.start(true);
        this.statPerso1 = calculeStatPerso(this.perso1, this.perso2);
        this.statPerso2 = calculeStatPerso(this.perso2, this.perso1);

        this.pcsControlleurVue.firePropertyChange(COMBAT, statPerso1, statPerso2);
        
        HealWeapon weapon = (HealWeapon) this.perso1.getObjets()[0];
        
        int pv = this.perso2.getPv();
        this.pcsControlleurVue.firePropertyChange(ANNIMATION_SOIN_PERSO, this.perso1, null);
        this.perso2.setPv(this.perso2.getPv() + (weapon.getBaseHeal() + (this.perso1.getMagie() / 2)));
        int damage = pv - this.perso2.getPv();
        this.pcsControlleurVue.firePropertyChange(MODIFY_PV_PERSO2, damage, null);
        this.setExp(this.perso1, weapon.getExp());
        this.musique.stop();
        this.pcsControlleurVue.firePropertyChange(FIN_COMBAT, null, null);
    }

    private int attaquePerso(int stats[], Character attaquant, Character attaquer) {
        int prec;
        int critique;
        int damage = 0;

        prec = (int) (Math.random() * 100 + 1);
        critique = (int) (Math.random() * 100 + 1);

        if (critique < stats[2]) {
            int pv = attaquer.getPv();
            this.pcsControlleurVue.firePropertyChange(ANNIMATION_CRITIQUE_PERSO, attaquant, null);
            attaquer.setPv(attaquer.getPv() - (stats[INDICE_FORCE_STAT] * 3));
            damage = pv - attaquer.getPv();
            if (attaquant.equals(this.perso1)) {
                this.pcsControlleurVue.firePropertyChange(MODIFY_PV_PERSO2, damage, null);
            } else {
                this.pcsControlleurVue.firePropertyChange(MODIFY_PV_PERSO1, damage, null);
            }
        } else {
            if (prec < stats[INDICE_PREC_STAT]) {
                int pv = attaquer.getPv();
                this.pcsControlleurVue.firePropertyChange(ANNIMATION_ATTAQUE_PERSO, attaquant, null);
                attaquer.setPv(attaquer.getPv() - stats[INDICE_FORCE_STAT]);
                damage = pv - attaquer.getPv();
                if (attaquant.equals(this.perso1)) {
                    this.pcsControlleurVue.firePropertyChange(MODIFY_PV_PERSO2, damage, null);
                } else {
                    this.pcsControlleurVue.firePropertyChange(MODIFY_PV_PERSO1, damage, null);
                }
            } else {
                this.pcsControlleurVue.firePropertyChange(ANNIMATION_ATTAQUE_PERSO, attaquant, null);
                this.pcsControlleurVue.firePropertyChange(ANNIMATION_ESQUIVE_PERSO, attaquer, null);
            }
        }
        return damage;
    }

    private void gagneExp(Character attaquant, Character attaquer, boolean damage) {
        int experience = this.experience(attaquant, attaquer, damage);
        this.setExp(attaquant, experience);
    }
    
    private void setExp (Character perso, int experience) {
        Character tmpPerso = new Character(perso);
        perso.ajouterExperience(experience);
        if (!tmpPerso.getComportementPersonnage().equals(perso.getComportementPersonnage())) {
            this.pcsControlleurVue.firePropertyChange(MODIFY_EXP_PERSO, 100 - tmpPerso.getExperience(), tmpPerso);
            this.pcsControlleurVue.firePropertyChange(MODIFY_CLASS_PERSO, tmpPerso, perso);
            perso.soin();
        } else if (tmpPerso.getNiv() != perso.getNiv()) {
            this.pcsControlleurVue.firePropertyChange(MODIFY_EXP_PERSO, experience, tmpPerso);
            this.pcsControlleurVue.firePropertyChange(MODIFY_NIV_PERSO, tmpPerso, perso);
        } else {
            this.pcsControlleurVue.firePropertyChange(MODIFY_EXP_PERSO, experience, tmpPerso);
        }
    }

    private int experience(Character perso, Character ennemy, boolean damage) {
        int experience = 1;
        if (damage) {
            if (ennemy.estKo()) {
                experience = (this.experienceFromDoingDamage(perso, ennemy)
                        + (this.experienceFromDefeating(perso, ennemy) + 20 + this.bossBonus(ennemy) + 0)) * 1;
            } else {
                experience = this.experienceFromDoingDamage(perso, ennemy);
            }
        }
        if (experience <= 0) {
            experience = 1;
        }
        return experience;
    }

    private int experienceFromDoingDamage(Character perso, Character ennemy) {
        return (31 + ((ennemy.getNiv() + ennemy.getComportementPersonnage().getClassBonusA())
                - (perso.getNiv() + perso.getComportementPersonnage().getClassBonusA())))
                / perso.getComportementPersonnage().getPower();
    }

    private int experienceFromDefeating(Character perso, Character ennemy) {
        int experience = ((ennemy.getNiv() * ennemy.getComportementPersonnage().getPower())
                + ennemy.getComportementPersonnage().getClassBonusB()) - (((perso.getNiv()
                * perso.getComportementPersonnage().getPower())
                + perso.getComportementPersonnage().getClassBonusB()) / 1);
        if (experience < 0) {
            experience = 0;
        }
        return experience;
    }

    private int bossBonus(Character ennemy) {
        return ennemy.getStatus() == Status.boss ? 40 : 0;
    }
}
