# TODO

- Einleitung mit Anschluss an das Pflichtenheft, Aufteilung in Pakete erklären
- Detaillierte Modellierung des Pakets: GUI  ("view")
- Detaillierte Modellierung des Pakets: Input (Listener für GUI-Elemente) ("controller")
- Detaillierte Modellierung des Pakets: Parser (Dokumentation des gegebenen Quellcodes sowie Spezifizierung auf unsere eigenen Datenstrukturen) ("model")
- Detaillierte Modellierung des Pakets: Interpreter (TODO: Interpreter-Architektur) ("model")
- Detaillierte Modellierung des Pakets: Graphgenerierung und interne Darstellung (Datenstruktur für gerichteten Graphen mit Backtracking-Pfeilen) ("model")
- Sequenzdiagramme für Test-Cases

# Entwurfsmuster (vgl. Gang of Four)

- Interpreter ist eigenes Entwurfsmuster
- Observer ist implizit durch Java Listener bereits gegeben
- TODO: Mehr Entwurfsmuster herausarbeiten

# Architekturstil

- Model-view-controller
  - "model" - Datenverwaltung, Ausgabe über View, Input über Controller
  - "view" - graphische Oberfläche, Interaktions-Schnittstelle für den Nutzer
    - Java mit Swing (gut dokumentiert, bekannt, mit MVC vereinbar, unterstützt alle Target-Platforms)
  - "controller" - verarbeitet Input, leitet weiter an Model bzw. View
    - Schaltflächen-Listener
    - Menüleisten-Listener
    - Konsolen-Eingabebereich
    - Navigations-Input für die Visualisierung

# Klassenentwurf

## Model (interne Datenverwaltung)

- Datei (Java: File)

- Inhalt des Konsolen-Ausgabefensters (Java: String)

- Parser (anzupassen: PrologParser) und Lexer (gegeben)
  - Token (gegeben)
  - Fakt
  - Regel
  - Variable
  - Funktor
  - Unifikation (gegeben in Pseudocode)
  - Substitution
  - Term
  - Ziel
  - Cut
  - Unifikationsziel
  - Arithmetik (arithmetische Operatoren, is)
  - Standardbibliothek (Java: String, wird einfach an zu parsenden Quellcode angehangen)

- Interpreter
  - Abfrage (Java: String, vom Lexer in Tokens umgewandelt)
  - Zustand
  - gerichteter Graph (Baum)

- Exceptions (extends Exception)
  - VIOpenFileException  - Fehler beim Öffnen (beschädigte Datei, Dateiformat wird nicht unterstützt)
  - VISaveFileException  - Fehler beim Speichern (keine Schreibrechte, zu wenig Speicherplatz)
  - VIParseException     - Fehler beim Parsen von Quellcode (Syntax-Fehler) (entweder ersetzen durch oder wrappen von gegebener ParseException)
  - VIInterpretException - Fehler beim Parsen von Abfragen (Syntax-Fehler)

## View (GUI)

- Hauptfenster (extends Java: JFrame)

- Editor (Library: RSyntaxTextArea) 

- Konsole
  - Eingabefeld (extends Java: JTextField) mit Präfix-Text (?-)
  - Ausgabe-Fenster (extends Java: JTextField)

- Visualisierungs-Fenster (extends Java: JPanel, Library: ZGRViewer)

- Schaltfläche (extends Java: JButton)
  - Neu
  - Öffnen
  - Speichern
  - Speichern als...
  - Parsen
  - Nächster Schritt
  - Nächste Lösung
  - Vorheriger Schritt
  - Abbrechen
  - Zoom für Visualisierung

- Schnellzugriffsleiste / Menüleiste (extends Java: JMenuBar)
  - Funktionalitäten wie bereits bei den Schaltflächen (extends Java: JMenuItem)
  - Aktivierung/Deaktivierung der STDLIB (extends Java: JMenuItem)
  - Formatierung (extends Java: JMenuItem)
  - Sprachwahl (extends Java: JMenuItem)
  - Bild-Export (extends Java: JMenuItem)
  - LaTeX-Export (extends Java: JMenuItem)

- Nicht-hartkodierter Text der GUI-Elemente (Java: ResourceBundle)

## Controller (Input)

- Listener für Schaltflächen (implements Java: ActionListener)
- Listener für Menüleiste (implements Java: ActionListener)
- Listener für Mausinput im Visualisierungs-Fenster (implements Java: MouseListener)
