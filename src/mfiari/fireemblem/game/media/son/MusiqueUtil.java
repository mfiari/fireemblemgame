/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.media.son;

import java.io.InputStream;
import mfiari.lib.game.liste.ListeDeMusique;
import mfiari.lib.game.media.Musique;

/**
 *
 * @author mike
 */
public class MusiqueUtil {
    
    public static final String CHANGEMENT_CLASSE = "changement_class";
    public static final String COMBAT = "combat";
    public static final String COMBAT_BOSS = "combat_boss";
    public static final String COMBAT_BOSS2 = "combat_boss2";
    public static final String COMBAT_ENEMY = "combat_enemy";
    public static final String ENEMY_PHASE = "enemy_phase";
    public static final String GAME_OVER = "game_over";
    public static final String LAST_ENEMY = "last_enemy";
    public static final String MAP = "map";
    public static final String MORT = "mort";
    public static final String PREPARATION = "preparation";
    public static final String SOIN = "soin";
    public static final String THEME = "theme";
    
    public Musique getMusique (String nom) {
        InputStream url = getClass().getResourceAsStream("/fireemblem/media/son/"+nom+".wav");
        if (url != null) {
            return new Musique(nom, nom, url);
        }
        return null;
    }
    
    public ListeDeMusique getAll() {
        return null;
    }
    
}
