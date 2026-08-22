.PHONY: help run test build clean

help:
	@printf '%s\n' \
	  'make run    Start act2' \
	  'make test   Run act2 tests' \
	  'make build  Compile and test act2' \
	  'make clean  Remove act2 build output'

run:
	@./gradlew run --console=plain

test:
	@./gradlew test

build:
	@./gradlew build

clean:
	@./gradlew clean
