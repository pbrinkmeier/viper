# Entwurfsmuster (vgl. Gang of Four)

- Observer ist implizit durch Java Listener bereits gegeben, benutze evtl. auch für Sprachumstellung

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

- Parser (anzupassen: PrologParser) und Lexer (gegeben); Keywords (nicht zwingend Klassen):
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
  - Standardbibliothek (Java: String)

- Interpreter
  - Abfrage (Java: String, vom Lexer in Tokens umgewandelt)
  - Zustand
  - gerichteter Graph (Baum)

- Exceptions (extends Exception)
  - OpenFileException  - Fehler beim Öffnen (beschädigte Datei, Dateiformat wird nicht unterstützt)
  - SaveFileException  - Fehler beim Speichern (keine Schreibrechte, zu wenig Speicherplatz)
  - ParseException     - Fehler beim Parsen von Quellcode (Syntax-Fehler) (gegeben, darf auch manipuliert werden)
  - InterpretException - Fehler beim Parsen von Abfragen (Syntax-Fehler)

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
