#!/bin/sh
set -eu

project_name=${1-}
repo_dir=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)

if [ -z "$project_name" ]; then
    echo "error: NAME is required (example: make new NAME=linked-list)" >&2
    exit 2
fi

case "$project_name" in
    *[!a-z0-9-]*|-*|*-)
        echo "error: NAME must use lowercase letters, digits, and internal hyphens" >&2
        exit 2
        ;;
esac

if [ ! -x "$repo_dir/gradlew" ] || [ ! -f "$repo_dir/build.gradle.kts" ]; then
    echo "error: repository Gradle files are missing" >&2
    exit 2
fi

target_dir=$repo_dir/$project_name
if [ -e "$target_dir" ]; then
    echo "error: '$project_name' already exists" >&2
    exit 2
fi

staging_dir=$(mktemp -d "$repo_dir/.new-project.XXXXXX")
trap 'rm -rf "$staging_dir"' EXIT HUP INT TERM

mkdir -p "$staging_dir/src/main/java" "$staging_dir/src/test/java"
: > "$staging_dir/src/main/java/.gitkeep"
: > "$staging_dir/src/test/java/.gitkeep"

cat > "$staging_dir/build.gradle.kts" <<'EOF'
plugins {
    application
}

application {
    mainClass.set("Main")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
EOF

cat > "$staging_dir/src/main/java/Main.java" <<'EOF'
public class Main {
    public static void main(String[] args) {
        System.out.println("Ready.");
    }
}
EOF

cat > "$staging_dir/gradlew" <<'EOF'
#!/bin/sh
set -eu
project_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
exec "$project_dir/../gradlew" -p "$project_dir" "$@"
EOF
chmod +x "$staging_dir/gradlew"

cat > "$staging_dir/gradlew.bat" <<'EOF'
@echo off
set "PROJECT_DIR=%~dp0"
call "%PROJECT_DIR%..\gradlew.bat" -p "%PROJECT_DIR%" %*
EOF

cat > "$staging_dir/Makefile" <<EOF
.PHONY: help run test build clean

help:
	@printf '%s\n' \\
	  'make run    Start ${project_name}' \\
	  'make test   Run ${project_name} tests' \\
	  'make build  Compile and test ${project_name}' \\
	  'make clean  Remove ${project_name} build output'

run:
	@./gradlew run --console=plain

test:
	@./gradlew test

build:
	@./gradlew build

clean:
	@./gradlew clean
EOF

mv "$staging_dir" "$target_dir"
trap - EXIT HUP INT TERM

printf "Created Java project '%s'.\n" "$project_name"
printf "Run: cd %s && make run\n" "$project_name"
