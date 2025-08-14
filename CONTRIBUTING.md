# Contributing to Project3_AP_KNTU

Thank you for your interest in contributing to Project3\_AP\_KNTU. This document explains how to report issues, propose changes, and submit pull requests.

## Table of contents

* Getting started
* Building the project
* Project structure
* Coding conventions
* Branching and commits
* Writing tests
* Submitting a pull request
* Reporting issues
* CI and quality checks
* License and contact

## Getting started

Prerequisites:

* Java Development Kit (JDK) 11 or newer
* Git
* IDE (IntelliJ IDEA, Eclipse, or similar)

Clone the repository:

```bash
git clone https://github.com/mahajialirezaei/Project3_AP_KNTU.git
cd Project3_AP_KNTU
```

If the repository includes the Gradle wrapper, prefer using it to build and run tasks.

## Building the project

Build and run using the Gradle wrapper:

```bash
./gradlew build
./gradlew run
```

If the project is a plain Java application without Gradle tasks, follow the instructions in the repository README for compilation and execution.

## Project structure

* `src/` — source code (Java)
* `gradle/`, `gradlew`, `gradlew.bat` — Gradle wrapper and configuration
* `build.gradle` — build configuration
* `README.md` — project overview and usage

Adjust paths above if the repository layout differs.

## Coding conventions

* Follow standard Java coding conventions (Oracle style): 4-space indentation, CamelCase for classes, camelCase for methods and variables.
* Keep methods short and single-responsibility.
* Write clear and descriptive names for classes, methods, and variables.
* Place public constants in UPPER\_SNAKE\_CASE.
* Document non-obvious logic with concise Javadoc where helpful.

## Branching and commits

* Fork the repository and create a feature branch from `main`:

```bash
git checkout -b feature/short-description
```

* Keep changes focused and small. Prefer multiple small PRs to one large PR.
* Use clear, imperative commit messages. Reference issue numbers where appropriate.
* Rebase or squash local commits before opening a PR if needed to keep history tidy.

## Writing tests

* Add unit tests alongside code in `src/test/java` (or follow the repository's test layout).
* Use JUnit for unit tests. Ensure tests are deterministic and fast.
* Run tests locally before opening a PR:

```bash
./gradlew test
```

Include test cases for bug fixes and new features.

## Submitting a pull request

1. Push your branch to your fork.
2. Open a pull request against `main` in the upstream repository.
3. In the PR description include:

   * A short summary of changes
   * Motivation and context
   * How to build and test the change
   * Any relevant screenshots or logs
4. Address review comments and update the PR accordingly.

## Reporting issues

When reporting bugs or proposing features, include:

* A descriptive title
* Steps to reproduce
* Expected vs actual behavior
* Environment details (OS, JDK version, IDE)
* Relevant logs or stack traces

Use labels and templates if the repository provides them.

## CI and quality checks

This repository contains CI configuration (for example, `.gitlab-ci.yml`). Contributions should pass existing CI checks. Common checks include:

* Build and unit tests
* Static analysis or linters
* Code style checks

If the repository uses Qodana or other quality tools, fix violations or explain exceptions in the PR.

## License and contact

By contributing you agree that your changes will be included under the repository's license. If a license is not specified, request clarification in an issue.

For questions or coordination, open an issue or create a pull request. Thank you for contributing!
