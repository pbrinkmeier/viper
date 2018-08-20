% VIPER standard library (revision 1)
%
% Copyright 2010-2018 IPD Snelting
% Copyright 2018 The VIPER authors
%
% Most rules are extracted from the programming paradigms slides from winter
% term 2017/18.
%
% Note that adding rules with the same functor head and arity will not
% "overwrite" any existing rules in this standard library.
% You can disable the standard library via the "Settings" menu at any time.

% member/2:
%   Check if x is member of a list, meaning that x is unifiable with the first
%   element or part of list rest.
%
% Example:
%   ? member(1, [1]).
%   yes.
%   ? member(1, [2, 1]).
%   yes.
member(X, [X | R]).
member(X, [Y | R]) :-
  member(X, R).

% append/3:
%   Append list with an element.
%
% Example:
%   ? append([1, 2], 3, X).
%   X = [1, 2, 3].
append([], L, L).
append([X | R], L, [X | T]) :-
  append(R, L, T).

% delete/3:
%   Delete the first occurence of an element from a list.
%
% Example:
%   ? delete([1, 2, 3], 2, X).
%   X = [1, 3].
delete([X | L], X, L).
delete([X | L], Y, [X | L1]) :-
  delete(L, Y, L1).

% length/2:
%   Calculate length of a list.
%
% Example:
%   ? length([1, 2, 3], X).
%   X = 3.
length([], 0).
length([First | Rest], N) :-
  length(Rest, M),
  N is (M + 1).

% nrev/2:
%   Naive reverse of a list.
%
% Example:
%   ? nrev([1, 2, 3], X).
%   X = [3, 2, 1].
nrev([], []).
nrev([X | R], Y) :-
  nrev(R, Y1),
  append(Y1, [X], Y).

% rev/2:
%   Reverse of a list (faster than nrev).
%
% Example:
%   ? rev([1, 2, 3], X).
%   X = [3, 2, 1].
rev(X, Y) :-
  rev1(X, [], Y).

rev1([], Y, Y).
rev1([X | R], A, Y) :-
  rev1(R, [X | A], Y).

% split/4:
%   Split a list given a split position and a list.
%
% Example:
%   ? split(2, [1, 2, 3], X, Y). % splits [1, 2, 3] at position 2 (index 1)
%   X = [1],
%   Y = [2, 3].
split(X, [], [], []).
split(X, [H | T], [H | R], Y) :-
  X > H,
  split(X, T, R, Y).
split(X, [H | T], R, [H | Y]) :-
  X =< H,
  split(X, T, R, Y).

% qsort/2:
%   Sort a list using the quick sort algorithm.
%
% Example:
%   ? qsort([4, 1, 3, 2], X).
%   X = [1, 2, 3, 4].
qsort([], []).
qsort([X | R], Y) :-
  split(X, R, R1, R2),
  qsort(R1, Y1),
  qsort(R2, Y2),
  append(Y1, [X | Y2], Y).

% qsortclp/2:
%   Sort a list using the quick sort algorithm in the contraint logic
%   programming paradigm.
%
% Example:
%   ? qsortclp([4, 1, 3, 2], X).
%   X = [1, 2, 3, 4].
qsortclp([], []).
qsortclp([X | R], Y) :-
  split(X, R, R1, R2),
  qsortclp(R1, Y1),
  qsortclp(R2, Y2),
  append(Y1, [X | Y2], Y).

% permute/2:
%   Generate permutations of a list.
%
% Example:
%   ? permute([1, 2, 3], X).
%   X = [1, 2, 3].
%   X = [2, 1, 3].
%   X = [2, 3, 1].
%   X = [1, 3, 2].
%   X = [3, 1, 2].
%   X = [3, 2, 1].
permute([], []).
permute([X | R], P) :-
  permute(R, P1),
  append(A, B, P1),
  append(A, [X | B], P).

% sum/4:
%   Calculate sum of three numbers.
%
% Example:
%   ? sum(1, 2, 3, X).
%   X = 6.
sum(A, B, C, S) :-
  S is ((A + B) + C).

% max/3:
%   Determine the maximum of two numbers.
%
% Example:
%   ? max(1, 2, X).
%   X = 2.
max(X, Y, X) :-
  X > Y,
  !.
max(X, Y, Y) :-
  X =< Y.

% even/1:
%   Check if a number is even.
%
% Example:
%   ? even(0).
%   yes.
%   ? even(1).
%   no.
even(0).
even(X) :-
  X > 0,
  X1 is (X - 1),
  odd(X1).

% odd/1:
%   Check if a number is odd.
%
% Example:
%   ? odd(0).
%   no.
%   ? odd(1).
%   yes.
even(0).
odd(1).
odd(X) :-
  X > 1,
  X1 is (X - 1),
  even(X1).

% nfib/2:
%   Calculate n-th fibonacci number (without use of cut).
%   WARNING: CPU intensive!
%
% Example:
%   ? nfib(10, X).
%   X = 55.
nfib(0, 0).
nfib(1, 1).
nfib(X, Y) :-
  X > 1,
  X1 is (X - 1),
  X2 is (X - 2),
  nfib(X1, Y1),
  nfib(X2, Y2),
  Y is (Y1 + Y2).

% fib/2:
%   Calculate n-th fibonacci number.
%   WARNING: CPU intensive!
%
% Example:
%   ? fib(10, X).
%   X = 55.
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

% fac/2:
%   Calculate factorial (n!) of number.
%
% Example:
%   ? fac(5, X).
%   X = 120.
fac(0, 1).
fac(N, X) :-
  M is (N - 1),
  fac(M, Y),
  X is (Y * N),
  !.

% nat/1:
%   Infinitely re-satisfiable predicate.
nat(0).
nat(X) :-
  nat(Y),
  X is (Y + 1).

% neq/2:
%   Check if X and Y are different terms. Both terms need to be instantiated.
neq(X, Y) :-
  X = Y,
  !,
  fail.
neq(X, Y).

% sqrt/2:
%   Calculate square root.
%
% Example:
%   ? sqrt(27, X).
%   X = 5.
sqrt(X, Y) :-
  nat(Y),
  Y2 is (Y * Y),
  Y3 is ((Y + 1) * (Y + 1)),
  Y2 =< X,
  X < Y3.
