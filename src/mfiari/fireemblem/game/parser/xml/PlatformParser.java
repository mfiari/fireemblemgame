/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mfiari.fireemblem.game.parser.xml;

import mfiari.fireemblem.game.terrain.Access;
import mfiari.fireemblem.game.terrain.Case;
import mfiari.fireemblem.game.terrain.GamePlatform;
import mfiari.fireemblem.game.terrain.Terrain;
import mfiari.fireemblem.game.terrain.TypesCase;
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
public class PlatformParser implements XMLParser {

    private int width;
    private int height;
    private final Terrain terrain;
    private final GamePlatform plateauJeu;
    private Case[][] zoneAbstracts;

    public PlatformParser(Terrain terrain, GamePlatform plateauJeu) {
        this.terrain = terrain;
        this.plateauJeu = plateauJeu;
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
        if ("map".equals(reader.getLocalName())) {
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                if ("width".equals(reader.getAttributeLocalName(i))) {
                    this.width = Integer.valueOf(reader.getAttributeValue(i));
                } else if ("height".equals(reader.getAttributeLocalName(i))) {
                    this.height = Integer.valueOf(reader.getAttributeValue(i));
                }
            }
            this.zoneAbstracts = new Case[this.width][this.height];
            this.plateauJeu.setWidth(width);
            this.plateauJeu.setHeight(height);
        } else if ("case".equals(reader.getLocalName())) {
            String typeName = null;
            int x = 0;
            int y = 0;
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                if ("type".equals(reader.getAttributeLocalName(i))) {
                    typeName = reader.getAttributeValue(i);
                } else if ("x".equals(reader.getAttributeLocalName(i))) {
                    x = Integer.valueOf(reader.getAttributeValue(i));
                } else if ("y".equals(reader.getAttributeLocalName(i))) {
                    y = Integer.valueOf(reader.getAttributeValue(i));
                }
            }
            this.zoneAbstracts[x][y] = this.terrain.creerZone(TypesCase.valueOf(typeName), new Position(x, y));
        }
    }

    private void endElement(XMLStreamReader reader) {
        if ("map".equals(reader.getLocalName())) {
            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    if (this.zoneAbstracts[i][j] == null) {
                        this.plateauJeu.ajouterZone(this.terrain.creerZone(TypesCase.plaine, new Position(i, j)));
                    } else {
                        this.plateauJeu.ajouterZone(this.zoneAbstracts[i][j]);
                    }
                }
            }
            for (Case zone : this.plateauJeu.getZones()) {
                for (Case zone2 : this.plateauJeu.getZones()) {
                    if (zone2.getPosition().equalsXY(new Position(zone.getPosition().getPositionX() + 1, zone.getPosition().getPositionY()))
                            || zone2.getPosition().equalsXY(new Position(zone.getPosition().getPositionX() - 1, zone.getPosition().getPositionY()))
                            || zone2.getPosition().equalsXY(new Position(zone.getPosition().getPositionX(), zone.getPosition().getPositionY() + 1))
                            || zone2.getPosition().equalsXY(new Position(zone.getPosition().getPositionX(), zone.getPosition().getPositionY() - 1))) {
                        Access acces = this.terrain.creerAcces(zone, zone2);
                        this.plateauJeu.ajouterAcces(acces);
                        Access acces2 = this.terrain.creerAcces(zone2, zone);
                        this.plateauJeu.ajouterAcces(acces2);
                    }
                }
            }
        }
    }
}
