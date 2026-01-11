---
title: "Rapport Final - Love Letter UTBM - Groupe 30"
author: 
  - "Suhayb Saleh"
  - "Bastien Laurent"
date: "Janvier 2026"
lang: fr-FR
geometry: margin=2cm
papersize: a4
fontsize: 12pt
documentclass: article
toc: true
toc-title: "Table des matières"
toc-depth: 3
numbersections: true
colorlinks: true
linkcolor: blue
---

\newpage 

# Introduction

L'objetif de ce projet était de recréer le jeu "Love Letter" en java tout en modifiant le contexte du jeu pour l'adapter à l'univers de l'UTBM. Nous avons donc choisit de baser le jeu sur la demande de semestre d'étude à l'étrnager.

# Architecture

Nous avons pour ce projet mis en place une architecture MVC pour ses avantage de lisibilité et de mainteance, ainsi que pour une repartion plus efficace du travaille.

## Contexte narratif

Notre version transpose Love Letter dans l'univers de l'UTBM. Les joueurs incarnent des étudiants tentant de valider leur dossier de Semestre d'Études à l'Étranger (SEE).

## Correspondance des cartes

Le tableau suivant présente la correspondance entre les cartes du jeu original et leur adaptation à l'univers UTBM :


| Valeur | Nom initial  | Adaptation UTBM                                    | Rôle dans le lore                                          |
|--------|--------------|----------------------------------------------------|------------------------------------------------------------|
| 9      | Princesse    | Gestionnaire SEE                                   | L'interlocuteur ultime qui valide ou refuse le dossier     |
| 8      | Comtesse     | LA (Learning Agreement)                           | Le contrat pédagogique qui définit votre programme d'études à l'étranger |
| 7      | Roi          | Directeur                                          | L'autorité suprême de l'établissement                      |
| 6      | Chancelier   | L'Ancien                                          | Un élève ayant déjà effectué un SEE dans cette destination |
| 5      | Prince       | Bug informatique                                   | Un imprévu technique qui peut tout bouleverser             |
| 4      | Servante     | Règlement des Études                              | Le cadre administratif à respecter impérativement          |
| 3      | Baron        | Le jury                                           | L'instance qui compare et évalue les candidatures          |
| 2      | Prêtre       | Tuteur pédagogique                                | Le conseiller qui vous guide dans vos démarches            |
| 1      | Garde        | Examen                                            | L'épreuve à surmonter pour avancer dans le processus       |
| 0      | Espionne     | B2 anglais                                        | Le prérequis linguistique indispensable et discret         |


\newpage

# Diagramme des cas d'utilisation


![](DiagrammeCasUtilisation_LoveLetter.png)

\newpage

# Diagramme de classes 

![Diagramme de classes](DiagrammeClasses.png)

\newpage

# Machines à états-transitions

## Vue d'ensemble : Partie complète

![Déroulement global d'une partie](DiagrammeET_PartieLoveLetter.png)

Enchaînement : Initialisation → HubPartie (boucle de manches) → FinPartie.

\newpage

## Initialisation de la partie 

![Initialisation](DiagrammeET_InitialisationPartie.png)

Configuration des joueurs (2-6) et calcul des pions requis pour la victoire.

\newpage

## Déroulement de la partie

### Hub central de la partie

![Hub de partie](DiagrammeET_HubPartie.png)

Affichage des scores et vérification de victoire. Lance une nouvelle manche ou termine la partie.

\newpage

### Initialisation d'une manche

![Initialisation de manche](DiagrammeET_InitialiserManche.png)

Création et mélange du deck, mise de côté d'une carte, distribution et détermination du premier joueur.

\newpage

### Déroulement d'une manche

![Déroulement de manche](DiagrammeET_DéroulementManche.png)

Tour de jeu : vérifications initiales → pioche → choix de carte (avec contrainte LA si nécessaire) → application d'effet → vérification fin de manche.

**Note** : La sous-machine ApplicationEffet n'a pas été finalisée.

\newpage

### Fin de manche

![Fin de manche](DiagrammeET_FinManche.png)

Détermination du vainqueur (dernier en jeu ou carte la plus forte), attribution des pions et bonus B2 si applicable.

\newpage

## Fin de partie

![Fin de partie](DiagrammeET_FinPartie.png)

Annonce du vainqueur et affichage des statistiques finales.


\newpage

# La partie Controle

la partie controle n'est composé que d'une seul classe : "GameController".
Véritable chef d'orchestre du projet, cette classe est la boucle de jeu du projet, elle permet de : 
- démarrer la partie
- lancer les différentes manches du jeu 
- définir le joueur actif 
- interpreter les actions des joueurs
- appeler les méthodes du modele 
- savoir quand changer d'écran
- gerer la boucle de jeu

Au lancement du jeu, le dialogue "InitPartieDialog" permet de demander combien il y aura de joueur, puis 'InitNomsDialog" arrive ensuite pour que les joueurs puissent choisir leurs noms.

Une fois l'initialisation faite, la classe va continuellement s'occuper des action des joueurs, en commencant par définir le joueur actif avec la focntion getJoueurActif, puis avec l'appele de la fonction jouerCarte, le controlleur va savoir quelle carte le joueur désire jouer, et va appeler la fonction correspondante dans le modele. 

\newpage
# La partie Modèle

La couche Modèle constitue le cœur logique du projet. Elle est totalement indépendante de la vue (l'interface graphique) et contient toutes les données, les règles métier et la mécanique interne du jeu. Elle a été conçue pour être modulaire et robuste.

## Architecture Générale

Le modèle s'articule autour de deux classes maîtresses qui gèrent le cycle de vie du jeu :

### 1. La Classe `Partie` (Singleton)

Point d'entrée principal du modèle, cette classe est implémentée selon le **pattern Singleton**. Cela garantit qu'il n'existe qu'une seule instance du gestionnaire de jeu à tout moment.

* **Rôle :** Elle orchestre le cycle de vie global (lancement du jeu, transition entre les manches, persistance des joueurs).
* **Accès :** Elle fournit un accès global à l'état du jeu via `Partie.getInstance()`.

### 2. La Classe `Manche`

Si `Partie` gère la guerre, `Manche` gère la bataille. Une nouvelle instance de `Manche` est créée à chaque round.

* **Responsabilités :** Elle contrôle la boucle de jeu séquentielle : qui est le joueur actif (`getJoueurActuel()`), la gestion de la pioche (`Deck`), et la vérification des conditions de fin de manche (pioche vide ou un seul survivant).
* **Logique de tour :** C'est elle qui valide et exécute les actions via la méthode `jouerTour(ActionJoueur action)`.

## La Gestion des Actions : `ActionJoueur`

La classe `ActionJoueur` est un élément central de notre architecture. Elle agit comme un objet de transfert de données qui encapsule l'intégralité de l'intention de jeu d'un utilisateur.

Plutôt que de passer de multiples paramètres disparates à travers les méthodes (qui joue, quelle carte, contre qui, quelle devinette...), on regroupe le tout dans cette structure :

* **`joueur`** : L'entité qui initie l'action.
* **`carteJouee`** : La carte sélectionnée depuis la main.
* **`cible`** : Le joueur visé par l'effet. Cet attribut peut être nul si la carte ne cible personne.
* **`carteDevinee`** : Un attribut spécifique requis uniquement par la carte `Examen` (Garde), permettant de stocker le `TypeCarte` que le joueur tente de deviner.

**Avantage architectural :** Cette encapsulation permet de découpler le Contrôleur du Modèle. Le Contrôleur "remplit" cet objet en fonction des clics de l'utilisateur, et le Modèle "consomme" cet objet pour appliquer les règles sans se soucier de l'origine de l'action.

## Le Système de Cartes (Factory et Polymorphisme)

Le jeu repose sur un système extensible de cartes géré par plusieurs classes :

* **`Carte` (Abstraite) :** Définit le contrat commun (`valeur`, `nom`, `type`) et force l'implémentation de la méthode `appliquerEffet(Manche m, ActionJoueur a)`. Chaque carte concrète (`B2Anglais`, `Exam`, `Tuteur`, etc.) contient sa propre logique métier encapsulée.
* **`CarteFactory` :** on utilisons le **pattern Factory** pour la création des cartes. La méthode statique `creerDeckComplet()` centralise la configuration du paquet (combien d'exemplaires de chaque carte), ce qui facilite l'équilibrage du jeu sans modifier les classes de cartes elles-mêmes.
* **`TypeCarte` (Enum) :** Une énumération qui liste toutes les cartes possibles, utilisée notamment pour la mécanique de devinette de la carte *Examen*.

## Les Entités de Données

Enfin, des classes entités stockent l'état des participants :

* **`Joueur` :** Maintient l'état individuel (en vie, protégé, éliminé) et le score (`pointsFaveur`).
* **`MainJoueur` :** Une classe dédiée à la gestion de la main du joueur. Elle implémente notamment la règle critique du "Learning Agreement" (Comtesse) via `doitJouerLA()`, forçant le joueur à se défausser de cette carte s'il possède également le *Directeur* (Roi) ou le *Bug Informatique* (Prince).

\newpage

# La partie Vue

La partie vue se décompose en trois sous-parties, la fenetre principale, les panels, et les dialogues.

## la fenetre principale

La classe LoveLetterUI est la fenetre principale de la vue. Elle hérite de JFrame, contient le panneau principale, et possede la méthode refresh() pour mettre à jour l'affiche du jeu.

## Le plateau de jeu

Ce plateau est en faite la table de jeu, il affiche en permanance les carte déjà jouées, la main du joueur actif et un affichage de tout les joueurs. Cette affichage permet de voir le nom des joueurs, la derniere carte qu'ils ont utilisés, et leurs état :
- en jeu (l'état normal)
- protégé (le joueur ne peut pas être attaqué)
- éliminé (le joueur n'est plus en lice)

## Les dialogues

Les dialogues sont gérés via des fenêtres modales (`JDialog`) qui interrompent le flux principal pour demander une interaction spécifique au joueur ou afficher une information critique. Ils sont essentiels pour la gestion des effets de cartes qui nécessitent une entrée utilisateur :

* **Initialisation :** Comme évoqué dans la partie Contrôle, `InitPartieDialog` et `InitNomsDialog` configurent la partie.
* **Actions de jeu :** Lorsqu'une carte nécessite de cibler un joueur ou de deviner une carte, une boîte de dialogue spécifique s'ouvre. Elle permet de sélectionner un joueur cible parmi une liste déroulante et, si nécessaire, de choisir un nom de carte.
* **Fin de manche/partie :** Un dialogue récapitulatif affiche le vainqueur de la manche ou de la partie, permettant aux joueurs de voir les scores finaux avant de recommencer ou de quitter.

\newpage

# Conclusion

Ce projet nous a permis de mettre en application les concepts de la programmation orientée objet en Java à travers la réalisation d'un jeu de société complet. L'adaptation du thème "Love Letter" à l'univers de l'UTBM (la demande de semestre à l'étranger) a ajouté une dimension ludique et narrative à notre travail.

L'adoption de l'architecture MVC (Model-View-Control) a été déterminante pour la réussite du projet. Elle nous a permis de :

1. Séparer clairement la logique du jeu (les règles des cartes) de l'interface graphique (Swing).
2. Travailler efficacement en groupe en répartissant les tâches (un membre sur l'interface, un autre sur la logique métier).
3. Faciliter le débogage, le Contrôleur agissant comme un point central pour surveiller le flux d'exécution.

Si le jeu est fonctionnel, certaines pistes d'amélioration restent envisageables, comme la mise en place d'un mode réseau pour jouer sur plusieurs machines. Ce projet reste une expérience formatrice solide sur la gestion d'états et les interfaces graphiques en Java.
