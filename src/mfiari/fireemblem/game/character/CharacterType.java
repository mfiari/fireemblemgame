/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.character;

/**
 *
 * @author mike
 */
public enum CharacterType {
    
    /* Class classique */
    archer_male, archer_female, assassin, bandit, berserker, bretteur, cavalier, cavalier_nomade, cavalier_wyvern, chevalier, chevalier_pegase, chevalier_wyvern, 
    clerc, combattant, corbeau, dragon_feu, druid, faucon, general, guerrier, heros, lord_wyvern, mage, manakete, mercenaire, myrmidon, paladin, pirate, 
    pretre, sage, shaman, sniper, soldat, valkyrie, voleur, 
    
    /* Class personnage */
    archimage, assassin_jaffart, berserker_hawkeye, clerc_mist, corbeau_naesala, dark_druide, dragon_blanc, faucon_tibarn, general_bryce, 
    general_chevalier_noir, general_murdock, general_uther, general_zelgius, heros_greil, king_daein, king_zephiel, lion_caineghis, lion_giffca, 
    lord_elbert, lord_eliwood, lord_equus, lord_happia, lord_hector, lord_lyn, lord_roy, lord_spatha, mage_lilina, mage_nino, mage_soren, manakete_fa, 
    marchand, master_lord, paladin_bertram, paladin_petrine, paladin_titania, pretre_sephiran, princess_crimea, ranger, sage_guinevere, sage_limstella, 
    sage_pent, shaman_bramimond, shaman_sophia, sniper_igrene, valkyrie_mist;
    
    
    public static CharacterType getTypeByName (String name) {
    	switch (name) {
    		case "lord":
    			return lord_eliwood;
    		case "Combattant":
    			return combattant;
    		default :
    			return lord_eliwood;
    	}
    }
    
    
}