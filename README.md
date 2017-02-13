# README #


## Application qui affiche les playlists d’un utilisateur. ##


## Web service ##
l’API de deezer 

## Eléments utilisées ##
* ViewPager
* TabLayout
* Fragments
* cardview
* Sharedpreferences
* picasso :: Bibliothèque pour affichage des immage :: http://square.github.io/picasso/
* Android Asynchronous Http Client :: Bibliothèque pour l'utilisation des appels HTTP client asynchrones :: http://loopj.com/android-async-http/


   1) La cellule playlist affiche : <br />

*  l'image
*  le titre 
*  le nombre de pistes
*  la date de création 
*  le nom du propriétaire

   2) Si l'application ne trouve pas de connexion, une version de données dans le cache est chargé.<br />
   3) Si le webservice support la pagination, les données sont chargés par partie. <br />



## Tester sur: ##

* Resolution : 400X800 hdpi
* API: 23

![image](https://cloud.githubusercontent.com/assets/25506346/22868877/d33b37c2-f19a-11e6-8796-4a043427273a.png)
