/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.controler;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mfiari.lib.game.connexionBD.ConnexionBD;
import mfiari.lib.game.controlleur.ControlleurVue;
import mfiari.lib.game.controlleur.Vues;
import mfiari.lib.game.reseau.Client;
import mfiari.lib.game.reseau.Connexion;
import mfiari.lib.game.reseau.EnvoiObjet;
import mfiari.lib.game.reseau.NetworkObjectTranmetter;
import mfiari.lib.game.reseau.ReceveurObjet;
import mfiari.lib.game.reseau.User;
import mfiari.lib.game.util.Config;

/**
 *
 * @author mike
 */
public class NetworkManager extends ControlleurVue {
    
    private Connexion connexion;
    
    public NetworkManager () {
        super(true);
    }
    
    public void start () {
        try {
            Client client = new Client();
            int maxIteration = 30;
            int iteration = 0;
            this.pcsControlleurVue.firePropertyChange("connect", null, null);
            do {
                this.attendre(1000);
                client.lancerClient(Config.getPropertie("server_adresse"), Integer.parseInt(Config.getPropertie("server_port")));
                iteration++;
            } while (!client.estConnecter() && iteration < maxIteration);
            if (iteration == maxIteration) {
                this.pcsControlleurVue.firePropertyChange("networkUnreachable", null, null);
                return;
            }
            Socket socket = client.getSocket();
            ReceveurObjet receveur = new ReceveurObjet(socket);
            EnvoiObjet envoi = new EnvoiObjet(socket);
            NetworkObjectTranmetter objectTranmetter = (NetworkObjectTranmetter)receveur.readObject();
            if ("connexion".equals(objectTranmetter.getMessage())) {
                System.out.println("connexion en cours");
                this.connexion = new Connexion("", "");
                boolean connexionSucces = true;
                do {
                    this.pcsControlleurVue.firePropertyChange("connexion", connexionSucces, this.connexion);
                    System.out.println(this.connexion);
                    envoi.writeObject(new NetworkObjectTranmetter("connexion", this.connexion));
                    objectTranmetter = (NetworkObjectTranmetter)receveur.readObject();
                    if ("connexion".equals(objectTranmetter.getMessage())) {
                        connexionSucces = (boolean) objectTranmetter.getObject();
                        System.out.println("connexionSucces : " + connexionSucces);
                    }
                } while (!connexionSucces);
                System.out.println("connexion réussi");
                this.pcsControlleurVue.firePropertyChange("message", "Connexion success, waiting for the server...", null);
                objectTranmetter = (NetworkObjectTranmetter)receveur.readObject();
                if ("partie".equals(objectTranmetter.getMessage())) {
                    int partie = (int) objectTranmetter.getObject();
                    this.pcsControlleurVue.firePropertyChange("choixMode", null, null);
                    switch (this.choix) {
                        case 1 :
                            envoi.writeObject(new NetworkObjectTranmetter("game", null));
                            objectTranmetter = (NetworkObjectTranmetter)receveur.readObject();
                            if ("nouvellePartie".equals(objectTranmetter.getMessage())) {
                                int indice = 1;
                                do {
                                    this.pcsControlleurVue.firePropertyChange("nouvellePartie", indice, null);
                                    if (this.choix == -1) {
                                        if (indice < 5) {
                                            indice++;
                                        }
                                    } else if (this.choix == -2) {
                                        if (indice > 1) {
                                            indice--;
                                        }
                                    }
                                } while (this.choix <= 0 || this.choix > 5);
                            }
                            break;
                        case 2 :
                            /*envoi.writeObject(new NetworkObjectTranmetter("fight", null));
                            ConnexionBD connexionBD = new ConnexionBD();
                            pokemon.connexionBD.Connexion c = new pokemon.connexionBD.Connexion(connexionBD.getConnexionHSQL(Global.hsqlLocation, Global.hsqlUser, Global.hsqlMdp));
                            c.setPartie(1);
                            Dresseur dresseur1 = c.chargerDresseur();
                            connexionBD.fermerConnexionHSQL();
                            System.out.println("mon dresseur : " + dresseur1.getNom());
                            envoi.writeObject(new NetworkObjectTranmetter("dresseur", dresseur1));
                            this.pcsControlleurVue.firePropertyChange("choixType", null, null);
                            if (this.choix == 1) {
                                this.pcsControlleurVue.firePropertyChange("choixTerrain", null, null);
                                System.out.println("terrain : " + terrain.toString());
                                envoi.writeObject(new NetworkObjectTranmetter("creer", this.terrain));
                                this.pcsControlleurVue.firePropertyChange("creationPartie", null, null);
                                objectTranmetter = (NetworkObjectTranmetter)receveur.readObject();
                                if ("demande".equals(objectTranmetter.getMessage())) {
                                    User user = (User) objectTranmetter.getObject();
                                    System.out.println("demande de l'utilisateur : " + user.getLogin());
                                    envoi.writeObject(new NetworkObjectTranmetter("accepte", user));
                                    this.pcsControlleurVue.firePropertyChange("message", "preparing fight...", null);
                                    objectTranmetter = (NetworkObjectTranmetter)receveur.readObject();
                                    if ("dresseur".equals(objectTranmetter.getMessage())) {
                                        Dresseur dresseur2 = (Dresseur) objectTranmetter.getObject();
                                        System.out.println("dresseur adv : " + dresseur2.getNom());
                                        envoi.writeObject(new NetworkObjectTranmetter("dresseur", dresseur2));
                                        CombatReseau combatReseau = new CombatReseauServer (this.terrain, socket, envoi, receveur);
                                        Vues.createVue(VueSwing_combatReseau.class, combatReseau);
                                        combatReseau.combatSimpleReseau(dresseur1, dresseur2);
                                    }
                                }
                            } else if (this.choix == 2) {
                                envoi.writeObject(new NetworkObjectTranmetter("rejoindre", null));
                                this.pcsControlleurVue.firePropertyChange("message", "getting available user...", null);
                                objectTranmetter = (NetworkObjectTranmetter)receveur.readObject();
                                if ("users".equals(objectTranmetter.getMessage())) {
                                    List<User> clients = (List<User>) objectTranmetter.getObject();
                                    this.pcsControlleurVue.firePropertyChange("availableUsers", clients, null);
                                    if (this.choix > 0 && this.choix <= clients.size()) {
                                        System.out.println("choix : " + clients.get(this.choix-1));
                                        envoi.writeObject(new NetworkObjectTranmetter("user", clients.get(this.choix-1)));
                                        this.pcsControlleurVue.firePropertyChange("message", "waiting for user response...", null);
                                        objectTranmetter = (NetworkObjectTranmetter)receveur.readObject();
                                        if ("dresseur".equals(objectTranmetter.getMessage())) {
                                            Dresseur dresseur2 = (Dresseur) objectTranmetter.getObject();
                                            System.out.println("dresseur adv : " + dresseur2.getNom());
                                            envoi.writeObject(new NetworkObjectTranmetter("dresseur", dresseur2));
                                            this.pcsControlleurVue.firePropertyChange("message", "preparing fight...", null);
                                            objectTranmetter = (NetworkObjectTranmetter)receveur.readObject();
                                            if ("terrain".equals(objectTranmetter.getMessage())) {
                                                Terrain terrain = (Terrain) objectTranmetter.getObject();
                                                System.out.println("terrain : " + terrain.toString());
                                                envoi.writeObject(new NetworkObjectTranmetter("terrain", terrain));
                                                CombatReseau combatReseau = new CombatReseauClient (terrain, socket, envoi, receveur);
                                                Vues.createVue(VueSwing_combatReseau.class, combatReseau);
                                                combatReseau.combatSimpleReseau(dresseur1, dresseur2);
                                            }
                                        }
                                    }
                                }
                            }*/
                            break;
                    }
                }
            } else {
                System.out.println("connexion non reçu");
            }
        } catch (IOException ex) {
            Logger.getLogger(EnvoiObjet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
