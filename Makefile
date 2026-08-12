.PHONY: help new test

help:
	@printf '%s\n' \
	  'make new NAME=<project-name>  Create a Java activity project' \
	  'make test                     Test the generator and all activities'

new:
	@./scripts/new-project.sh "$(NAME)"

test:
	@./tests/new-project-test.sh
	@./gradlew test
