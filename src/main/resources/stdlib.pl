% append list with another list and return the new list
append([], L, L).
append([H|T], L, [H|R]) :-
  append(T, L, R).

% length of empty list is zero
length([], 0).
% length of non-empty list is 1 + rest of list
length([First | Rest], N) :-
  length(Rest, M),
  N is M + 1.
