NAME=pflichtenheft

GUI_INPUT_DIR = ./image/gui
GUI_OUTPUT_DIR = ./build/gui

GUI_INPUT_FILES := $(wildcard $(GUI_INPUT_DIR)/*.svg)
GUI_OUTPUT_FILES := $(patsubst $(GUI_INPUT_DIR)/%.svg,$(GUI_OUTPUT_DIR)/%.png,$(GUI_INPUT_FILES))

# Prevents make from automatically the generated PNGs after every build
# It usually does, because they are "intermediate files"
.PRECIOUS: $(GUI_OUTPUT_FILES)

default: $(NAME).pdf

all: $(NAME).pdf

build/%.pdf: %.tex *.sty $(GUI_OUTPUT_FILES)
	mkdir -p build
	latexmk -pdf -g -f -auxdir=build -outdir=build $<

%.pdf: build/%.pdf
	mv $< $@ # atomic!
	cp $@ $<

$(GUI_OUTPUT_DIR)/%.png: $(GUI_INPUT_DIR)/%.svg
	mkdir -p $(GUI_OUTPUT_DIR)
	convert -density 300 $< $@

.PHONY: show clean distclean

show: $(NAME).pdf
	xdg-open $<

clean:
	rm -f *.tmp *.tui *.log *.tuc *.mp *.bbl *.blg *.fls *.idx *.aux *.out *.fdb_latexmk *.ilg *.ind
	rm -rf build

distclean: clean
	rm -f $(NAME).pdf
