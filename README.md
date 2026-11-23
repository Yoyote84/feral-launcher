# Feral Launcher FR

Version française communautaire de **Feral Launcher 2.1.2**.

Cette application permet de modifier les préférences de plusieurs portages Android de Feral Interactive, sans modifier les APK des jeux.

## Jeux pris en charge

- Saints Row: The Third
- Tomb Raider
- Hitman: Absolution
- GRID Legends
- GRID Autosport
- Hitman: Blood Money
- XCOM 2 Collection
- Tropico

## Fonctions

- Réglage de la résolution de rendu et de la résolution dynamique.
- Réglages graphiques détaillés selon le jeu : ombres, textures, reflets, anticrénelage, végétation et effets.
- Limites de fréquence d’images et compteur FPS.
- Préréglages et réglages automatiques selon le GPU.
- Options avancées du moteur, notamment Vulkan et le gestionnaire de fréquence d’images.
- Sauvegarde et restauration des préférences originales.
- Accès aux fichiers du jeu via Shizuku ou root.
- Réglages de performances système nécessitant root.

## Compatibilité

- Android 11 ou version ultérieure.
- Jeu concerné installé et lancé au moins une fois.
- Pour modifier Android/data : accès root (Magisk, KernelSU ou APatch) ou Shizuku démarré et autorisé.

## Compilation

Le projet utilise Android Gradle Plugin 8.5.2 et Java 17.

Commande de compilation : `gradle --no-daemon assembleDebug`.

APK de débogage : `app/build/outputs/apk/debug/app-debug.apk`.

Un workflow GitHub Actions dans `.github/workflows/android.yml` compile automatiquement l’application et publie l’APK comme artefact.

## Installation

L’application française possède un identifiant Android distinct de l’édition originale : `fr.yoyote84.ferallauncher`. Elle peut donc être installée à côté de Feral Launcher original.

L’APK généré par GitHub Actions est signé avec la clé de débogage Android. Pour les mises à jour, conserve la même clé de signature que celle du premier APK français installé.

## Crédits et licence

Feral Launcher original par **Tsifrokayf**. Cette adaptation française conserve la licence MIT du projet d’origine et ses attributions.

Cette application est un projet communautaire non affilié à Feral Interactive. Les marques et noms des jeux restent la propriété de leurs détenteurs respectifs.
