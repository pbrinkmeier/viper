% It is not meant to be a meaningful program

% empty rules, functors, variables, numbers
true.
true(X).
greaterThan(42, 17).

% rules with subgoals
randomRule :-
  % variable goals
  X,
  % functor goals
  isValid(Y),
  % cuts
  !,
  % unification goals
  zomg = Msg,
  1 = Num,
  [X, Y] = [1, 2],
  % arithmetic goals
  X is 42,
  X is 42 + 30 - 3,
  X is 3 * (10 + 13),
  % comparison goals
  X < 100,
  X =< 42,
  X =:= 42,
  X =\= 69,
  X >= 17,
  X > 17,
  last.

% lists are also supported terms
listStuff([], [1, 2], [1 | 2]).

% comment at end of file
