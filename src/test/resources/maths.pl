max(X, Y, X) :-
  X > Y, !.
max(X, Y, Y).

sum(X, Y, Z) :-
  Z is X + Y, !.
sum(X, Y, Z) :-
  Y is Z - X, !.
sum(X, Y, Z) :-
  X is Z - Y.
