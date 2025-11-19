
OUT_DIR = out

java-compile:
	javac -d $(OUT_DIR) Main.java model/*.java model/cita/*.java dao/*.java utils/*.java

java-run:
	java -cp $(OUT_DIR) Main

clean:
	rm -rf $(OUT_DIR)

all: java-compile java-run