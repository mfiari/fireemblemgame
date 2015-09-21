package mfiari.fireemblem.game.media.map;

import mfiari.fireemblem.game.terrain.Case;
import mfiari.fireemblem.game.terrain.CaseChateau;
import mfiari.fireemblem.game.terrain.CaseFort;
import mfiari.fireemblem.game.terrain.CaseMontagne;
import mfiari.fireemblem.game.terrain.CaseMur;
import mfiari.fireemblem.game.terrain.CasePlaine;
import mfiari.fireemblem.game.terrain.CasePorte;
import java.awt.image.BufferedImage;
import mfiari.lib.game.image.Image;

public class MapImage extends Image {

    private static final MapImage _ImageMap;

    static {
        _ImageMap = new MapImage();
    }

    public static BufferedImage getImageFromZone(Case zone) {
        return _ImageMap.getImage(zone);
    }

    public BufferedImage getImage(Case zone) {

        if (zone instanceof CaseChateau) {
            if (this.aImage("chateau")) {
                return this.getImage(this.getInputStreamImage("chateau"));
            }
        } else if (zone instanceof CaseFort) {
            if (this.aImage("fort")) {
                return this.getImage(this.getInputStreamImage("fort"));
            }
        } else if (zone instanceof CaseMontagne) {
            if (this.aImage("montagne")) {
                return this.getImage(this.getInputStreamImage("montagne"));
            }
        } else if (zone instanceof CaseMur) {
            if (this.aImage("mur")) {
                return this.getImage(this.getInputStreamImage("mur"));
            }
        } else if (zone instanceof CasePlaine) {
            if (this.aImage("plaine")) {
                return this.getImage(this.getInputStreamImage("plaine"));
            }
        } else if (zone instanceof CasePorte) {
            if (this.aImage("porte")) {
                return this.getImage(this.getInputStreamImage("porte"));
            }
        }
        return null;
    }

}
