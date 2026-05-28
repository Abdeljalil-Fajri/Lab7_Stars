# StarVault

## Description du projet

StarVault est une application Android développée en Java permettant de gérer et d'afficher une galerie de célébrités. L'utilisateur peut parcourir une liste de stars avec leurs photos et leurs notes, effectuer une recherche par nom, trier la liste, modifier les notes, ajouter de nouvelles célébrités, en supprimer par glissement, et partager l'application via les applications installées sur son appareil.

---

## Structure du projet

Le projet est organisé en packages distincts suivant une architecture en couches claires :

- `model` : contient la classe `Celebrity` représentant la structure des données
- `data` : contient l'interface générique `DataAccessLayer` définissant le contrat CRUD
- `logic` : contient `CelebrityRepository` qui implémente la couche d'accès aux données
- `adapter` : contient `CelebrityCardAdapter` qui gère l'affichage de la liste
- `activity` : contient `LaunchActivity` et `BrowseActivity` qui gèrent les écrans

---

## Fonctionnalités

**Ecran de lancement animé**

L'application démarre sur un écran de lancement avec une animation combinée utilisant `ObjectAnimator` et `AnimatorSet`. Le logo effectue une rotation double tour accompagnée d'un effet d'agrandissement progressif, pendant que le nom de l'application apparait par un glissement vers le haut. L'écran redirige automatiquement vers l'écran principal après 3,5 secondes.

**Affichage de la liste**

Les célébrités sont affichées dans un `RecyclerView` avec un `LinearLayoutManager`. Chaque carte `MaterialCardView` présente une photo circulaire chargée depuis le web, le nom de la célébrité, et sa note sous forme de `RatingBar`.

**Basculement liste et grille**

Un élément du menu permet de basculer entre une vue en liste via `LinearLayoutManager` et une vue en grille à deux colonnes via `GridLayoutManager`.

**Recherche en temps réel**

Une `SearchView` intégrée dans la `MaterialToolbar` permet de filtrer instantanément la liste selon le nom saisi. L'adaptateur implémente l'interface `Filterable` pour gérer le filtrage sans recharger les données.

**Tri de la liste**

Un sous-menu permet de trier les célébrités par ordre alphabétique ou par note décroissante via `Collections.sort()`.

**Modification de la note**

Un clic sur une carte ouvre une boite de dialogue `AlertDialog` contenant une `RatingBar`. La nouvelle note est enregistrée dans le repository et la ligne correspondante est mise à jour via `notifyItemChanged()`.

**Ajout d'une célébrité**

Un bouton d'action flottant `FloatingActionButton` ouvre une boite de dialogue permettant de saisir un nom, une URL de photo et une note. La nouvelle célébrité est ajoutée à la liste immédiatement.

**Suppression par glissement**

L'interface `ItemTouchHelper.SimpleCallback` permet de supprimer une célébrité par glissement gauche ou droit. Une `Snackbar` s'affiche avec une option d'annulation permettant de restaurer l'élément supprimé.

**Partage**

Une option dans le menu utilise `ShareCompat.IntentBuilder` pour partager un message textuel via les applications disponibles sur l'appareil.

**Chargement d'images**

La bibliothèque Glide charge les images depuis des URLs distantes et les affiche dans un `CircleImageView` avec une image de remplacement en cas d'échec du chargement.

---

## Architecture

Le projet suit le patron de conception Singleton dans la couche `CelebrityRepository` pour garantir une instance unique partagée dans toute l'application. L'interface générique `DataAccessLayer<T>` définit les opérations `insert`, `refresh`, `remove`, `getById` et `getAll`, rendant l'architecture extensible à d'autres types d'objets.

L'adaptateur `CelebrityCardAdapter` utilise le patron `ViewHolder` pour optimiser les performances du `RecyclerView`. Il expose une interface de rappel `OnCelebrityClickListener` pour déléguer les événements de clic à l'activité, séparant ainsi la logique d'affichage de la logique métier.

---

## Installation

1. Ouvrir le projet dans Android Studio
2. Vérifier qu'une connexion Internet est disponible pour le chargement des images
3. Lancer l'application sur un émulateur ou un appareil physique avec API 24 minimum

---

## Dépendances

- `androidx.recyclerview:recyclerview:1.3.1`
- `de.hdodenhof:circleimageview:3.1.0`
- `com.github.bumptech.glide:glide:4.15.1`
- `com.google.android.material:material:1.11.0`

---

## Technologies utilisées

- Langage : Java
- Environnement : Android Studio
- SDK minimum : API 24 (Android 7.0)
- Composants principaux : RecyclerView, MaterialCardView, CoordinatorLayout, FloatingActionButton, SearchView, AlertDialog, Snackbar, ItemTouchHelper, ObjectAnimator, AnimatorSet
