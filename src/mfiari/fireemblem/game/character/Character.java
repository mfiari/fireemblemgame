/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.character;

import mfiari.fireemblem.game.behaviour.character.CharacterBehaviour;
import mfiari.fireemblem.game.behaviour.combat.FightBehaviour;
import mfiari.fireemblem.game.behaviour.move.MoveBehaviour;
import mfiari.fireemblem.game.behaviour.talk.BehaviourTalk;
import mfiari.fireemblem.game.controler.Chapter;
import mfiari.fireemblem.game.factory.CharacterFactory;
import mfiari.fireemblem.game.object.ClassicWeapon;
import mfiari.fireemblem.game.object.Objet;
import mfiari.fireemblem.game.object.ObjetType;
import mfiari.fireemblem.game.object.Weapon;
import mfiari.fireemblem.game.organizations.Organization;
import mfiari.fireemblem.game.strategy.AttackNearestStrategy;
import mfiari.fireemblem.game.strategy.NoActionStrategy;
import mfiari.fireemblem.game.strategy.NoMovementStrategy;
import mfiari.fireemblem.game.strategy.RangeStrategy;
import mfiari.fireemblem.game.strategy.Strategy;
import java.beans.PropertyChangeEvent;
import java.util.Objects;
import mfiari.lib.game.position.Position;

/**
 *
 * @author mike
 */
public class Character {
    
    private String name;
    protected FightBehaviour behaviour;
    protected BehaviourTalk speech;
    protected MoveBehaviour move;
    protected Organization subject;
    protected String operatingState;

    private int niv;
    private int pv;
    private int pvGagne;
    private int puissanceGagne;
    private int magieGagne;
    private int capaciteGagne;
    private int vitesseGagne;
    private int chanceGagne;
    private int defGagne;
    private int resistanceGagne;
    private int constitutionGagne;
    private int experience;

    protected int pourcentagePv;
    protected int pourcentagePuissance;
    protected int pourcentageMagie;
    protected int pourcentageCapacite;
    protected int pourcentageVitesse;
    protected int pourcentageChance;
    protected int pourcentageDefense;
    protected int pourcentageResistance;

    private Position position;

    private CharacterBehaviour comportementPersonnage;
    private Strategy strategie;

    private final Objet[] objets;
    private Objet objetGagne;

    private boolean aJouer;

    private Status status;
    private Etat etat;

    public enum Status {

        normal, boss;
    }

    public enum Etat {

        normal, attendre;
    }

    public Character(String nom, Organization organisation) {
        this.name = nom;
        this.behaviour = null;
        this.speech = null;
        this.subject = organisation;
        if (this.subject != null) {
            this.subject.attach(this);
        }
        this.experience = 0;
        this.objets = new Objet[5];
        this.aJouer = false;
        this.niv = 1;
        this.status = Status.normal;
        this.etat = Etat.normal;
        if (organisation != null) {
            organisation.addListener((PropertyChangeEvent evt) -> {
                if (evt.getPropertyName().equals("ordre")) {
                    ordre((Chapter.Ordre) evt.getOldValue());
                }
            });
        }
    }

    public Character(Character perso) {
        this.niv = perso.niv;
        this.pv = perso.pv;
        this.pvGagne = perso.pvGagne;
        this.puissanceGagne = perso.puissanceGagne;
        this.magieGagne = perso.magieGagne;
        this.capaciteGagne = perso.capaciteGagne;
        this.vitesseGagne = perso.vitesseGagne;
        this.chanceGagne = perso.chanceGagne;
        this.defGagne = perso.defGagne;
        this.resistanceGagne = perso.resistanceGagne;
        this.constitutionGagne = perso.constitutionGagne;
        this.experience = perso.experience;
        this.pourcentagePv = perso.pourcentagePv;
        this.pourcentagePuissance = perso.pourcentagePuissance;
        this.pourcentageMagie = perso.pourcentageMagie;
        this.pourcentageCapacite = perso.pourcentageCapacite;
        this.pourcentageVitesse = perso.pourcentageVitesse;
        this.pourcentageChance = perso.pourcentageChance;
        this.pourcentageDefense = perso.pourcentageDefense;
        this.pourcentageResistance = perso.pourcentageResistance;
        this.position = perso.position;
        this.comportementPersonnage = perso.comportementPersonnage;
        this.behaviour = perso.behaviour;
        this.move = perso.move;
        this.strategie = perso.strategie;
        this.objets = perso.objets;
        this.objetGagne = perso.objetGagne;
        this.aJouer = perso.aJouer;
    }

    public int getNiv() {
        return niv;
    }

    public void setNiv(int niv) {
        this.niv = niv;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        if (pv < 0) {
            this.pv = 0;
        } else if (pv > this.getPvMax()) {
            this.pv = this.getPvMax();
        } else {
            this.pv = pv;
        }
    }

    public void changeNiveau(int niv) {
        while (this.niv < niv) {
            this.niveauSuivant();
        }
        this.pv = this.getPvMax();
    }

    public boolean estKo() {
        return this.pv == 0;
    }

    public int getPvMax() {
        return this.pvGagne + this.comportementPersonnage.getPvBase();
    }

    public void setPvGagne(int pv) {
        this.pvGagne = pv;
    }

    public int getPvGagne() {
        return this.pvGagne;
    }

    public int getPuissance() {
        return this.comportementPersonnage.getPuissanceBase() + this.puissanceGagne;
    }

    public void setPuissanceGagne(int puissance) {
        this.puissanceGagne = puissance;
    }

    public int getPuissanceGagne() {
        return this.puissanceGagne;
    }

    public int getMagie() {
        return this.comportementPersonnage.getMagieBase() + this.magieGagne;
    }

    public void setMagieGagne(int magie) {
        this.magieGagne = magie;
    }

    public int getMagieGagne() {
        return this.magieGagne;
    }

    public int getCapacite() {
        return this.comportementPersonnage.getCapaciteBase() + this.capaciteGagne;
    }

    public void setCapaciteGagne(int capacite) {
        this.capaciteGagne = capacite;
    }

    public int getCapaciteGagne() {
        return this.capaciteGagne;
    }

    public int getVitesse() {
        return this.comportementPersonnage.getVitesseBase() + this.vitesseGagne;
    }

    public void setVitesseGagne(int vitesse) {
        this.vitesseGagne = vitesse;
    }

    public int getVitesseGagne() {
        return this.vitesseGagne;
    }

    public int getChance() {
        return this.comportementPersonnage.getChanceBase() + this.chanceGagne;
    }

    public void setChanceGagne(int chance) {
        this.chanceGagne = chance;
    }

    public int getChanceGagner() {
        return this.chanceGagne;
    }

    public int getDef() {
        return this.comportementPersonnage.getDefBase() + this.defGagne;
    }

    public void setDefGagne(int def) {
        this.defGagne = def;
    }

    public int getDefGagne() {
        return this.defGagne;
    }

    public int getResistance() {
        return this.comportementPersonnage.getResistanceBase() + this.resistanceGagne;
    }

    public void setResistanceGagne(int resistance) {
        this.resistanceGagne = resistance;
    }

    public int getResistanceGagne() {
        return this.resistanceGagne;
    }

    public int getConstitution() {
        return this.comportementPersonnage.getConstitutionBase() + this.constitutionGagne;
    }

    public void setConstitutionGagne(int constitution) {
        this.constitutionGagne = constitution;
    }

    public void setOperatingState(String operatingState) {
        this.operatingState = operatingState;
    }

    public String getOperatingState() {
        return operatingState;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void ajouterExperience(int experience) {
        if (this.niv < 20 || (!this.comportementPersonnage.isPromoted() && this.niv == 20)) {
            if (this.experience + experience >= 100) {
                this.experience = this.experience + experience - 100;
                this.niveauSuivant();
            } else {
                this.experience = this.experience + experience;
            }
        }
    }

    public void soin() {
        this.pv = this.getPvMax();
    }

    public void niveauSuivant() {
        if (this.niv < 19) {
            this.niv++;
            if (this.getPvMax() < this.comportementPersonnage.getPvMax()) {
                int alea = (int) (Math.random() * 100 + 1);
                if (alea < this.pourcentagePv) {
                    this.pvGagne++;
                }
            }
            if (this.getPuissance() < this.comportementPersonnage.getPuissanceMax()) {
                int alea = (int) (Math.random() * 100 + 1);
                if (alea < this.pourcentagePuissance) {
                    this.puissanceGagne++;
                }
            }
            if (this.getCapacite() < this.comportementPersonnage.getCapaciteMax()) {
                int alea = (int) (Math.random() * 100 + 1);
                if (alea < this.pourcentageCapacite) {
                    this.capaciteGagne++;
                }
            }
            if (this.getVitesse() < this.comportementPersonnage.getVitesseMax()) {
                int alea = (int) (Math.random() * 100 + 1);
                if (alea < this.pourcentageVitesse) {
                    this.vitesseGagne++;
                }
            }
            if (this.getChance() < this.comportementPersonnage.getChanceMax()) {
                int alea = (int) (Math.random() * 100 + 1);
                if (alea < this.pourcentageChance) {
                    this.chanceGagne++;
                }
            }
            if (this.getDef() < this.comportementPersonnage.getDefMax()) {
                int alea = (int) (Math.random() * 100 + 1);
                if (alea < this.pourcentageDefense) {
                    this.defGagne++;
                }
            }
            if (this.getResistance() < this.comportementPersonnage.getResistanceMax()) {
                int alea = (int) (Math.random() * 100 + 1);
                if (alea < this.pourcentageResistance) {
                    this.resistanceGagne++;
                }
            }
        } else if (this.niv == 19 && this.comportementPersonnage.hasPromotedClass()) {
            CharacterFactory characterFactory = new CharacterFactory();
            Character character = characterFactory.createCharacter(this.getName(), this.subject, this.comportementPersonnage.getPromotedClass());
            this.behaviour = character.behaviour;
            this.move = character.move;
            this.comportementPersonnage = character.comportementPersonnage;
            this.niv = 1;
            this.experience = 0;
        }
    }

    public int getPourcentagePv() {
        return pourcentagePv;
    }

    public void setPourcentagePv(int pourcentagePv) {
        this.pourcentagePv = pourcentagePv;
    }

    public int getPourcentagePuissance() {
        return pourcentagePuissance;
    }

    public void setPourcentagePuissance(int pourcentagePuissance) {
        this.pourcentagePuissance = pourcentagePuissance;
    }

    public int getPourcentageMagie() {
        return pourcentageMagie;
    }

    public void setPourcentageMagie(int pourcentageMagie) {
        this.pourcentageMagie = pourcentageMagie;
    }

    public int getPourcentageCapacite() {
        return pourcentageCapacite;
    }

    public void setPourcentageCapacite(int pourcentageCapacite) {
        this.pourcentageCapacite = pourcentageCapacite;
    }

    public int getPourcentageVitesse() {
        return pourcentageVitesse;
    }

    public void setPourcentageVitesse(int pourcentageVitesse) {
        this.pourcentageVitesse = pourcentageVitesse;
    }

    public int getPourcentageChance() {
        return pourcentageChance;
    }

    public void setPourcentageChance(int pourcentageChance) {
        this.pourcentageChance = pourcentageChance;
    }

    public int getPourcentageDefense() {
        return pourcentageDefense;
    }

    public void setPourcentageDefense(int pourcentageDefense) {
        this.pourcentageDefense = pourcentageDefense;
    }

    public int getPourcentageResistance() {
        return pourcentageResistance;
    }

    public void setPourcentageResistance(int pourcentageResistance) {
        this.pourcentageResistance = pourcentageResistance;
    }

    public CharacterBehaviour getComportementPersonnage() {
        return comportementPersonnage;
    }

    public void setComportementPersonnage(CharacterBehaviour comportementPersonnage) {
        this.comportementPersonnage = comportementPersonnage;
        this.pv = this.getPvMax();
    }

    public Strategy getStrategie() {
        return strategie;
    }

    public void setStrategie(Strategy strategie) {
        this.strategie = strategie;
    }

    public boolean isaJouer() {
        return aJouer;
    }

    public void setaJouer(boolean aJouer) {
        this.aJouer = aJouer;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void ajouterObjet(Objet obj) {
        for (int i = 0; i < this.objets.length; i++) {
            if (this.objets[i] == null) {
                this.objets[i] = obj;
                break;
            }
        }
    }

    public Objet[] getObjets() {
        return objets;
    }
    
    public boolean hasObjet (ObjetType objetType) {
        for (Objet objet : this.objets) {
            if (objet != null && objet.getType() == objetType) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasObjet (Objet obj) {
        for (Objet objet : this.objets) {
            if (objet != null && objet.equals(obj)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasObjetGagne () {
        return this.objetGagne != null;
    }
    
    public boolean isObjetGagne (Objet obj) {
        if (this.objetGagne != null) {
            return this.objetGagne.equals(obj);
        }
        return false;
    }
    
    public Objet getObjetGagne () {
        Objet obj = this.objetGagne;
        this.objetGagne = null;
        return obj;
    }
    
    public void setObjetGagne (Objet obj) {
        this.objetGagne = obj;
    }
    
    public boolean hasArme () {
        for (Objet objet : this.objets) {
            if (objet != null && objet instanceof ClassicWeapon && this.behaviour.isWeaponAvailable((Weapon)objet)) {
                return true;
            }
        }
        return false;
    }

    public ClassicWeapon getArme() {
        if (this.objets[0] instanceof ClassicWeapon) {
            return (ClassicWeapon) this.objets[0];
        }
        return null;
    }

    public void setArme(int indice) {
        Objet obj = this.objets[0];
        this.objets[0] = this.objets[indice];
        this.objets[indice] = obj;
    }

    public int getNbCombat() {
        return 0;
    }

    public int getNbVictoire() {
        return 0;
    }

    public int getNbDefaite() {
        return 0;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Etat getEtat() {
        return this.etat;
    }

    public void update() {
        this.operatingState = subject.getOperatingMode();
    }

    public Organization getSubject() {
        return subject;
    }

    public void setSubject(Organization subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FightBehaviour getFightBehaviour() {
        return behaviour;
    }

    public void setFightBehaviour(FightBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public BehaviourTalk getSpeech() {
        return speech;
    }

    public void setSpeech(BehaviourTalk speech) {
        this.speech = speech;
    }

    public MoveBehaviour getMove() {
        return move;
    }

    public void setMoveBehaviour(MoveBehaviour move) {
        this.move = move;
    }

    public void ordre(Chapter.Ordre ordre) {
        switch (ordre) {
            case immobile:
                this.setStrategie(new NoMovementStrategy(this));
                break;
            case plusProche:
                this.setStrategie(new AttackNearestStrategy(this));
                break;
            case portee:
                this.setStrategie(new RangeStrategy(this));
                break;
            case rien:
                this.setStrategie(new NoActionStrategy(this));
                break;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.comportementPersonnage);
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
        final Character other = (Character) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        return this.comportementPersonnage.equals(other.comportementPersonnage);
    }

}