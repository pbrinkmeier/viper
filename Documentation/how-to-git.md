A small guide on how to use Git
===============================

Although nobody is _forced_ to use these conventions, we agreed on a few basic
rules on how to use git and some of its features.

Commits
-------
  - **English** language

  - Use the **imperative** form ("add", "fix", ...)

  - Title (first **74** characters) are a short description, more text can follow
    after two line breaks

  - First word of title is **capitalized**

  - Title does **not** end with a **dot**

  Example for a **GOOD** commit message: **Add builtin predicate print()**

  Example for a **GOOD** commit description: *This commit adds the builtin Prolog print() predicate that is used for debugging clauses*

Branches and merging
--------------------
  - **NOBODY** merges into master without the other members being present and/or in
    complete agreement

  - Branches are created on a **per-feature** basis, **not per member**. If multiple people are working on the same feature, they can **further split up the feature branch** and **merge together later**
