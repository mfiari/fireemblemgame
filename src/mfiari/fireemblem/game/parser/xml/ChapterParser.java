/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.parser.xml;

import mfiari.fireemblem.game.character.Character;
import mfiari.fireemblem.game.character.CharacterType;
import mfiari.fireemblem.game.factory.CharacterFactory;
import mfiari.fireemblem.game.object.Objet;
import mfiari.fireemblem.game.object.ObjetFactory;
import mfiari.fireemblem.game.object.ObjetType;
import mfiari.fireemblem.game.strategy.AttackNearestStrategy;
import mfiari.fireemblem.game.strategy.NoMovementStrategy;
import mfiari.fireemblem.game.strategy.RangeStrategy;
import mfiari.fireemblem.game.strategy.Strategy;
import mfiari.fireemblem.game.terrain.GamePlatform;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import mfiari.lib.game.position.Position;

/**
 *
 * @author mike
 */
public class ChapterParser implements XMLParser {

    private final GamePlatform plateauJeu;
    private Character perosEnCours;
    private final CharacterFactory factoryPersonnage;
    private final ObjetFactory factoryObjet;
    private boolean isPerso;

    public ChapterParser(GamePlatform plateauJeu) {
        this.plateauJeu = plateauJeu;
        this.factoryPersonnage = new CharacterFactory();
        this.factoryObjet = new ObjetFactory();
    }

    @Override
    public void parse(XMLStreamReader reader) {
        try {
            while (reader.hasNext()) {
                int eventType = reader.next();
                switch (eventType) {
                    case XMLEvent.END_ELEMENT:
                        this.endElement(reader);
                        break;
                    case XMLEvent.START_ELEMENT:
                        this.startElement(reader);
                        break;
                }
            }
        } catch (XMLStreamException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startElement(XMLStreamReader reader) {
        if ("personnage".equals(reader.getLocalName())) {
            this.perosEnCours = this.createPersonnage(reader);
            this.isPerso = true;
        } else if ("ennemie".equals(reader.getLocalName())) {
            this.perosEnCours = this.createPersonnage(reader);
            this.isPerso = false;
        } else if ("annexe".equals(reader.getLocalName())) {
            this.perosEnCours = this.createPersonnage(reader);
            this.isPerso = false;
        } else if ("position".equals(reader.getLocalName())) {
            int x = 0;
            int y = 0;
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                if ("x".equals(reader.getAttributeLocalName(i))) {
                    x = Integer.valueOf(reader.getAttributeValue(i));
                } else if ("y".equals(reader.getAttributeLocalName(i))) {
                    y = Integer.valueOf(reader.getAttributeValue(i));
                }
            }
            this.perosEnCours.setPosition(new Position(x, y));
        } else if ("stat".equals(reader.getLocalName())) {
            int pv = 0;
            boolean pvRecup = false;
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                int value = Integer.valueOf(reader.getAttributeValue(i));
                if ("niv".equals(reader.getAttributeLocalName(i))) {
                    if (this.isPerso) {
                        this.perosEnCours.setNiv(value);
                    } else {
                        this.perosEnCours.changeNiveau(value);
                    }
                } else if ("pv".equals(reader.getAttributeLocalName(i))) {
                    pv = value;
                    pvRecup = true;
                } else if ("pvgagne".equals(reader.getAttributeLocalName(i))) {
                    this.perosEnCours.setPvGagne(value);
                } else if ("puissancegagne".equals(reader.getAttributeLocalName(i))) {
                    this.perosEnCours.setPuissanceGagne(value);
                } else if ("capacitegagne".equals(reader.getAttributeLocalName(i))) {
                    this.perosEnCours.setCapaciteGagne(value);
                } else if ("vitessegagne".equals(reader.getAttributeLocalName(i))) {
                    this.perosEnCours.setVitesseGagne(value);
                } else if ("chancegagne".equals(reader.getAttributeLocalName(i))) {
                    this.perosEnCours.setChanceGagne(value);
                } else if ("defgagne".equals(reader.getAttributeLocalName(i))) {
                    this.perosEnCours.setDefGagne(value);
                } else if ("resistancegagne".equals(reader.getAttributeLocalName(i))) {
                    this.perosEnCours.setResistanceGagne(value);
                } else if ("experience".equals(reader.getAttributeLocalName(i))) {
                    this.perosEnCours.setExperience(value);
                }
            }
            if (pvRecup) {
                this.perosEnCours.setPv(pv);
            }
        } else if ("objet".equals(reader.getLocalName())) {
            String nom = null;
            String type = null;
            boolean gagne = false;
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                if ("nom".equals(reader.getAttributeLocalName(i))) {
                    nom = reader.getAttributeValue(i);
                } else if ("type".equals(reader.getAttributeLocalName(i))) {
                    type = reader.getAttributeValue(i);
                } else if ("gagne".equals(reader.getAttributeLocalName(i))) {
                    String s = reader.getAttributeValue(i);
                    gagne = s.equals("true");
                }
            }
            Objet obj = factoryObjet.createObjet(nom, ObjetType.valueOf(type));
            this.perosEnCours.ajouterObjet(obj);
            if (gagne) {
                this.perosEnCours.setObjetGagne(obj);
            }
        } else if ("strategie".equals(reader.getLocalName())) {
            String nom = null;
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                if ("nom".equals(reader.getAttributeLocalName(i))) {
                    nom = reader.getAttributeValue(i);
                }
            }
            this.perosEnCours.setStrategie(this.getStrategieByName(nom));
        }
    }

    private void endElement(XMLStreamReader reader) {
        if ("personnage".equals(reader.getLocalName())) {
            this.plateauJeu.ajouterPersonnage(this.perosEnCours);
        } else if ("ennemie".equals(reader.getLocalName())) {
            this.plateauJeu.ajouterEnnemie(this.perosEnCours);
        } else if ("annexe".equals(reader.getLocalName())) {
            this.plateauJeu.ajouterAnnexe(this.perosEnCours);
        }
    }

    private Character createPersonnage(XMLStreamReader reader) {
        String nom = null;
        String type = null;
        Character.Status status = null;
        Character.Etat etat = Character.Etat.normal;
        for (int i = 0; i < reader.getAttributeCount(); i++) {
            if ("nom".equals(reader.getAttributeLocalName(i))) {
                nom = reader.getAttributeValue(i);
            } else if ("type".equals(reader.getAttributeLocalName(i))) {
                type = reader.getAttributeValue(i);
            } else if ("status".equals(reader.getAttributeLocalName(i))) {
                status = Character.Status.valueOf(reader.getAttributeValue(i));
            } else if ("etat".equals(reader.getAttributeLocalName(i))) {
                etat = Character.Etat.valueOf(reader.getAttributeValue(i));
            }
        }
        Character perso = factoryPersonnage.createCharacter(nom, null, CharacterType.valueOf(type));
        if (status != null) {
            perso.setStatus(status);
            perso.setEtat(etat);
        }
        return perso;
    }

    private Strategy getStrategieByName(String nom) {
        switch (nom) {
            case "immobile":
                return new NoMovementStrategy(this.perosEnCours);
            case "plusProche":
                return new AttackNearestStrategy(this.perosEnCours);
            case "portee":
                return new RangeStrategy(this.perosEnCours);
            default:
                return null;
        }
    }
}