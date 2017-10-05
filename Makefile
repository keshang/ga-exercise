
draft.pdf: draft.tex reference.bib
	pdflatex draft
	bibtex draft
	pdflatex draft
	pdflatex draft

# draft.pdf: draft.tex abstract.tex introduction.tex related_work.tex experimental_results.tex   experimental_settings.tex conclusion.tex reference.bib
# 	platex draft
# 	bibtex draft
# 	platex draft
# 	platex draft
# 	dvipdfmx -p letter -f texfonts.map draft

all: draft.pdf

clean:
	rm -f *.aux *.bbl *.blg *.log draft.dvi draft.ps 
