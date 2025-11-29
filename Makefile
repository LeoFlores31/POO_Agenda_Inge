# ...existing code...
OUT_DIR = out
SRC_DIR = src/main/java
RESOURCES_DIR = src/main/resources
JAVAC = javac
JAVA = java

JAVAFX_PATH := $(HOME)/Development/javafx-sdk-25.0.1/lib
ifeq ($(wildcard $(JAVAFX_PATH)),)
JAVAFX_PATH := $(shell find /usr/local ~/Applications ~/Library -name "javafx-sdk*" -type d 2>/dev/null | head -1)
ifeq ($(JAVAFX_PATH),)
$(warning ⚠️ JavaFX no encontrado. Usando ruta por defecto /usr/local/javafx-sdk-23/lib)
JAVAFX_PATH := /usr/local/javafx-sdk-23/lib
else
JAVAFX_PATH := $(JAVAFX_PATH)/lib
endif
endif

JAVAFX_MODS = --module-path $(JAVAFX_PATH) --add-modules javafx.controls,javafx.fxml
JAVAC_FLAGS = -d $(OUT_DIR) -cp $(OUT_DIR) $(JAVAFX_MODS)
JAVA_FLAGS = -cp $(OUT_DIR) $(JAVAFX_MODS)

SCENEBUILDER = /Applications/SceneBuilder.app/Contents/MacOS/SceneBuilder

.PHONY: all compile run clean help scene-builder scene-builder-all javafx-info

javafx-info:
	@echo "ℹ️  Información de JavaFX:"
	@echo "   Ruta: $(JAVAFX_PATH)"
	@if [ -d "$(JAVAFX_PATH)" ]; then \
		ls -la $(JAVAFX_PATH) | head -5; \
	else \
		echo "   Ejecuta: brew install javafx-sdk  o extrae SDK en ~/Development/javafx-sdk-21"; \
	fi

SOURCES := $(shell find $(SRC_DIR) -name "*.java")

compile: javafx-info
	@echo "🔨 Compilando proyecto (compilación única de todos los sources)..."
	mkdir -p $(OUT_DIR)
	$(JAVAC) $(JAVAC_FLAGS) $(SOURCES)
	@echo "✅ Compilación completada"

# Copiar recursos (FXML) AL ROOT DE out para que FXMLLoader los encuentre con rutas absolutas
copy-resources:
	@echo "📋 Copiando recursos FXML al root de $(OUT_DIR)..."
	mkdir -p $(OUT_DIR)
	cp -f $(RESOURCES_DIR)/*.fxml $(OUT_DIR)/ 2>/dev/null || echo "⚠️ No se encontraron archivos FXML"
	@echo "✅ Recursos copiados"

run:
	@echo "🚀 Ejecutando aplicación..."
	$(JAVA) $(JAVA_FLAGS) application.App

compile-run: compile copy-resources run

scene-builder:
	@echo "🎨 Abriendo SceneBuilder..."
	@if [ -z "$(FXML)" ]; then \
		echo "❌ Error: Especifica el archivo FXML (ej: FXML=Menu.fxml)"; \
		exit 1; \
	fi
	@if [ ! -f "$(RESOURCES_DIR)/$(FXML)" ]; then \
		echo "❌ Error: Archivo $(RESOURCES_DIR)/$(FXML) no encontrado"; \
		exit 1; \
	fi
	open -a "$(SCENEBUILDER)" "$(RESOURCES_DIR)/$(FXML)"

scene-builder-all:
	@echo "🎨 Abriendo SceneBuilder con todos los FXML..."
	@for fxml in $(RESOURCES_DIR)/*.fxml; do \
		if [ -f "$$fxml" ]; then \
			open -a "$(SCENEBUILDER)" "$$fxml"; \
		fi; \
	done

clean:
	@echo "🧹 Limpiando archivos compilados..."
	rm -rf $(OUT_DIR)
	@echo "✅ Limpieza completada"

all: clean compile copy-resources run

help:
	@echo "📚 Comandos disponibles:"
	@echo "  make compile			- Compila el proyecto"
	@echo "  make copy-resources	 - Copia los FXML al root de out"
	@echo "  make compile-run		 - Compila y ejecuta la aplicación"
	@echo "  make run				- Ejecuta la aplicación"
	@echo "  make scene-builder FXML=File.fxml - Abre un FXML en SceneBuilder"
	@echo "  make clean			  - Elimina los archivos compilados"
# ...existing code...