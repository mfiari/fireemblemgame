/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.texte;

import mfiari.lib.game.texte.Texte;
import static mfiari.lib.game.texte.Texte.getInstance;

/**
 *
 * @author mike
 */
public class TexteVueChapter extends Texte {
    
    public static TexteVueChapter getInstance () {
        return (TexteVueChapter) getInstance(TexteVueChapter.class);
    }
    
}