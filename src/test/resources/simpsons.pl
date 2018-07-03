father(abe, homer).
father(homer, bart).
grandfather(X, Y) :- father(X, Z), father(Z, Y).