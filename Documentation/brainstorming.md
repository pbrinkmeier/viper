# Editor

  - RSyntaxTextArea (https://github.com/bobbylight/RSyntaxTextArea)
  - AutoComplete (https://github.com/bobbylight/AutoComplete)
  - Muss
    - Syntax Highlighting
    - Auto Indentation
    - Line Numbers
    - Speichern und Laden von .pl Dateien
    - Recently opened files
    - Export vom Graphen (SVG, PNG, ...)
    - Konsolenausgabe und -abfrage
    - Shortcuts für Speichern, Laden, Ausführen, Visualisierung, Export, Formatting etc.
    - Einzelne Komponenten resizen (Interpreter-Fenster, Tree View etc.)
    - Fehleroutput von Parser im Interpreter-Fenster

  - Kann
    - Formatter mit Konfigurationsfenster (Spaces vs. Tabs, wie viele davon, etc.)
    - Tree View  und Tabs
    - Minimap
    - Auto-Completion
    - Dark-Theme (Light-Theme by default)
    - LaTeX-Export für Folien (Code sowie Visualisierungsbaum als SVG)

# Debugger

  - Breakpoints bei Zeilen
  - Ausführung bis Breakpoints, dann Interpreter der die Fakten bis zu diesem Punkt beachtet

# Visualisierung

  - GraphViz-Java (https://github.com/nidi3/graphviz-java) zur Graphviz-Generierung
  - ZGRViever (http://zvtm.sourceforge.net/zgrviewer.html) zur Visualisierung
  - Muss
    - Navigieren
    - Zoomen
  - Kann
    - Verschiedene Stile für Graphen

# Sprachfeatures

  - Muss
    - Prolog ohne das was bei "Kann" steht (Parser bereits gegeben) 
  - Kann
    - Standard-Bibliothek (Listen etc.)
    - Unifikationsziele
    - Artihmetik
    - Cut-Feature

# Nicht-funktionale Anforderungen

  - Viper Hauptprogramm ist nach kurzer Zeit einsatzbereit
  - Ein Programm ohne Rekursion unter 100 LoC ist nach 3 Sekunden ausgeführt und nach 5 visualisiert
  - Internationale Studenten können die Oberfläche bedienen (Sprachunterstützung und Symbole)
  - One-Click Installation, kein Setup von Abhängigkeiten
  - Ohne Abhängigkeiten ist die Software kleiner als 50 MiB, mit Abhängigkeiten kleiner als 200 MiB
  - Laden einer Datei unter 1MiB in unter 5 Sekunden

# Statische Analyse, Warnungen

  - Unreachable Code bei Rekursion
  - Sinnvolle Fehlermeldungen
  - Fehler bei mehr als einer Abfrage im Programm **oder** nur die letzte Abfrage beachten (Parser vergleichen)

# Sonstiges

  - Einfache Installation (nicht Graphviz aufsetzen müssen etc.)
  - Sprachunterstützung: Englisch, Deutsch
  - Buttonleiste editierbar / modular
  - Beispielprogramme zu Bildungszwecken

# Entwicklung
  - Code-Stil festgelegt und im Pflichtenheft beschrieben
  - IDE: Eclipse
  - Stil: Checkstyle, clang-format
  - Testing: JUnit
  - Coverage: JCov
  - Build system: Gradle
  - CI: Jenkins
  - VCS: git mit SCC GitLab

# Produkteinsatz

  - Graphisches Prolog-Lerntool
  - Zielgruppe: Studierende
  - Kein vollständiger Prolog-Interpreter, Abdeckung einer Teilmenge
  - Integrierte Entwicklungsumgebung (Autocompletion, Debugger, Syntax Highlighting etc.) 

# Produktumgebung

  - Graphische Applikation auf einem Desktop-System
  - Mindestens 2 AMD64 oder x86 Kerne eines modernen Prozessors (später als 2010?) mit insgesamt 2GB shared RAM zur Verfügung
  - Unterstützung für Windows 7 und aufwärts, Mac OSX (Minimalversion?) und Ubuntu 16.04
  - Verlangte Eingabegeräte sind Maus und Tastatur
  - Java-Installation Version 8 und aufwärts

# Mögliche Testcases

  - Programm öffnen und schließen
  - Datei öffnen, speichern
  - Interpretation von Hello-World, evtl. Fibonacci / Simpsons-Familie sowie Visualisierung
  - Alle Parser-Fehlermeldungen
  - Debugger stoppt bei Breakpoint
  - Export (Bild, LaTeX etc.)
