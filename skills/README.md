# Skills

Die Skills werden in der Exceldatei `downloads/skillsNasahl.xlsx` gespeichert.

Mithilfe des Skill Generators wird aus den Exceldaten zwei neue Dateien im Targetverzeichnis generiert
- eine neue Version von `index.html`
- ein Snippet` word.txt` für die Anpassung der Datei `Skillmatrix.docx`

## Anpassung der Skills

Folgende Schritte sind durchzuführen:

1. Aktualisierung der Datei `downloads/skillsNasahl.xlsx`.  
Dabei haben die Spalten die folgenden Bedeutungen:
   - Spalte 1: Themenbereich
   - Spalte 2: Skill
   - Spalte 3: Kenntnisstand von 1 bis 5
   - Spalte 4: Bei einer eingetragenen 0, wird der Skill nicht angezeigt
2. Durchführen des Skillgenerators über die IntelliJ RunConfiguration `SkillGenerator`
3. Prüfen der generierten Datei [skills/SkillGenerator/target/index.html](SkillGenerator/target/index.html). 
   Falls alles passt, kann sie in das Mainverzeichnis kopiert werden
4. Anpassen des Dokuments `Skillmatrix.docx`:
   Dabei kann das generierte Snippet [skills/SkillGenerator/target/word.txt](SkillGenerator/target/word.txt) 
   mittels Kopieren ohne Formatierung in das Worddokument kopiert werden.