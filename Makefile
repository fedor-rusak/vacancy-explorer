clean:
	gradle clean

build:
	gradle assemble

test:
	gradle test

run:
	java -jar build/libs/vacancy-explorer.jar