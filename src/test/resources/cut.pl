a(x) :-
  b(x),
  c(x),
  d(x).

a(x) :-
  e(x),
  f(x),
  !,
  g(x).

a(x) :-
  h(x),
  i(x),
  j(x).

b(x).
c(x).
d(x).
e(x).
f(x).
g(x).