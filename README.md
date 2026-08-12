# DSA Activities

Independent Java projects for data structures and algorithms coursework, managed as one Gradle multi-project build.

## Requirements

- JDK 25
- A POSIX shell and Make for project generation

Gradle itself does not need to be installed; use the checked-in wrapper.

## Create an activity

Project names use lowercase letters, digits, and internal hyphens.

```bash
make new NAME=linked-list
```

Then add Java source under `linked-list/src/main/java` and tests under `linked-list/src/test/java`.

## Test

Test every activity from the repository root:

```bash
./gradlew test
```

Test one activity from the root, using its project name:

```bash
./gradlew :act1:test
```

Or from that activity's directory:

```bash
cd act1
./gradlew test
```

If the activity includes a Makefile:

```bash
cd act1
make test
```

Run generator checks and all activity tests:

```bash
make test
```

## Run

Activities that apply the Gradle application plugin and set a `Main` class can be started from the repository root:

```bash
./gradlew :act1:run
```

Or from the activity directory:

```bash
cd act1
./gradlew run
```

If the activity includes a Makefile:

```bash
cd act1
make run
```

`act1` is the linked-list menu. From its menu you choose a list type, then insert, delete, search, display, run data-type examples, or manage contacts.

Activity-specific dependencies and the application plugin belong in that activity's `build.gradle.kts`.
