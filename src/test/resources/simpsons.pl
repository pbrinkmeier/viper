% Simple simpsons example program
% This is mostly used for testing purposes
father(abe, homer).
father(homer, bart).
grandfather(X, Y) :- father(X, Z), father(Z, Y).
% End comment %