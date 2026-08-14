.PHONY: help run test build clean

help:
	@printf '%s\n' \
	  'make run    Start the Main interactive menu' \
	  'make test   Run act1 tests' \
	  'make build  Compile and test act1' \
	  'make clean  Remove act1 build output'

run:
	@./gradlew run --console=plain

test:
	@./gradlew test

build:
	@./gradlew build

clean:
	@./gradlew clean
