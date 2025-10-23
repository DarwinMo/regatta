# Livrable n°1

## Identification du projet

- Nom du projet&nbsp;: Jeu de régate

- Équipe&nbsp;: Mohamed Abdessalame, Abraão Albuquerque, Eymen Bouhlel, Benoît Cheramy, Lucie Dai, Mohamed ElBoukhiari, Abderrahmane Kabar, Joris Moczygeba

## Diagramme des cas d'utilisation




## Diagramme de classes métier


## Maquettes des futurs écrans


## Analyse des risques projet

### Risque techniques :

- Complexité de la modelisation physique (polaire des vitesses, vent, déplacement)
    - Prévention : Prototyper le moteur de simulation tôt, valider les formules avec de tests simples avant l'intégration de l'interface.
- Difficulté d'intégration de JavaFX
    - Prévention : Séparer la logique du jeu et de l'affichage (architecture MVVM, tester chaque partie indépendament)
- Problèmes de compatibilité ou de configuration (Gradle, CI/CD, JDK)
    - Prévention : Figer les version des outils dans build.gradle
- Absence des tests unitaires et de CI fiables
    - Intégrer JUnit dans le cycle de développement et automatiser les tests via Gitlab CI
- Indisponabilité de l'API météo
    - Prévoir mode "offline" avec valeur défine.

### Risques lié au Groupe

- Manque de communication au coordination entre les membres
    - Prévention : réunions régulières, utilisation stricte des branches et merge requests sur GitLab
- Mauvaise répartition des tâches, surcharge d’un membre
    - Prévention : Répartition claire des rôles (PM, Architecte, Devs), suivi de l’avancement par le chef de projet, tableau Kanban
- Non respesct des délais (sessions TP limitées)
    - Planification par jalons, priorisation des fonctionnalités essentielles
- Manque d’expérience sur les outils imposés
    - Apprentissage rapide par tutoriels, partage des connaissances entre membres