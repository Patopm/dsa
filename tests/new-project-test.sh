#!/bin/sh
set -eu

repo_dir=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
fixture_dir=$(mktemp -d "${TMPDIR:-/tmp}/dsa-generator.XXXXXX")
trap 'rm -rf "$fixture_dir"' EXIT HUP INT TERM

cp -R "$repo_dir/." "$fixture_dir/repo"
cd "$fixture_dir/repo"

missing_output=$(mktemp "$fixture_dir/missing-name.XXXXXX")
if make new >"$missing_output" 2>&1; then
    echo "expected missing NAME to fail" >&2
    exit 1
fi
grep -F "NAME is required" "$missing_output" >/dev/null

echo "missing-name test passed"

invalid_output=$(mktemp "$fixture_dir/invalid-name.XXXXXX")
if make new NAME='../escape' >"$invalid_output" 2>&1; then
    echo "expected unsafe NAME to fail" >&2
    exit 1
fi
grep -F "NAME must use lowercase letters, digits, and internal hyphens" "$invalid_output" >/dev/null

echo "invalid-name test passed"

make new NAME=binary-search-tree

test -f binary-search-tree/build.gradle.kts
test -x binary-search-tree/gradlew
test -f binary-search-tree/gradlew.bat
test -f binary-search-tree/Makefile
test -f binary-search-tree/src/main/java/.gitkeep
test -f binary-search-tree/src/main/java/Main.java
test -f binary-search-tree/src/test/java/.gitkeep
grep -F 'application' binary-search-tree/build.gradle.kts >/dev/null
grep -F 'mainClass.set("Main")' binary-search-tree/build.gradle.kts >/dev/null
grep -E '^run:' binary-search-tree/Makefile >/dev/null
grep -E '^test:' binary-search-tree/Makefile >/dev/null
grep -E '^build:' binary-search-tree/Makefile >/dev/null
grep -E '^clean:' binary-search-tree/Makefile >/dev/null

echo "project-generation test passed"

duplicate_output=$(mktemp "$fixture_dir/duplicate-name.XXXXXX")
if make new NAME=binary-search-tree >"$duplicate_output" 2>&1; then
    echo "expected duplicate NAME to fail" >&2
    exit 1
fi
grep -F "'binary-search-tree' already exists" "$duplicate_output" >/dev/null

echo "duplicate-name test passed"

mv build.gradle.kts build.gradle.kts.saved
missing_root_output=$(mktemp "$fixture_dir/missing-root.XXXXXX")
if make new NAME=orphan-project >"$missing_root_output" 2>&1; then
    echo "expected missing root Gradle files to fail" >&2
    exit 1
fi
grep -F "repository Gradle files are missing" "$missing_root_output" >/dev/null
test ! -e orphan-project
mv build.gradle.kts.saved build.gradle.kts

echo "missing-root test passed"

./gradlew projects | grep -F "Project ':binary-search-tree'"

cat > binary-search-tree/src/test/java/ExampleTest.java <<'EOF'
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExampleTest {
    @Test
    void usesJava25() {
        assertEquals(25, Runtime.version().feature());
    }
}
EOF

(cd binary-search-tree && ./gradlew test)
(cd binary-search-tree && make test)
run_output=$(mktemp "$fixture_dir/make-run.XXXXXX")
(cd binary-search-tree && make run) >"$run_output"
grep -F "Ready." "$run_output" >/dev/null

echo "gradle-integration test passed"
echo "generator acceptance test passed"
