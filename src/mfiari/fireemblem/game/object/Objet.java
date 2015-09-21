/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mfiari.fireemblem.game.object;

import java.util.Objects;

/**
 *
 * @author mike
 */
public class Objet {
    
    private final String name;
    private final ObjetType typeObjet;
    private int utilisation;
    private final int utilisationMax;
    private final int prix;
    
    public Objet (String nom, int utilisation, int prix, ObjetType typeObjet) {
        this.name = nom;
        this.utilisation = utilisation;
        this.utilisationMax = utilisation;
        this.prix = prix;
        this.typeObjet = typeObjet;
    }

    public String getName() {
        return name;
    }

    public ObjetType getType() {
        return typeObjet;
    }
    
    public void setUtilisation (int utilisation) {
    	this.utilisation = utilisation;
    }

    public int getUtilisation() {
        return utilisation;
    }

    public int getUtilisationMax() {
        return utilisationMax;
    }

    public int getPrix() {
        return prix;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.typeObjet);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Objet other = (Objet) obj;
        return this.typeObjet == other.typeObjet;
    }
}
