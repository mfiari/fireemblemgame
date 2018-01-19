/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.controler;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.character.CharacterType;
import mfiari.fireemblem.game.evenement.EvenementRecrutement;
import mfiari.fireemblem.game.factory.CharacterFactory;
import mfiari.fireemblem.game.media.son.MusiqueUtil;
import mfiari.fireemblem.game.object.Objet;
import mfiari.fireemblem.game.object.ObjetFactory;
import mfiari.fireemblem.game.object.ObjetType;
import mfiari.fireemblem.game.object.Potion;
import mfiari.fireemblem.game.object.Weapon;
import mfiari.fireemblem.game.organizations.Organization;
import mfiari.fireemblem.game.state.BatonChoiceState;
import mfiari.fireemblem.game.state.DefaultState;
import mfiari.fireemblem.game.state.FightSimulationState;
import mfiari.fireemblem.game.state.FreeState;
import mfiari.fireemblem.game.state.MenuState;
import mfiari.fireemblem.game.state.MoveState;
import mfiari.fireemblem.game.state.ObjectActionState;
import mfiari.fireemblem.game.state.ObjectMenuState;
import mfiari.fireemblem.game.state.OrdreState;
import mfiari.fireemblem.game.state.OtherPhaseState;
import mfiari.fireemblem.game.state.StatusState;
import mfiari.fireemblem.game.state.UnitsState;
import mfiari.fireemblem.game.state.ViewMoveState;
import mfiari.fireemblem.game.state.WeaponChoiceState;
import mfiari.fireemblem.game.strategy.AttackNearestStrategy;
import mfiari.fireemblem.game.terrain.Case;
import mfiari.fireemblem.game.terrain.CasePlaine;
import mfiari.fireemblem.game.terrain.GamePlatform;
import java.util.ArrayList;
import java.util.List;
import mfiari.lib.game.controlleur.ControlleurVue;
import mfiari.lib.game.media.Musique;
import mfiari.lib.game.position.Position;

/**
 *
 * @author mike
 */
public class Chapter extends ControlleurVue {

    private final GamePlatform plateauDeJeu;
    private final String nom;
    private final String objectif;
    private List<Case> zonesSelectionner;
    private List<Case> zonesAtkSelectionner;
    private Character persoEnCours;
    private Character persoAttaquer;
    private Position currentPosition;
    private Position oldPosition;
    private Tour tour;
    private boolean continuer;
    private Combat combat;
    private int nbTour;
    private DefaultState state;
    private boolean renfortAppeler;
    private final Organization organization;
    private boolean fin;
    private Musique musique;
    private MusiqueUtil musiqueUtil;
    private List<EvenementRecrutement> evenementRecrutement;

    public final String AFFICHE_ACTION_PERSO = "afficheActionPerso";
    public final String AFFICHE_ARMES_PERSO = "afficheArmePerso";
    public final String AFFICHE_ATTAQUE_DISPONIBLE = "afficheAttaqueDisponible";
    public final String AFFICHE_BATON_PERSO = "afficheBatonPerso";
    public final String AFFICHE_DEPLACEMENT_DISPONIBLE = "afficheDeplacementDisponible";
    public final String AFFICHE_MAP = "afficheMap";
    public final String AFFICHE_MENU = "afficheMenu";
    public final String CANCEL_ACTION_PERSO = "cancelActionPerso";
    public final String CANCEL_OBJET_ACTION = "cancelObjetAction";
    public final String CHANGE_TOUR = "changeTour";
    public final String DEPLACE_PERSO = "deplacePerso";
    public final String EFFACE_ATTAQUE_DISPONIBLE = "effaceAttaqueDisponible";
    public final String EFFACE_DEPLACEMENT_DISPONIBLE = "effaceDeplacementDisponible";
    public final String ENLEVE_PERSO = "enlevePerso";
    public final String FOCUS_POSITION = "focusOnPosition";
    public final String FREE_STATE = "freeState";
    public final String GAGNE_OBJET = "gagneObjet";
    public final String GAME_OVER = "gameOver";
    public final String HIDE_CHARACTER_VIEW = "hideCaractereView";
    public final String HIDE_MENU = "hideMenu";
    public final String HIDE_ORDRE = "hideOrdre";
    public final String HIDE_STATUS = "hideStatus";
    public final String HIDE_UNITS = "hideUnits";
    public final String INIT = "init";
    public final String OBJETS = "objets";
    public final String OBJETS_ACTION = "objetsAction";
    public final String ORDRES = "ordres";
    public final String PARTIE_SUSPENDU = "partieSuspendu";
    public final String QUITTER = "quitter";
    public final String REFRESH_AT_POSITION = "refreshAtPosition";
    public final String SHOW_CHARACTER_VIEW = "showCaractereView";
    public final String SIMULATION_COMBAT = "simulationCombat";
    public final String STATUS = "status";
    public final String SUSPENDRE = "suspendre";
    public final String UNITES = "unites";
    public final String USE_OBJECT = "useObject";
    public final String VICTOIRE = "victoire";

    public enum menu {

        unite, statut, renfort, ordre, suspen, fin, quitter;
    }

    public enum actionPerso {

        parler, porte, attaquer, baton, objet, echange, attendre;
    }

    public enum Tour {

        perso, ennemie, annexes;
    }

    public enum Ordre {

        rien, immobile, plusProche, portee;
    }

    public enum ObjectAction {

        utiliser, equiper, jeter;
    }
        
    public enum actionCombat {
        run, soin;
    }

    public Chapter(String nom, GamePlatform plateauDeJeu, Organization organization, String objectif, List<EvenementRecrutement> evenementRecrutement) {
        super(false);
        this.nom = nom;
        this.plateauDeJeu = plateauDeJeu;
        this.objectif = objectif;
        this.nbTour = 1;
        this.organization = organization;
        this.evenementRecrutement = evenementRecrutement;
        this.renfortAppeler = false;
        this.state = new FreeState(this);
        this.pcsControlleurVue.firePropertyChange(INIT, null, null);
        this.musiqueUtil = new MusiqueUtil();
    }

    public String getNom() {
        return nom;
    }

    public String getObjectif() {
        return objectif;
    }

    public GamePlatform getPlateauDeJeu() {
        return plateauDeJeu;
    }

    public Character getPersoAttaquer() {
        return this.persoAttaquer;
    }

    public void setPersoAttaquer(Character characterAbstract) {
        this.persoAttaquer = characterAbstract;
    }

    public int getTour() {
        return this.nbTour;
    }

    public void setTour(int tour) {
        this.nbTour = tour;
    }

    public void setCurrentPosition(Position position) {
        this.currentPosition = position;
    }

    public Character getPersoEnCours() {
        return this.persoEnCours;
    }

    public void setPersoEnCours(Character characterAbstract) {
        this.persoEnCours = characterAbstract;
    }

    public Position getCurrentPosition() {
        return this.currentPosition;
    }

    public Position getOldPosition() {
        return this.oldPosition;
    }

    public void setState(DefaultState state) {
        this.state = state;
    }

    public List<Case> getZonesSelectionner() {
        return this.zonesSelectionner;
    }

    public List<Case> getZonesAtkSelectionner() {
        return this.zonesAtkSelectionner;
    }
    
    public boolean hasEvenement () {
        System.out.println("hasEvenement");
        for (EvenementRecrutement recrutement : this.evenementRecrutement) {
            System.out.println(recrutement.toString());
            if (recrutement.estActiver(this)) {
                return true;
            }
        }
        return false;
    }

    public void run() {
        this.musique = this.musiqueUtil.getMusique(MusiqueUtil.MAP);
        this.musique.start(true);
        this.pcsControlleurVue.firePropertyChange(AFFICHE_MAP, null, null);
        this.tour = Tour.perso;
        this.continuer = true;
        while (!this.fin) {
            if (this.continuer) {
                this.continuer = false;
                this.continuer();
            }
            this.attendre(100);
        }
    }

    public void victoire() {
        this.pcsControlleurVue.firePropertyChange(VICTOIRE, null, null);
        this.pcsControlleurVue.firePropertyChange(QUITTER, null, null);
        this.fin = true;
    }

    public void defaite() {
        this.pcsControlleurVue.firePropertyChange(GAME_OVER, null, null);
        this.pcsControlleurVue.firePropertyChange(QUITTER, null, null);
        this.fin = true;
    }

    public void doContinue() {
        this.continuer = true;
    }

    public void continuer() {
        switch (this.tour) {
            case perso:
                boolean hasPersoNormal = false;
                for (Character perso : this.plateauDeJeu.getPersonnages()) {
                    if (perso.getEtat() == Character.Etat.normal) {
                        hasPersoNormal = true;
                        break;
                    }
                }
                if (!hasPersoNormal) {
                    this.tour = Tour.ennemie;
                    this.state = new OtherPhaseState(this);
                    for (Character perso : this.plateauDeJeu.getPersonnages()) {
                        perso.setEtat(Character.Etat.normal);
                        this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, perso, null);
                    }
                    this.pcsControlleurVue.firePropertyChange(CHANGE_TOUR, this.tour, null);
                    this.musique.stop();
                    this.musique = this.musiqueUtil.getMusique(MusiqueUtil.ENEMY_PHASE);
                    this.musique.start(true);
                    for (int i = 0; i < this.plateauDeJeu.getEnnemies().size(); i++) {
                        Character p = this.plateauDeJeu.getEnnemies().get(i);
                        for (Case c : this.plateauDeJeu.getZones()) {
                            if (c.getPosition().equalsXY(p.getPosition())) {
                                Character tmp = new Character(p);
                                c.effect(p);
                                if (tmp.getPv() < p.getPv()) {
                                    this.pcsControlleurVue.firePropertyChange(USE_OBJECT, tmp, p);
                                }
                                break;
                            }
                        }
                    }
                }
                break;
            case ennemie:
                for (int i = 0; i < this.plateauDeJeu.getEnnemies().size(); i++) {
                    Character p = (Character) this.plateauDeJeu.getEnnemies().get(i);
                    this.persoEnCours = p;
                    pcsControlleurVue.firePropertyChange(FOCUS_POSITION, p.getPosition(), null);
                    p.getStrategie().run(this);
                    if (p.estKo()) {
                        pcsControlleurVue.firePropertyChange(ENLEVE_PERSO, p, null);
                        this.plateauDeJeu.getEnnemies().remove(p);
                        i--;
                    } else {
                        p.setEtat(Character.Etat.attendre);
                        this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, p, null);
                    }
                    if (this.fin) {
                        break;
                    }
                    this.attendre(1000);
                }
                if (!this.fin) {
                    this.tour = Tour.annexes;
                    for (Character perso : this.plateauDeJeu.getEnnemies()) {
                        perso.setEtat(Character.Etat.normal);
                        this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, perso, null);
                    }
                    this.pcsControlleurVue.firePropertyChange(CHANGE_TOUR, this.tour, null);
                    this.musique.stop();
                    this.musique = this.musiqueUtil.getMusique(MusiqueUtil.MAP);
                    this.musique.start(true);
                    for (int i = 0; i < this.plateauDeJeu.getAnnexes().size(); i++) {
                        Character p = this.plateauDeJeu.getAnnexes().get(i);
                        for (Case c : this.plateauDeJeu.getZones()) {
                            if (c.getPosition().equalsXY(p.getPosition())) {
                                Character tmp = new Character(p);
                                c.effect(p);
                                if (tmp.getPv() < p.getPv()) {
                                    this.pcsControlleurVue.firePropertyChange(USE_OBJECT, tmp, p);
                                }
                                break;
                            }
                        }
                    }
                }
                break;
            case annexes:
                for (int i = 0; i < this.plateauDeJeu.getAnnexes().size(); i++) {
                    Character p = this.plateauDeJeu.getAnnexes().get(i);
                    this.persoEnCours = p;
                    pcsControlleurVue.firePropertyChange(FOCUS_POSITION, p.getPosition(), null);
                    p.getStrategie().run(this);
                    if (p.estKo()) {
                        pcsControlleurVue.firePropertyChange(ENLEVE_PERSO, p, null);
                        this.plateauDeJeu.getAnnexes().remove(p);
                        i--;
                    } else {
                        p.setEtat(Character.Etat.attendre);
                        this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, p, null);
                    }
                    if (this.fin) {
                        break;
                    }
                    this.attendre(1000);
                }
                if (!this.fin) {
                    for (Character perso : this.plateauDeJeu.getAnnexes()) {
                        perso.setEtat(Character.Etat.normal);
                        this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, perso, null);
                    }
                    this.tour = Tour.perso;
                    this.pcsControlleurVue.firePropertyChange(CHANGE_TOUR, this.tour, null);
                    this.musique.stop();
                    this.musique = this.musiqueUtil.getMusique(MusiqueUtil.MAP);
                    this.musique.start(true);
                    this.renfortAppeler = false;
                    this.state = new FreeState(this);
                    this.nbTour++;
                    for (int i = 0; i < this.plateauDeJeu.getPersonnages().size(); i++) {
                        Character p = this.plateauDeJeu.getPersonnages().get(i);
                        for (Case c : this.plateauDeJeu.getZones()) {
                            if (c.getPosition().equalsXY(p.getPosition())) {
                                Character tmp = new Character(p);
                                c.effect(p);
                                if (tmp.getPv() < p.getPv()) {
                                    pcsControlleurVue.firePropertyChange(FOCUS_POSITION, p.getPosition(), null);
                                    this.pcsControlleurVue.firePropertyChange(USE_OBJECT, tmp, p);
                                }
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < this.plateauDeJeu.getPersonnages().size(); i++) {
                        Character p = this.plateauDeJeu.getPersonnages().get(i);
                        if (p.getStatus() == Character.Status.boss) {
                            pcsControlleurVue.firePropertyChange(FOCUS_POSITION, p.getPosition(), null);
                            break;
                        }
                    }
                }
                break;
        }
    }

    public void action() {
        this.state.action();
    }

    public void annulation() {
        this.state.cancel();
    }

    public void info() {
        this.state.info();
    }

    public void left() {
        this.state.left();
    }

    public void right() {
        this.state.right();
    }

    public void up() {
        this.state.up();
    }

    public void down() {
        this.state.down();
    }

    public void freeStateAction() {
        for (Character perso : this.plateauDeJeu.getPersonnages()) {
            if (perso.getPosition().equalsXY(this.currentPosition) && perso.getEtat() == Character.Etat.normal) {
                List<Case> zones = perso.getMove().getCaseAvailable(this.plateauDeJeu, perso);
                List<Case> zonesAtk = this.getPorteAttaque(perso, zones, this.plateauDeJeu.getZones());
                this.pcsControlleurVue.firePropertyChange(AFFICHE_DEPLACEMENT_DISPONIBLE, zones, null);
                this.pcsControlleurVue.firePropertyChange(AFFICHE_ATTAQUE_DISPONIBLE, zonesAtk, null);
                this.persoEnCours = perso;
                this.zonesSelectionner = zones;
                this.zonesAtkSelectionner = zonesAtk;
                this.state = new MoveState(this);
                return;
            }
        }
        for (Character perso : this.plateauDeJeu.getEnnemies()) {
            if (perso.getPosition().equalsXY(this.currentPosition)) {
                List<Case> zones = perso.getMove().getCaseAvailable(this.plateauDeJeu, perso);
                List<Case> zonesAtk = this.getPorteAttaque(perso, zones, this.plateauDeJeu.getZones());
                this.pcsControlleurVue.firePropertyChange(AFFICHE_DEPLACEMENT_DISPONIBLE, zones, null);
                this.pcsControlleurVue.firePropertyChange(AFFICHE_ATTAQUE_DISPONIBLE, zonesAtk, null);
                this.persoEnCours = perso;
                this.zonesSelectionner = zones;
                this.zonesAtkSelectionner = zonesAtk;
                this.state = new ViewMoveState(this);
                return;
            }
        }
        for (Character perso : this.plateauDeJeu.getAnnexes()) {
            if (perso.getPosition().equalsXY(this.currentPosition)) {
                List<Case> zones = perso.getMove().getCaseAvailable(this.plateauDeJeu, perso);
                List<Case> zonesAtk = this.getPorteAttaque(perso, zones, this.plateauDeJeu.getZones());
                this.pcsControlleurVue.firePropertyChange(AFFICHE_DEPLACEMENT_DISPONIBLE, zones, null);
                this.pcsControlleurVue.firePropertyChange(AFFICHE_ATTAQUE_DISPONIBLE, zonesAtk, null);
                this.persoEnCours = perso;
                this.zonesSelectionner = zones;
                this.zonesAtkSelectionner = zonesAtk;
                this.state = new ViewMoveState(this);
                return;
            }
        }
        this.displayMenu();
    }

    private void displayMenu() {
        menu[] menus;
        if (this.renfortAppeler) {
            menus = new menu[menu.values().length - 1];
            int indice = 0;
            for (menu m : menu.values()) {
                if (m != menu.renfort) {
                    menus[indice] = m;
                    indice++;
                }
            }
        } else if (this.plateauDeJeu.getAnnexes().isEmpty()) {
            menus = new menu[menu.values().length - 1];
            int indice = 0;
            for (menu m : menu.values()) {
                if (m != menu.ordre) {
                    menus[indice] = m;
                    indice++;
                }
            }
        } else {
            menus = menu.values();
        }
        this.pcsControlleurVue.firePropertyChange(AFFICHE_MENU, menus, null);
        this.state = new MenuState(this);
    }

    public void ordre(Ordre ordre) {
        this.organization.setOrder(ordre);
        this.pcsControlleurVue.firePropertyChange(HIDE_ORDRE, null, null);
        this.state = new FreeState(this);
    }

    public void ordreStateCancel() {
        this.displayMenu();
    }

    public void choiceWeapon(int indice) {
        this.persoEnCours.setArme(indice);
        this.simuleCombat();
    }

    public void simuleCombat() {
        List<Character> list = this.getEnnemies();
        if (!list.isEmpty()) {
            Case square = null;
            for (Case zoneAbstract : this.getPlateauDeJeu().getZones()) {
                square = zoneAbstract;
                if (square.getPosition().equalsXY(this.persoEnCours.getPosition())) {
                    break;
                }
            }
            this.combat = new Combat(this.persoEnCours, list.get(0), square, 1, this);
            this.combat.simulerCombat();
            this.pcsControlleurVue.firePropertyChange(SIMULATION_COMBAT, null, null);
            this.state = new FightSimulationState(this);
        }
    }

    public List<Character> getEnnemies() {
        List<Character> list = new ArrayList<>();
        for (Character perso : this.plateauDeJeu.getEnnemies()) {
            if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX() - 1, this.persoEnCours.getPosition().getPositionY()))) {
                list.add(perso);
            } else if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX() + 1, this.persoEnCours.getPosition().getPositionY()))) {
                list.add(perso);
            } else if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX(), this.persoEnCours.getPosition().getPositionY() - 1))) {
                list.add(perso);
            } else if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX(), this.persoEnCours.getPosition().getPositionY() + 1))) {
                list.add(perso);
            }
        }
        return list;
    }

    public List<Character> getAlies() {
        List<Character> list = new ArrayList<>();
        for (Character perso : this.plateauDeJeu.getPersonnages()) {
            if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX() - 1, this.persoEnCours.getPosition().getPositionY()))) {
                list.add(perso);
            } else if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX() + 1, this.persoEnCours.getPosition().getPositionY()))) {
                list.add(perso);
            } else if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX(), this.persoEnCours.getPosition().getPositionY() - 1))) {
                list.add(perso);
            } else if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX(), this.persoEnCours.getPosition().getPositionY() + 1))) {
                list.add(perso);
            }
        }
        for (Character perso : this.plateauDeJeu.getAnnexes()) {
            if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX() - 1, this.persoEnCours.getPosition().getPositionY()))) {
                list.add(perso);
            } else if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX() + 1, this.persoEnCours.getPosition().getPositionY()))) {
                list.add(perso);
            } else if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX(), this.persoEnCours.getPosition().getPositionY() - 1))) {
                list.add(perso);
            } else if (perso.getPosition().equalsXY(new Position(this.persoEnCours.getPosition().getPositionX(), this.persoEnCours.getPosition().getPositionY() + 1))) {
                list.add(perso);
            }
        }
        return list;
    }

    public void fightSimulationStateCancel() {
        this.combat.finSimulation();
        this.pcsControlleurVue.firePropertyChange(AFFICHE_ARMES_PERSO, this.persoEnCours, null);
        this.state = new WeaponChoiceState(this);
    }

    public void combat() {
        List<Character> list = this.getEnnemies();
        if (!list.isEmpty()) {
            this.persoAttaquer = list.get(0);
            this.combat.finSimulation();
            new RunControlleur(combat).start();
            this.persoEnCours.setEtat(Character.Etat.attendre);
            this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, this.persoEnCours, null);
            this.state = new FreeState(this);
            this.pcsControlleurVue.firePropertyChange(FREE_STATE, null, null);
        }
    }

    public void combatSoin() {
        List<Character> list = this.getAlies();
        if (!list.isEmpty()) {
            Case square = null;
            for (Case zoneAbstract : this.getPlateauDeJeu().getZones()) {
                square = zoneAbstract;
                if (square.getPosition().equalsXY(this.persoEnCours.getPosition())) {
                    break;
                }
            }
            this.combat = new Combat(this.persoEnCours, list.get(0), square, 1, this);
            new RunControlleur(combat, actionCombat.soin).start();
        }
    }

    public void deplacePerso(Character perso, Position p) {
        this.oldPosition = new Position(perso.getPosition());
        perso.setPosition(p);
        this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, perso, this.oldPosition);
    }

    public List<Case> getPorteAttaque(Character perso, List<Case> zones, List<Case> allZones) {
        List<Case> zonesAtk = new ArrayList<>();
        if (perso.hasArme()) {
            for (Case zone : zones) {
                for (int i = perso.getArme().getMinRange() ; i <= perso.getArme().getMaxRange() ; i++) {
                    Position p1 = new Position(zone.getPosition().getPositionX() + i, zone.getPosition().getPositionY());
                    this.addZoneAttaque(zones, allZones, zonesAtk, p1);
                    Position p2 = new Position(zone.getPosition().getPositionX() - i, zone.getPosition().getPositionY());
                    this.addZoneAttaque(zones, allZones, zonesAtk, p2);
                    Position p3 = new Position(zone.getPosition().getPositionX(), zone.getPosition().getPositionY() + i);
                    this.addZoneAttaque(zones, allZones, zonesAtk, p3);
                    Position p4 = new Position(zone.getPosition().getPositionX(), zone.getPosition().getPositionY() - i);
                    this.addZoneAttaque(zones, allZones, zonesAtk, p4);
                }
            }
        }
        return zonesAtk;
    }

    public void addZoneAttaque(List<Case> zones, List<Case> allZones, List<Case> zonesAtk, Position p) {
        for (Case ztmp : zonesAtk) {
            if (ztmp.getPosition().equalsXY(p)) {
                return;
            }
        }
        for (Case ztmp : zones) {
            if (ztmp.getPosition().equalsXY(p)) {
                return;
            }
        }
        for (Case ztmp : allZones) {
            if (ztmp.getPosition().equalsXY(p)) {
                zonesAtk.add(ztmp);
                break;
            }
        }
    }

    public void menu(menu m) {
        switch (m) {
            case unite:
                this.state = new UnitsState(this);
                break;
            case statut:
                this.pcsControlleurVue.firePropertyChange(STATUS, null, null);
                this.state = new StatusState(this);
                break;
            case renfort:
                if (!this.renfortAppeler) {
                    CharacterFactory characterFactory = new CharacterFactory();
                    ObjetFactory objetFactory = new ObjetFactory();
                    Character character = characterFactory.createCharacter("chevalier", this.organization, CharacterType.chevalier);
                    character.ajouterObjet(objetFactory.createObjet("lance-fer", ObjetType.hache_fer));
                    character.setPosition(new Position(19, 8));
                    character.setStrategie(new AttackNearestStrategy(character));
                    this.plateauDeJeu.ajouterAnnexe(character);
                    this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, character, null);
                    this.renfortAppeler = true;
                    //this.menuStateCancel();
                    this.pcsControlleurVue.firePropertyChange(HIDE_MENU, null, null);
                    this.state = new FreeState(this);
                }
                break;
            case ordre:
                this.pcsControlleurVue.firePropertyChange(ORDRES, Ordre.values(), null);
                this.state = new OrdreState(this);
                break;
            case suspen:
                this.pcsControlleurVue.firePropertyChange(SUSPENDRE, null, null);
                /*XMLWriter xmlWriter = new XMLWriter();
                xmlWriter.write(this);*/
                this.pcsControlleurVue.firePropertyChange(PARTIE_SUSPENDU, null, null);
                this.fin = true;
                break;
            case fin:
                for (Character perso : this.plateauDeJeu.getPersonnages()) {
                    perso.setEtat(Character.Etat.attendre);
                    this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, perso, null);
                }
                this.pcsControlleurVue.firePropertyChange(HIDE_MENU, null, null);
                this.state = new FreeState(this);
                this.continuer = true;
                break;
            case quitter:
                this.pcsControlleurVue.firePropertyChange(QUITTER, null, null);
                this.fin = true;
                break;
        }
    }

    public void actionPerso(actionPerso action) {
        switch (action) {
            case parler:
                for (EvenementRecrutement recrutement : this.evenementRecrutement) {
                    if (recrutement.estActiver(this)) {
                        recrutement.activeEvenement(this);
                        this.persoEnCours.setEtat(Character.Etat.attendre);
                        this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, this.persoEnCours, null);
                        this.state = new FreeState(this);
                        this.pcsControlleurVue.firePropertyChange(FREE_STATE, null, null);
                        this.continuer = true;
                    }
                }
                break;
            case porte:
                if (this.persoEnCours.hasObjet(ObjetType.cle_porte)) {
                    Position p = new Position(this.persoEnCours.getPosition().getPositionX()-1, this.persoEnCours.getPosition().getPositionY());
                    Case casePlaine = new CasePlaine(p);
                    this.plateauDeJeu.setZoneAtPosition(p, casePlaine);
                    this.pcsControlleurVue.firePropertyChange(REFRESH_AT_POSITION, p, null);
                    this.persoEnCours.setEtat(Character.Etat.attendre);
                    this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, this.persoEnCours, null);
                    this.state = new FreeState(this);
                    this.pcsControlleurVue.firePropertyChange(FREE_STATE, null, null);
                    this.continuer = true;
                } else {
                    this.state.cancel();
                }
                break;
            case attaquer:
                this.pcsControlleurVue.firePropertyChange(AFFICHE_ARMES_PERSO, this.persoEnCours, null);
                this.state = new WeaponChoiceState(this);
                break;
            case baton:
                this.pcsControlleurVue.firePropertyChange(AFFICHE_BATON_PERSO, this.persoEnCours, null);
                this.state = new BatonChoiceState(this);
                break;
            case objet:
                int nbObjet = 0;
                Objet[] objets = this.persoEnCours.getObjets();
                for (Objet objet : objets) {
                    if (objet != null) {
                        nbObjet++;
                    }
                }
                Objet[] objs = new Objet[nbObjet];
                int indice = 0;
                for (Objet objet : objets) {
                    if (objet != null) {
                        objs[indice] = objet;
                        indice++;
                    }
                }
                this.pcsControlleurVue.firePropertyChange(OBJETS, objs, null);
                this.state = new ObjectMenuState(this);
                break;
            case echange:
                break;
            case attendre:
                persoEnCours.setEtat(Character.Etat.attendre);
                this.pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, this.persoEnCours, null);
                this.state = new FreeState(this);
                this.pcsControlleurVue.firePropertyChange(FREE_STATE, null, null);
                this.continuer = true;
                break;
        }
    }

    public void choixObjet(int choix) {
        ObjectAction[] actions = null;
        Objet objet = this.persoEnCours.getObjets()[choix];
        if (objet instanceof Potion) {
            if (this.persoEnCours.getPv() == this.persoEnCours.getPvMax()) {
                actions = new ObjectAction[1];
                actions[0] = ObjectAction.jeter;
            } else {
                actions = new ObjectAction[2];
                actions[0] = ObjectAction.utiliser;
                actions[1] = ObjectAction.jeter;
            }
        } else if (objet instanceof Weapon) {
            if (this.persoEnCours.getFightBehaviour().isWeaponAvailable((Weapon) objet)) {
                actions = new ObjectAction[2];
                actions[0] = ObjectAction.equiper;
                actions[1] = ObjectAction.jeter;
            } else {
                actions = new ObjectAction[1];
                actions[0] = ObjectAction.jeter;
            }
        }
        this.pcsControlleurVue.firePropertyChange(OBJETS_ACTION, actions, choix);
        this.state = new ObjectActionState(this);
    }

    public void utiliserObjet(int choix) {
        Character tmp = new Character(this.persoEnCours);
        Potion potion = (Potion) this.persoEnCours.getObjets()[choix];
        potion.use(this.persoEnCours);
        this.pcsControlleurVue.firePropertyChange(USE_OBJECT, tmp, this.persoEnCours);
        this.actionPerso(actionPerso.attendre);
    }

    public void equiperObjet(int choix) {
        this.persoEnCours.setArme(choix);
        this.pcsControlleurVue.firePropertyChange(CANCEL_OBJET_ACTION, null, null);
        this.actionPerso(actionPerso.objet);
    }

    public void jeterObjet(int choix) {
        Objet[] objets = this.persoEnCours.getObjets();
        for (int i = choix; i < objets.length - 1; i++) {
            objets[i] = objets[i + 1];
        }
        objets[objets.length - 1] = null;
        this.pcsControlleurVue.firePropertyChange(CANCEL_OBJET_ACTION, null, null);
        this.actionPerso(actionPerso.objet);
    }

    public void verifMort() {
        if (this.persoAttaquer.estKo()) {
            this.pcsControlleurVue.firePropertyChange(ENLEVE_PERSO, this.persoAttaquer, null);
            if (this.plateauDeJeu.getAnnexes().contains(this.persoAttaquer)) {
                this.plateauDeJeu.getAnnexes().remove(this.persoAttaquer);
            } else if (this.plateauDeJeu.getEnnemies().contains(this.persoAttaquer)) {
                this.plateauDeJeu.getEnnemies().remove(this.persoAttaquer);
                if (this.persoAttaquer.hasObjetGagne()) {
                    Objet obj = this.persoAttaquer.getObjetGagne();
                    pcsControlleurVue.firePropertyChange(GAGNE_OBJET, obj, null);
                    this.persoEnCours.ajouterObjet(obj);
                }
                if (this.persoAttaquer.getStatus() == Character.Status.boss) {
                    this.victoire();
                }
            } else if (this.plateauDeJeu.getPersonnages().contains(this.persoAttaquer)) {
                this.plateauDeJeu.getPersonnages().remove(this.persoAttaquer);
                if (this.persoAttaquer.getStatus() == Character.Status.boss) {
                    this.defaite();
                }
            }
        }
        if (this.persoEnCours.estKo()) {
            pcsControlleurVue.firePropertyChange(ENLEVE_PERSO, this.persoEnCours, null);
            if (this.plateauDeJeu.getAnnexes().contains(this.persoEnCours)) {
                this.plateauDeJeu.getAnnexes().remove(this.persoEnCours);
            } else if (this.plateauDeJeu.getEnnemies().contains(this.persoEnCours)) {
                this.plateauDeJeu.getEnnemies().remove(this.persoEnCours);
                if (this.persoEnCours.getStatus() == Character.Status.boss) {
                    this.victoire();
                }
            } else if (this.plateauDeJeu.getPersonnages().contains(this.persoEnCours)) {
                this.plateauDeJeu.getPersonnages().remove(this.persoEnCours);
                if (this.persoEnCours.getStatus() == Character.Status.boss) {
                    this.defaite();
                }
            }
        }
    }

    public void fire(String code, Object obj1, Object obj2) {
        this.pcsControlleurVue.firePropertyChange(code, obj1, obj2);
    }

    public class RunControlleur extends Thread {

        private final Combat combat;
        private final actionCombat ac;

        public RunControlleur(Combat combat) {
            this.combat = combat;
            this.ac = actionCombat.run;
        }

        public RunControlleur(Combat combat, actionCombat ac) {
            this.combat = combat;
            this.ac = ac;
        }

        @Override
        public void run() {
            switch (ac) {
                case run:
                    musique.stop();
                    this.combat.run();
                    musique.start(true);
                    persoEnCours.setEtat(Character.Etat.attendre);
                    pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, persoEnCours, null);
                    verifMort();
                    continuer = true;
                    break;
                case soin :
                    musique.stop();
                    this.combat.soin();
                    musique.start(true);
                    persoEnCours.setEtat(Character.Etat.attendre);
                    pcsControlleurVue.firePropertyChange(DEPLACE_PERSO, persoEnCours, null);
                    state = new FreeState(Chapter.this);
                    pcsControlleurVue.firePropertyChange(FREE_STATE, null, null);
                    continuer = true;
                    break;
            }
            
        }

    }

}
