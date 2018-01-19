/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.media.image.util;

import java.awt.image.BufferedImage;
import mfiari.lib.game.image.Image;

/**
 *
 * @author mike
 */
public class ImageUtil extends Image {
    
    public BufferedImage getBindingBlade () {
        return this.getImage(ImageUtil.class.getResourceAsStream("/mfiari/fireemblem/game/media/image/util/binding_blade.png"));
    }
    
    public BufferedImage getBlazingSword () {
        return this.getImage(ImageUtil.class.getResourceAsStream("/mfiari/fireemblem/game/media/image/util/blazing_sword.png"));
    }
    
    public BufferedImage getPathOfRadiance () {
        return this.getImage(ImageUtil.class.getResourceAsStream("/mfiari/fireemblem/game/media/image/util/path_of_radiance.jpg"));
    }
    
    public BufferedImage getImageEnLigne () {
        return this.getImage(ImageUtil.class.getResourceAsStream("/mfiari/fireemblem/game/media/image/util/path_of_radiance.jpg"));
    }
    
    public BufferedImage getImageMap () {
        return this.getImage(ImageUtil.class.getResourceAsStream("/mfiari/fireemblem/game/media/image/util/path_of_radiance.jpg"));
    }
    
    public BufferedImage getImageArene () {
        return this.getImage(ImageUtil.class.getResourceAsStream("/mfiari/fireemblem/game/media/image/util/path_of_radiance.jpg"));
    }
    
    public BufferedImage getImageMusique () {
        return this.getImage(ImageUtil.class.getResourceAsStream("/mfiari/fireemblem/game/media/image/util/path_of_radiance.jpg"));
    }
    
    public BufferedImage getImageVideo () {
        return this.getImage(ImageUtil.class.getResourceAsStream("/mfiari/fireemblem/game/media/image/util/path_of_radiance.jpg"));
    }
    
    public BufferedImage getImageConfiguration () {
        return this.getImage(ImageUtil.class.getResourceAsStream("/mfiari/fireemblem/game/media/image/util/path_of_radiance.jpg"));
    }
    
}
