# EchoSteps

## Running the Project

This project includes a `Makefile` to simplify common tasks. Below are the available commands:

### Makefile Commands

The Makefile automates building, running, and cleaning the project.

- `make build`  
  Compiles all Java source files from `src/` into the `build/` directory and copies all assets from `resources/` into `build/`.

- `make run`  
  Runs the main class `echosteps.Main` using the compiled files and resources in `build/`.

- `make clean`  
  Deletes the `build/` directory and all compiled files.

- `make restart`  
  Runs `clean`, then `build`, then `run` in sequence to rebuild and start the project fresh.

### Usage

From the project root, run any of the commands, for example:

```bash
make restart
```
