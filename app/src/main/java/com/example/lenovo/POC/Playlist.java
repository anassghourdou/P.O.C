package com.example.lenovo.POC;


/*
* Class qui représente les élément de playlist qui vont êtres afficher dans notre Cyclerview
*/
public class Playlist {
    String titre;

    public Playlist(String titre, String auteur, String date_creation, String nbr_albums, String image) {
        this.titre = titre;
        this.image = image;
        this.auteur = auteur;
        this.date_creation = date_creation;
        this.nbr_albums = nbr_albums;
    }

    String image;
    String auteur;

    public String getTitre() {
        return titre;
    }

    public String getImage() {
        return image;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public String getNbr_albums() {
        return nbr_albums;
    }

    String date_creation;
    String nbr_albums;


}
