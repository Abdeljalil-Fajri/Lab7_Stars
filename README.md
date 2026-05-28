# StarVault
## Description du Projet
StarVault est une application Android conçue pour gérer et afficher une galerie de célébrités. L'application permet aux utilisateurs de parcourir une liste de stars, de les noter, de rechercher par nom, et de découvrir de nouvelles fonctionnalités interactives avec une interface utilisateur moderne et animée.
## Fonctionnalités Principales
- Affichage en Liste et en Grille : Une liste de célébrités affichée à l'aide d'un RecyclerView. L'utilisateur peut basculer entre une vue en liste et une vue en grille.
- Animations d'Introduction : L'application démarre par un écran de lancement animé (Splash Screen) avant de rediriger vers l'écran principal.
- Interaction Dynamique : Les utilisateurs peuvent cliquer sur une célébrité pour modifier sa note via une boite de dialogue personnalisée.
- Filtrage et Recherche : Une barre de recherche intégrée (SearchView) permet de trouver rapidement une célébrité par son nom.
- Tri : Les utilisateurs peuvent trier la liste par ordre alphabétique ou par note.
- Gestion de Contenu : Un bouton d'action flottant (FAB) permet d'ajouter de nouvelles célébrités, et une action de glissement (swipe-to-delete) permet de les supprimer avec une option d'annulation (Undo) via une Snackbar.
- Partage : Une option dans le menu permet de partager l'application avec d'autres utilisateurs via des applications tierces.
- Chargement d'Images : Les images des célébrités sont affichées dans un format circulaire, chargées efficacement depuis le web grâce à la bibliothèque Glide.
## Architecture
Le projet respecte une architecture avec des couches bien séparées pour garantir un code propre et réutilisable :
- Modèle (Model) : La classe `Celebrity` représente la structure des données.
- Couche d'Accès aux Données (Data) : L'interface générique `DataAccessLayer` définit les opérations CRUD.
- Logique (Logic) : Le `CelebrityRepository` implémente les méthodes d'accès aux données en utilisant le modèle Singleton.
- Interface Utilisateur (Activity et Adapter) : `BrowseActivity`, `LaunchActivity`, et `CelebrityCardAdapter` gèrent l'affichage et l'interaction utilisateur.
## Installation et Utilisation
1. Ouvrez le projet avec Android Studio.
2. Assurez-vous d'avoir une connexion Internet active pour charger les images des célébrités (permission incluse dans le fichier Manifest).
3. Compilez et lancez l'application sur un émulateur ou un appareil Android physique (API 24 minimum recommandée).
## Technologies Utilisées
- Java pour Android
- RecyclerView, CardView, ConstraintLayout, CoordinatorLayout
- Animations Android (ObjectAnimator, AnimatorSet)
- Glide pour le chargement d'images
- Composants Material Design (Snackbar, FloatingActionButton)




