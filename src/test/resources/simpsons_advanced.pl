father(abe, homer).
father(homer, bart).
father(homer, lisa).
mother(marge, bart).

grandfather(X, Y) :-
  father(X, Z),
  parent(Z, Y).

parent(X, Y) :-
  mother(X, Y).

parent(X, Y) :-
  father(X, Y).
