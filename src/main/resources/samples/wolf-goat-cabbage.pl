% The infamous wolf-goat-cabbage riddle and how to solve it using Prolog.
% 
% A farmer wants to cross a river with his wolf, goat and cabbage.
% If he leaves the wolf and the goat alone together, the wolf will eat the goat;
% likewise, if he leaves the goat and the cabbage alone together, the goat will eat the cabbage.
% He can always cross the river alone or take a single item with him.
%
% Enter "solve(Moves)." to calculate a list of valid movements.

% The river has two opposite sides: left (l) and right (r)
oppositeBank(l, r).
oppositeBank(r, l).

% Initially, the farmer, wolf, goat and cabbage are on the left side.
% state(Farmer, Wolf, Goat, Cabbage) represents the positions of the farmer and the items.
initial(state(l, l, l, l)).

% Our goal is to have the farmer and all of the items on the right side.
goal(state(r, r, r, r)).

% Valid moves are:
% The farmer changes sides alone;
move(state(A, W, G, C), state(B, W, G, C), m(alone, B)) :-
  oppositeBank(A, B).
% with the wolf;
move(state(A, A, G, C), state(B, B, G, C), m(wolf, B)) :-
  oppositeBank(A, B).
% with the goat;
move(state(A, W, A, C), state(B, W, B, C), m(goat, B)) :-
  oppositeBank(A, B).
% or with the cabbage.
move(state(A, W, G, A), state(B, W, G, B), m(cabbage, B)) :-
  oppositeBank(A, B).

% Valid states include (the Cut is used here to make isValid/1 deterministic):
% Wolf and goat, goat and cabbage are on opposing sides;
isValid(state(Farmer, Wolf, Goat, Cabbage)) :-
  oppositeBank(Wolf, Goat),
  oppositeBank(Goat, Cabbage),
  !.
% the farmer watches over wolf and goat;
isValid(state(Farmer, Farmer, Farmer, Cabbage)) :-
  !.
% or the farmer watches over goat and cabbage.
isValid(state(Farmer, Wolf, Farmer, Farmer)) :-
  !.

% If the farmer and all items are in the goal state, the riddle is solved.
% The Cut is used here to stop Prolog from trying the second solve/2 rule.
solve(State, PreviousSteps, []) :-
  goal(State),
  !.
% If the state is not a solved state, try to find a new valid move.
solve(State, PreviousStates, [Move | MoveList]) :-
  % Find a valid move and the resulting state,
  move(State, NewState, Move),
  % check whether the new state is valid,
  isValid(NewState),
  % check whether the new state has been calculated before...
  noMember(NewState, PreviousStates),
  % and recursively solve the riddle.
  solve(NewState, [State | PreviousStates], MoveList).

% Shorthand for finding valid move lists.
solve(Moves) :-
  initial(S),
  solve(S, [], Moves).

% X is never an element of the empty list.
noMember(X, []).
% If X is the first element of the given list, fail.
noMember(X, [X | R]) :-
  !,
  fail.
% If it isn't, check the rest of the list.
noMember(X, [Y | R]) :-
  noMember(X, R).
