# A small guide on how to use Git

Although nobody is _forced_ to use these conventions, we agreed on a few basic
rules on how to use git and some of its features.

## Commits

- **English** language

- Use the **imperative** form ("add", "fix", ...)

- Title (first **74** characters) are a short description, more text can follow after two line breaks

- First word of title is **capitalized**

- Prefix your commit message with a short change topic, e.g. "spec:"

- Title does **not** end with a **dot**

Example for a **GOOD** commit message: **spec: Add non-criteria draft**

Example for a **GOOD** commit description: *This commit adds the builtin Prolog print() predicate that is used for debugging clauses*

## Branches and merging

- When updating your local branch to import changes made in master, use **rebase** to prevent merge commits

- **NOBODY** merges into master without the other members being present and/or in complete agreement. When merging, use the `--no-ff` flag to explicitly force a merge commit.

- Branches are created on a **per-feature** basis, **not per member**. If multiple people are working on the same feature, they can **further split up the feature branch** and **merge together later**
