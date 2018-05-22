NAME=pflichtenheft

GUI_INPUT_DIR = ./image/gui
GUI_OUTPUT_DIR = ./build/gui

GUI_INPUT_FILES := $(wildcard $(GUI_INPUT_DIR)/*.svg)
GUI_OUTPUT_FILES := $(patsubst $(GUI_INPUT_DIR)/%.svg,$(GUI_OUTPUT_DIR)/%.png,$(GUI_INPUT_FILES))

GRAPHS_INPUT_DIR = ./image/visualisation
GRAPHS_OUTPUT_DIR = ./build/visualisation

GRAPHS_INPUT_FILES := $(wildcard $(GRAPHS_INPUT_DIR)/*.gv)
GRAPHS_OUTPUT_FILES := $(patsubst $(GRAPHS_INPUT_DIR)/%.gv,$(GRAPHS_OUTPUT_DIR)/%.png,$(GRAPHS_INPUT_FILES))

# Prevents make from automatically the generated PNGs after every build
# It usually does, because they are "intermediate files"
.PRECIOUS: $(GUI_OUTPUT_FILES) $(GRAPHS_OUTPUT_FILES)

# Phony targets do not produce a file named like them
.PHONY: show clean distclean

default: $(NAME).pdf

all: $(NAME).pdf

build/%.pdf: %.tex *.sty $(GUI_OUTPUT_FILES) $(GRAPHS_OUTPUT_FILES)
	mkdir -p build
	latexmk -pdf -g -f -auxdir=build -outdir=build $<

%.pdf: build/%.pdf
	mv $< $@ # atomic!
	cp $@ $<

$(GUI_OUTPUT_DIR)/%.png: $(GUI_INPUT_DIR)/%.svg
	mkdir -p $(GUI_OUTPUT_DIR)
	convert -density 300 $< $@

$(GRAPHS_OUTPUT_DIR)/%.png: $(GRAPHS_INPUT_DIR)/%.gv
	mkdir -p $(GRAPHS_OUTPUT_DIR)
	dot -Tpng -Gdpi=300 $< > $@

show: $(NAME).pdf
	xdg-open $<

clean:
	rm -f *.tmp *.tui *.log *.tuc *.mp *.bbl *.blg *.fls *.idx *.aux *.out *.fdb_latexmk *.ilg *.ind
	rm -rf build

distclean: clean
	rm -f $(NAME).pdf
