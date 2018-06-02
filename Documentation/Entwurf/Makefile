NAME=entwurf

# Phony targets do not produce a file named like them
.PHONY: show clean distclean

default: $(NAME).pdf

all: $(NAME).pdf

build/%.pdf: %.tex
	mkdir -p build
	latexmk -pdf -g -f -auxdir=build -outdir=build $<

%.pdf: build/%.pdf
	mv $< $@ # atomic!
	cp $@ $<

show: $(NAME).pdf
	xdg-open $<

clean:
	rm -f *.tmp *.tui *.log *.tuc *.mp *.bbl *.blg *.fls *.idx *.aux *.out *.fdb_latexmk *.ilg *.ind
	rm -rf build

distclean: clean
	rm -f $(NAME).pdf
