# AndroidSXP
Test Hello World qui fonctionne correctement sur émulateur. 

IMPORTANT : Si vous utilisez Eclipse ainsi que les dernières versions du SDK Android, une erreur peut survenir dans le projet (au niveau du fichier "res > values > styles.xml" à la ligne "Theme.AppCompat.Light").
Si c'est le cas, cela signifie que le projet doit contenir la librairie "appcompat_v7" dont le projet Android peut être téléchargé ici :

https://github.com/koush/android-support-v7-appcompat

Procédure pour enlever l'erreur :

1) Télécharger la librairie précédente dans le workspace d'Eclipse.

2) Dans Eclipse, File > New > Project, sélectionner le dossier "Android" puis "Android Project from Existing Code".

3) Récupérer le dossier racine de "appcompat_v7" puis valider la boîte de dialogue.

4) Le projet "appcompat_v7" appraît ensuite dans la colonne de gauche.

5) Sélectionner le projet AndroidSXP puis, dans Eclipse, faire "Project > Properties", cliquer sur le menu "Android" de la boîte de dialogue et ensuite cliquer sur le bouton "add" dans la partie "Library".

6) Sélectionner le projet à ajouter (ici appcompat_v7) et cliquer sur "OK".

7) Cliquer enfin sur "Apply" puis "OK".

Les erreurs doivent avoir disparues. 

Notez que le projet "appcompat_v7" doit être ouvert pendant toute la manipulation du projet SXP.

Romain Semler ©2016
