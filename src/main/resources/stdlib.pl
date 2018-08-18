%
% VIPER standard library
%   revision 1
%
% Most rules are extracted from the programming paradigms slides from winter
% term 2017/18.

% check if x is member of a list (unifiable with the first element or part of
% the list rest)
member(X, [X | R]).
member(X, [Y | R]) :-
  member(X, R).

% append list with another list and return the new list
append([], L, L).
append([X | R], L, [X | T]) :-
  append(R, L, T).

% delete from list
delete([X | L], X, L).
delete([X | L], Y, [X | L1]) :-
  delete(L, Y, L1).

% length of empty list is zero
length([], 0).
% length of non-empty list is 1 + rest of list
length([First | Rest], N) :-
  length(Rest, M),
  N is (M + 1).

% naive reverse
nrev([], []).
nrev([X | R], Y) :-
  nrev(R, Y1),
  append(Y1, [X], Y).

% advanced reverse
rev(X, Y) :-
  rev1(X, [], Y).

rev1([], Y, Y).
rev1([X | R], A, Y) :-
  rev1(R, [X | A], Y).

% split a list
split(X, [], [], []).
split(X, [H | T], [H | R], Y) :-
  X > H,
  split(X, T, R, Y).
split(X, [H | T], R, [H | Y]) :-
  X =< H,
  split(X, T, R, Y).

% quicksort
qsort([], []).
qsort([X | R], Y) :-
  split(X, R, R1, R2),
  qsort(R1, Y1),
  qsort(R2, Y2),
  append(Y1, [X | Y2], Y).

% quicksort using contraint logic programming
qsortclp([], []).
qsortclp([X | R], Y) :-
  split(X, R, R1, R2),
  qsortclp(R1, Y1),
  qsortclp(R2, Y2),
  append(Y1, [X | Y2], Y).

% list permutations
permute([], []).
permute([X | R], P) :-
  permute(R, P1),
  append(A, B, P1),
  append(A, [X | B], P).

% sum
sum(A, B, C, S) :-
  S is ((A + B) + C).

% max (with cut)
max(X, Y, X) :-
  X > Y,
  !.
max(X, Y, Y) :-
  X =< Y.

% even
even(0).
even(X) :-
  X > 0,
  X1 is (X - 1),
  odd(X1).

odd(1).
odd(X) :-
  X > 1,
  X1 is (X - 1),
  even(X1).

% naive fibonacci (without cut)
nfib(0, 0).
nfib(1, 1).
nfib(X, Y) :-
  X > 1,
  X1 is (X - 1),
  X2 is (X - 2),
  nfib(X1, X2),
  nfib(X2, Y2),
  Y is (Y1 + Y2).

% fibonacci with cut
fib(0, Y) :-
  !,
  Y = 0.
fib(1, Y) :-
  !,
  Y = 1.
fib(X, Y) :-
  X1 is (X - 1),
  X2 is (X - 2),
  fib(X1, Y1),
  fib(X2, Y2),
  Y is (Y1 + Y2).

% infinitely re-satisfiable predicate
nat(0).
nat(X) :-
  nat(Y),
  X is (Y + 1).

% square root
sqrt(X, Y) :-
  nat(Y),
  Y2 is (Y * Y),
  Y3 is ((Y + 1) * (Y + 1)),
  Y2 =< X,
  X < Y3.
