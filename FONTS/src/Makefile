OUT_DIR = ..\\..\\EXE\\OUT
JAR_DIR = ..\\..\\EXE
LIBS_DIR = ..\\..\\EXE\\lib
JSON_SIMPLE_JAR = $(LIBS_DIR)\\json-simple-1.1.1.jar
DATA_DIR = ..\\..\\FONTS\\src

all: PackageControladors PackageDomain PackageData PackagePresentacio PackageMain exe 

PackageControladors: PackageDomain
	javac -cp $(JSON_SIMPLE_JAR);$(OUT_DIR);. -encoding UTF-8 CtrlDomini\\*.java -d $(OUT_DIR)

PackageDomain:
	javac -cp . -encoding UTF-8 Domini\\*.java -d $(OUT_DIR)

PackageData: PackageDomain
	javac -cp $(JSON_SIMPLE_JAR);$(OUT_DIR);. -encoding UTF-8 data\\*.java -d $(OUT_DIR)

PackagePresentacio: PackageDomain
	javac -cp $(JSON_SIMPLE_JAR);$(OUT_DIR);. -encoding UTF-8 CtrlPresentacio\\*.java -d $(OUT_DIR)
PackageMain: 
	javac -cp $(OUT_DIR);. -encoding UTF-8 Main\\*.java -d $(OUT_DIR)

exe:
	jar cfm $(JAR_DIR)\\Main.jar Manifest.txt -C $(OUT_DIR) .
	jar uf $(JAR_DIR)\\Main.jar -C $(LIBS_DIR) json-simple-1.1.1.jar

clean:
	if exist "$(OUT_DIR)" rmdir /s /q "$(OUT_DIR)"
	if exist "$(JAR_DIR)\\*.jar" del "$(JAR_DIR)\\*.jar"
