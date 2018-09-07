% functor
father(abe, homer).
father(homer, bart).

grandfather(X, Y) :- father(X, Z), father(Z, Y).

% functor_nohead
succeed.

% unification, unification_fail
doubleEq(X, Y) :- X = Y, X = Y.

% cut
cutMyLife :- !, intoPieces.

% arithmetic, arithmetic_error
triple(X, Y) :-
  Z is X * 2,
  Y is Z + X.

% comparison, comparison_error
ordered(X, Y, Z) :-
  X =< Y,
  Y =< Z.
