<img src="https://github.githubassets.com/images/icons/emoji/unicode/26a0.png?v8" height="64" alt="Warning"/>
<img src="doc/JavaFX_Logo.png" height="64" alt="JavaFX logo"/>

## Plusieurs versions de JavaFX sont inclues dans ce template !
Ce projet est volumineux et il est important que vous lisiez (avec attention) ce qui suit pour bien comprendre comment tout ça fonctionne et ce que vous pouvez faire pour limiter les inconvénients.

Car en fonction de votre situation particulière, vous allez probablement pouvoir supprimer près de `240MB` d'espace de stockage inutile.

### Pourquoi y a-t-il plusieurs versions de JavaFX ?
Pour être belle et conviviale, cette application a été réalisée à l'aide de la librairie JavaFX de Java et fournit une ihm élaborée qui utilise des boutons, des listes, ...

Or la librairie JavaFX ne fait malheureusement plus partie intégrante du JDK (ceci dès sa version 8, or nous utilisons la version 17 LTS). Dès lors, JavaFX doit être fourni séparément et incorporé aux librairies que le projet utilise afin que celui-ci puisse compiler et fonctionner correctement. 

Or il faut savoir qu'une distribution de JavaFX dépend de l'OS et de l'architecture du processeur. Or de nombreux enseignants et apprentis qui font du développement utilisent des Mac et/ou des PC, et bien souvent les deux. 

Pour vous simplifier la vie et gagner du temps, ce projet contient déjà 3 versions spécifiques de JavaFX 17 : pour **PC/i64**, pour **Mac/i64** et pour **Mac/M1-M2-M3**.

Sachant de plus qu'une distribution de JavaFX pèse pas loin de `80MB`, lisez ce qui suit et vous pourrez supprimer les versions qui ne vous concernent pas et ainsi gagner passablement d'espace disque.

### Comment choisir/changer de distribution de JavaFX ?
Par défaut **ce projet utilise la distribution pour PC/i64**.

VSC est malin et charge automatiquement les librairies du dossier `/lib` (bon, pour être précis il a été configuré ainsi dans le fichier [config.json](/.vscode/settings.json) (mais pas touche hein !)). Donc coder sur un PC puis passer ensuite un moment sur un Mac est chose facile : il suffit de copier la bonne version de JavaFX du dossier `/libfx` dans `/lib` pour fonctionner de manière transparente. Et ça roule !

Par exemple, si l'on souhaite développer sur PC, c'est aussi simple que de copier le dossier `openjfx_17_0_7_windows_x64` du dossier `/libfx` dans `/lib` (et d'y enlever la version précédemment utilisée). C'est tout 8-).

### Quelles distributions de JavaFX sont présentes ?
| Version | Plateforme | Architecture | Dossier à copier dans `/lib` | Commentaires |
| :---- | :---- | :---- | :---- | :---- |
| 17.0.7 | PC | **i64** | `openjfx_17_0_7_windows_x64` | Déjà présent dans `/lib` par défaut |
| 17.0.7 | Mac | **i64** | `openjfx_17_0_7_osx_x64` | |
| 17.0.7 | Mac | **aarch64**<br/>(M1,M2,M3) | `openjfx_17_0_7_osx_aarch64` | |

### Comment libérer de l'espace de stockage inutile ?
Si dans votre projet vous n'avez pas utilité d'avoir plusieurs distributions de JavaFX, ne conservez que la distribution souhaitée dans votre dossier `/lib` et supprimez simplement l'ensemble du dossier `/libfx`. Vous gagnerez environ 3 x ~80MB.


[Revenir à la consigne de l'exercice](/README.md) 