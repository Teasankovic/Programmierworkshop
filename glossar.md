## Was ist ein glossar.md
Ein glossar.md ist eine Textdatei die für die Dokumentation eines Projektes dient.
## Variable
Eine Variable ist, wie ein speicher indem werte wie bsp. Zahlen eingetragen werden können. Der wert kann auf zeit verändert werden.
Bsp.: Man spielt ein Spiel und erhält beim lösen von aufgaben Punkte. Wenn sich die Punktzahl erhöht kann der wert der hinter dem "lable" "Punktzahl" steht verändert werden.

## Deklaration
Eine Deklaration ist der schritt indem man dem Computer/Software sagt, dass das was man machen will eine bestimmte Aufgabe hat. In java ist es zB.: int = Ein Zahlen wert ohne kommer, sowie direkt dahinter "alter"
(ohne "). So versteht der Computer die Intention dieses wertes im späteren Prozess.

## Initialisierung
Initialisierung bedeutet das dieses int alter was man bei der Deklaration bestimmt hat einen Wert zugeordnet bekommt. Sprich in Java schreibt man dann: int alter = 25;

## Datentyp
Der Datenwert sagt dem Computer sowie uns was rauskommen soll. Es gibt: String --> Textausgabe; int --> ganze zahlen; boolean --> wahr oder falsch.
Mit diesen Datentypen stellen wir sicher das das rauskommt was wir auch wollen.

## Konstante
Eine Konstante soll wie der nahme schon sagt Konstant bleiben. Sprich der Wert soll sich im laufe nicht mehr ändern. Ein beispiel wäre bei der Zeitrechnung das eine Stunde immer 60 min sind und nicht mehr oder weniger.

## Integer
Integer oder int ist ein Datentyp für ganzzahlige Zahlen. Also -10, 2, 55, ...
Es dürfen keine kommer Zahlen sein.

## Float
Float ist auch ein Datentyp für Zahlen, aber hier können kommerzahlen benutzt werden. Bsp.: 5.25

## Double
Double ist ähnlich wie Float. Zahlen mit Nachkommastellen werden gespeichert, dennoch mit mehr Präzision wenn man Werte genauer braucht. Allerdings verbraucht der Datentyp mehr Speicherplatz.

## Operator
Operator sind die Symbole wie +, -, *, =. Mit denen werden Rechenoperationen ausgeführt

## Zeichenkette (String)
Durch "String" wird der code "Hallo Welt" ausgeführt. Also ein Datentyp der beim programmieren Text ausspuckt.

## Wahrheitswert (Boolean)
Boolean dient für die Ausgabe von Wahr oder Falsch. Es kann beispielweise beim Abfragen von Bedingungen helfen. "Ist der Zahlenwert 2 größer als 6?"

## Standardausgaben (z.B. print)
Print oder println dienen dazu die Eingaben wie String oder int im Programm als Text anzuzeigen.

## Kommentar
Kommentare werden im Programm ignoriert. Man kann kurze Kommentare mit // schreiben die über dem codierten stehen. Länmgere Kommentare werden mit /* */ geschrieben.
Sie werden verwendet um den Code und die Vorgehensweise zu erklären oder für sich um den Hintergrund zu erläutern.

## Kompilieren vs. Interpretieren
Ein Kompilierer (Compiler) ist wie ein professioneller Übersetzter für den Computer. Der Code wird in einem mal in "Maschinencode" übersetzt.

Der Interpretierer (Interpreter) ist im gegensatz wie ein Dollmetscher für den Computer. Einzelne Zeilen Code werden auch vereinzelnd in Maschinencode Übersetzt.
Kompelieren ist deshalb in der Regel schneller als Interpretieren, aber das Interpretieren kann beim Testen effiezienter sein.

## Syntax vs. Semantik
Die Syntax sind die Grammatikregeln von der Programmiersprache. Bedeutet es gibt feste Regeln wie der Code geschrieben werden muss (Groß- und Kleinschreibung, Semicollons, usw)

Die Semantik hingegen dient dazu wie verschiedene Befehle ausgeführt werden sollen. Heißt auch wenn der Code richtig geschrieben wurde aber man beispielsweise 5 - 10 schreibt, aber eigentlich 5 + 10 meint kommt das falsche Ergebnis raus. Der Computer hat durch den Schreibfehler die Logik des Programmierers nicht richtig verstanden.

## JDK 
contains the compiler and everything needed to build java programms

## JVM 
runs your compiled program on any operating system 

## IntelliJ "Anleitung + Vokabeln"
Neues Verzeichnis = mkdir "Name der Datei"
cd .. = Change Directory 
Neue Datei = touch "Datei name"
ls = List manager
rm = Remove 
cat "Datei "  = Inhalt wird ausgegeben
clear = löscht die Kommando Zeile
cp = copy == "Datei name" "Neuer Datei name" --> Die neue Datei besitzt den gleichen inhalt wie die alt 
mv = move --> Datei kann verschoben werden oder umbenannt

GitHub: 
git status = schauen ob etwas nicht commitet wurde 
1) git add . = Datei wird hinzugefügt 
2) git commit -m "Was man mit derf datei gemacht hat" Datei Name 
3) git push origin main = Fügt die neue datei hinzu/ updated änderungen die man vorgenommen hat 