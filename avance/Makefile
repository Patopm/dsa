.PHONY: help run test build clean

help:
	@printf '%s\n' \
	  'make run    Start avance' \
	  'make test   Run avance tests' \
	  'make build  Compile and test avance' \
	  'make clean  Remove avance build output'

run:
	@./gradlew run --console=plain

test:
	@./gradlew test

build:
	@./gradlew build

clean:
	@./gradlew clean
