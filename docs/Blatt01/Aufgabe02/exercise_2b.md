## Aufgabe 2b

### Berechnung vom Score

Um den aktuellen Score zu berechnen, wird der letzte Frame der HashMap genommen, welche alle Frames des Spiels beinhaltet. Daraufhin wird geprüft, ob dieser nicht vollständig oder es der letzte Frame des Spiels (10. Frame) ist. Falls einer der beiden Fälle eintritt, wird der letzte "reguläre"/vollständige Frame referenziert.

Daraufhin wird durch alle vollständigen regulären Frames iteriert.
In jeder Iteration wird:

* Der erste Score des Frames auf den gesamten Score addiert
* Überprüft, ob der Frame ein Strike ist; 
    
    * Falls positiv, wird die Methode getStrikeBonus(frame) aufgerufen
    * Falls negativ, wird der zweite Score des Frames zum gesamten Score addiert 
      * Wenn im Frame ein Spare geworfen wurde, wird die Methode getSpareBonus(frame) aufgerufen und zum gesamten Score addiert

Nach der Iteration durch alle regulären vollständigen Frames wird überprüft, ob der aktuellste Frame vollständig ist und ob dieser der 10. Frame des Spiels ist.

Falls der aktuellste Frame nicht komplett ist und es sich nicht um den 10. Frame handelt, wird der erste Score des Frames aufgerufen und zum gesamten Score addiert, wenn dieser vorhanden ist.
Wenn es der 10. Frame ist, wird überprüft, ob dieser einen ersten und zweiten Score hat. Diese werden dann zum gesamten Score addiert. Wenn im 10. Frame ein Strike oder Spare geworfen wurde und ein dritter Score zusätzlich existiert, wird dieser auch zum gesamten Score addiert.

#### getStrikeBonus() Methode

Um den Bonus für einen Strike zu ermitteln, werden die nächsten beiden Würfe aufgerufen falls vorhanden. Dazu wird zunächst der nächste Frame aufgerufen. Wenn dieser einen ersten Wurf gespeichert hat,
wird dieser dem Bonus hinzugefügt. Falls ein zweiter Wurf in diesem Frame vorhanden ist, wird auch dieser dem Bonus hinzugefügt. Falls nicht, aber ein Wurf im nächsten Frame vorhanden ist,
wird dieser dem Bonus hinzugefügt.

#### getSpareBonus() Methode

Um den Bonus zu ermitteln, wird das nächste Frame aufgerufen um den nächsten Wurf zu finden. Wenn der erste Wurf des Frames vorhanden ist, wird dieser als Bonus addiert.


### Designentscheidungen

#### Würfe werden in Frames gespeichert

Anstatt einen running score zu führen, haben wir uns entschieden, die einzelnen Würfe basierend auf ihrem Wert zum passenden Frame zuzuordnen und in diesem abzuspeichern.
Dazu haben wir die Klasse Frame erstellt, um diese Information zu kapseln.

Diese Entscheidung priorisiert Wartbarkeit und Testbarkeit über Performance und Speicherbedarf. Unser Ansatz benötigt so zwar mehr Speicher um jeden Wurf in jedem Frame zu speichern
und mehr Leistung bei der Berechnung des Scores als ein "running score"-Ansatz, ist jedoch durch ihre Aufgliederung einfacher zu warten und zu testen.
Zudem steigt die Erweiterbarkeit, da einfach neue Uses Cases abgedeckt werden können, die die Informationen in den Frames benötigen (z.B. Ausgabe des gesamten Spiels als String).

#### Verwendung von Test Driven Development

Zunächst haben wir die Struktur und den Ablauf des Spiels Bowling herangezogen, um schrittweise Tests für die grundlegenden Funktionen des Spiels zu entwickeln.
Dabei haben wir mit einem einzelnen Frame angefangen, dann ein ganzes Spiel ohne Strikes/Spares, dann Spiele die Strikes und Spares enthalten oder damit enden.
Nachdem ein Test Case geschrieben wurde, haben wir dann zunächst die entsprechende Funktionalität geschaffen, dass dieser erfolgreich durchläuft
und dann vor dem nächsten Test ein Refactoring gemacht.
Sobald die reguläre Funktionalität bestand haben wir uns (wieder mit dem Red-Green-Refactor Schema) Edge Cases und Verstöße gegen die Spielregeln vorgenommen.
Edge Cases sind z.B. ein perfektes Spiel zu spielen, Strikes und Spares zu werfen oder ein 0-Punkte-Spiel zu spielen.
Verstöße gegen die Spielregeln liegen z.B. vor, wenn in einem regulären Frame zwei mal 6 Pins geworfen werden.