# Lask
Une application Java de gestion de tâches

## Lancement

### Task Top
Pour lancer l'application Task Top, il est possible d'utiliser la commande suivante sur le jar fourni avec un fichier xml contenant une liste de tâches valide:
```
java -jar TaskTop.jar <fichier xml>
```

### Task Edit
Pour lancer l'application Task Edit, il est nécessaire d'avoir Maven d'installé sur la machine avec la commande suivante :
```
sudo apt install maven
```

Puis d'utiliser la commande suivante :
```
mvn clean javafx:run
```

La commande installera automatiquement toutes les dépendances nécessaires (notamment javafx) puis lancera l'application graphique