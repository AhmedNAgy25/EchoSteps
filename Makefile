build:
	mkdir -p build
	javac -d build $(shell find src -name "*.java")
	cp -r resources/* build/

run:
	java -cp build echosteps.Main

clean:
	rm -rf build

restart:
	make clean
	make build
	make run