# Makefile for NanoTekSpice

# Variables
MVN = mvn
EXEC = nanotekspice
TARGET_DIR = target
JAR_FILE = $(TARGET_DIR)/$(EXEC)-1.0-SNAPSHOT.jar
ROOT_EXEC = ./$(EXEC)

# Default rule
all: $(ROOT_EXEC)

# Compilation rule
compile:
	$(MVN) compile

# Test rule
test:
	$(MVN) test

# Rule to build the project and create the executable
$(ROOT_EXEC): compile
	$(MVN) package
	echo '#!/bin/sh\njava -jar $(JAR_FILE) "$$@"' > $(ROOT_EXEC)
	chmod +x $(ROOT_EXEC)

# Clean rule (removes all generated files except the executable)
clean:
	$(MVN) clean
	rm -f $(ROOT_EXEC)

# Re rule (rebuild everything)
re: clean all

# Fclean rule (removes all generated files including the executable)
fclean: clean
	rm -rf $(TARGET_DIR)

.PHONY: all compile test clean re fclean
