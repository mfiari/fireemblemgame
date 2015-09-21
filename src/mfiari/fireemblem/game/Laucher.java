/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game;

import mfiari.fireemblem.game.connexionBD.CreationBase;
import mfiari.fireemblem.game.controler.Demarrage;
import mfiari.fireemblem.game.swing.VueSwing_connexion;
import mfiari.lib.game.connexionBD.ConnexionBD;
import mfiari.lib.game.controlleur.Vues;
import mfiari.lib.game.swing.Ecran;
import mfiari.lib.game.util.Config;

/**
 *
 * @author mike
 */
public class Laucher {
	public static void main(String[] args) {

            Ecran ecran = new Ecran("Fire emblem", null, 700, 650);
            ConnexionBD connexionBD = new ConnexionBD();
            CreationBase creationBase = new CreationBase(connexionBD.getConnexionHSQL(Config.getPropertie("hsql_location"), Config.getPropertie("hsql_user"), Config.getPropertie("hsql_password")));
            Vues.createVue(VueSwing_connexion.class, creationBase);
            creationBase.createDatabase();
            connexionBD.fermerConnexionHSQL();
            Demarrage d = new Demarrage();
            d.jeu();
	}
}
