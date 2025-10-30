## Aufgabe 3b

### Berechnung vom Score

Um den aktuellen Score zu berechnen, wird der letzte Frame der HashMap genommen, welche alle Frames des Spiels beinhaltet. Daraufhin wird geprüft, ob dieser nicht vollständig oder es der letzte Frame des Spiels (10. Frame) ist. Falls einer der beiden Fälle eintritt, wird der letzte "reguläre"/vollständige Frame referenziert.

Daraufhin wird durch alle vollständigen regulären Frames iteriert.
In jeder Iteration wird:

* Der erste Score des Frames auf den gesamten Score addiert
* Überprüft, ob der Frame ein Strike ist; 
    
    * Falls positiv, wird die Methode getStrikeBonus(frame) aufgerufen
    * Falls negativ, wird der zweite Score des Frames zum gesamten Score addiert 
* Wenn im Frame ein Spare geworfen wurde, wird die Methode getSpareBonus(frame) aufgerufen und zum gesamten Score addiert

Nach der Iteration durch alle regulären vollständigen Frames wird überprüft, ob der der aktuellste Frame vollständig ist und ob dieser der 10. Frame des Spiels ist.

Falls der aktuellste Frame nicht komplett ist und es sich nicht um den 10. Frame handelt, wird der erste Score des Frames aufgerufen, wenn dieser vorhanden ist.
Wenn es der 10. Frame ist, wird überprüft, ob dieser einen ersten und zweiten Score hat. Diese werden dann zum gesamten Score addiert. Wenn im 10. Frame ein Strike oder Spare geworfen wurde und ein dritter Score zusätzlich existiert, wird dieser auch zum gesamten Score addiert.

#### getStrikeBonus() Methode

Um den Bonus zu ermitteln, wird das nächste Frame aufgerufen. Wenn firstScore vorhanden ist, wird dieser als Bonus addiert. Wenn secondScore vorhanden ist, wird dieser auch zusätzlich als Bonus addiert.

#### getSpareBonus() Methode

Um den Bonus zu ermitteln, wird das nächste Frame aufgerufen. Wenn firstScore vorhanden ist, wird dieser als Bonus addiert.


### Designentscheidungen (TODO)

* Frames werden in Map<Integer, Frame> gespeichert
* Verwendung von Test Driven Development