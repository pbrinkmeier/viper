max(X, Y, X) :-
  X > Y, !.
max(X, Y, Y).

sum(X, Y, Z) :-
  Z is X + Y, !.
sum(X, Y, Z) :-
  Y is Z - X, !.
sum(X, Y, Z) :-
  X is Z - Y.

fac(0, 1).
fac(N, X) :-
  M is N - 1,
  fac(M, Y),
  X is N * Y,
  !.
