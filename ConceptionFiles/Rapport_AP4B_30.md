---
title: "Rapport - Love Letter - Groupe 30"
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

# La partie Model

## à completer


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

Au lancement du jeu, le dialogue "InitPartieDialog" permet de demander combien il y aura de joueur, puis 'InitNomsDialog" arrive ensuite pour que les joueurs puissent choisir leurs noms.

Une fois l'initialisation faite, la classe va continuellement s'occuper des action des joueurs, en commencant par définir le joueur actif avec la focntion getJoueurActif, puis avec l'appele de la fonction jouerCarte, le controlleur va savoir quelle carte le joueur désire jouer, et va appeler la fonction correspondante dans le modele. 

\newpage

# La partie Vue

La partie vue se décompose en trois sous-parties, la fenetre principale, les panels, et les dialogues.

### la fenetre principale

La classe LoveLetterUI est la fenetre principale de la vue. Elle hérite de JFrame, contient le panneau principale, et possede la méthode refresh() pour mettre à jour l'affiche du jeu.

### Le plateau de jeu

Ce plateau est en faite la table de jeu, il affiche en permanance les carte déjà jouées, la main du joueur actif et un affichage de tout les joueurs. Cette affichage permet de voir le nom des joueurs, la derniere carte qu'ils ont utilisés, et leurs état :
- en jeu (l'état normal)
- protégé (le joueur ne peut pas être attaqué)
- éliminé (le joueur n'est plus en lice)

### Les dialogue


