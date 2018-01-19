package mfiari.fireemblem.game.media.map;

import mfiari.fireemblem.game.terrain.Case;
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
        switch (zone.getType()) {
            case chateau :
                return this.getImage(this.getInputStreamImage("chateau"));
            case forest :
                return this.getImage(this.getInputStreamImage("forest"));
            case fort :
                return this.getImage(this.getInputStreamImage("fort"));
            case montagne :
                return this.getImage(this.getInputStreamImage("montagne"));
            case mur :
                return this.getImage(this.getInputStreamImage("mur"));
            case plaine : 
                return this.getImage(this.getInputStreamImage("plaine"));
            case porte :
                return this.getImage(this.getInputStreamImage("porte"));
        }
        return null;
    }

}
