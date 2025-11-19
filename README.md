# 📋 Agenda de Citas Médicas - El Inge

Sistema de gestión de citas médicas desarrollado en Java con arquitectura MVC que permite administrar pacientes y agendar citas matutinas y vespertinas con motivos específicos para cada turno.

## 🎯 Descripción General

**Agenda de Citas Médicas** es una aplicación de consola que facilita la administración de pacientes y citas médicas. El sistema diferencia entre citas matutinas (antes de las 12:00) y vespertinas (a partir de las 12:00), cada una con motivos de consulta específicos y duraciones diferentes.

### Características principales:

- ✅ Gestión completa de pacientes (CRUD)
- ✅ Creación de citas matutinas y vespertinas
- ✅ Motivos de cita específicos por turno
- ✅ Validación de disponibilidad de horarios
- ✅ Búsqueda de citas por paciente
- ✅ Persistencia de datos mediante serialización

---

## 📁 Estructura del Proyecto

```
POO_Agenda_Inge/
├── Main.java                 # Punto de entrada de la aplicación
├── model/                    # Clases del modelo de datos
│   ├── Paciente.java        # Entidad de paciente
│   ├── Agenda.java          # Gestor de citas
│   ├── GestorPacientes.java # Gestor de pacientes
│   └── cita/                # Paquete de citas
│       ├── Cita.java        # Clase abstracta base
│       ├── CitaMatutina.java # Citas matutinas (antes de 12:00)
│       ├── CitaVespertina.java # Citas vespertinas (después de 12:00)
│       └── MotivoCita.java  # Motivos de consulta
├── dao/                      # Data Access Objects (Persistencia)
│   ├── AgendaDAO.java       # Serialización de citas
│   └── GestorPacientesDAO.java # Serialización de pacientes
├── utils/                    # Utilidades y controladores
│   ├── Menu.java            # Métodos de visualización de menús
│   ├── SubMenus.java        # Submenús de la aplicación
│   └── ControladorCitas.java # Lógica de control de citas
└── data/                     # Directorio de almacenamiento de datos
    ├── pacientes.dat        # Archivo serializado de pacientes
    └── citas.dat            # Archivo serializado de citas
```

---

## 🔄 Diagrama de Dependencias

```
┌─────────────────────────────────────────────────────────────┐
│                        Main.java                            │
│              (Punto de entrada - Orquestador)               │
└────────────────┬──────────────────┬─────────────────────────┘
                 │                  │
         ┌───────▼──────┐   ┌──────▼──────────┐
         │  Pacientes   │   │     Citas       │
         └───────┬──────┘   └──────┬──────────┘
                 │                  │
    ┌────────────▼───────────┐  ┌──▼────────────────────┐
    │  GestorPacientes.java  │  │   Agenda.java         │
    │  (Lógica pacientes)    │  │  (Lógica citas)       │
    └────────────┬───────────┘  └───┬───────────────────┘
                 │                  │
    ┌────────────▼────────────┐ ┌───▼─────────────────┐
    │ GestorPacientesDAO.java │ │  AgendaDAO.java     │
    │ (Guardar/Cargar)        │ │ (Guardar/Cargar)    │
    └─────────────────────────┘ └─────────────────────┘
                 │                  │
    ┌────────────▼────────────┐ ┌───▼─────────────────┐
    │  pacientes.dat          │ │   citas.dat         │
    │  (Archivo serializado)  │ │(Archivo serializado)│
    └─────────────────────────┘ └─────────────────────┘
                 │                  │
         ┌───────▼──────────────────▼────────┐
         │  SubMenus.java / Utils/           │
         │  (Interfaz y Controles)           │
         └───────────────────────────────────┘
```

---

## 📦 Descripción de Paquetes y Clases

### **model/** - Modelo de Datos

#### `Paciente.java`

Entidad que representa a un paciente en el sistema.

**Atributos:**

- `id`: Identificador único autogenerado (P001, P002, etc.)
- `nombre`: Nombre del paciente
- `telefono`: Teléfono de contacto
- `email`: Correo electrónico

**Métodos principales:**

- `Paciente(String nombre, String telefono, String email)`: Constructor
- `getId()`, `getNombre()`, `getTelefono()`, `getEmail()`: Getters
- `setNombre()`, `setTelefono()`, `setEmail()`: Setters

---

#### `Agenda.java`

Gestor principal de citas médicas.

**Responsabilidades:**

- Almacenar y gestionar la lista de citas
- Validar disponibilidad de horarios (evitar conflictos)
- Buscar citas por nombre, teléfono o email
- Agendar nuevas citas
- Cancelar citas existentes

**Métodos principales:**

- `agendarCita(Cita nuevaCita)`: Agrega una cita si hay disponibilidad
- `validarDisponibilidadCita(Cita nuevaCita)`: Verifica que no haya conflictos de horarios
- `buscarCitaPorNombre()`, `buscarCitaPorTelefono()`, `buscarCitaPorEmail()`: Búsquedas
- `cancelarCita(int id)`: Elimina una cita
- `getCitaPorId(int id)`: Obtiene una cita específica

---

#### `GestorPacientes.java`

Gestor de operaciones CRUD para pacientes.

**Responsabilidades:**

- Dar de alta nuevos pacientes
- Modificar información de pacientes
- Eliminar pacientes
- Listar pacientes
- Buscar pacientes por ID o nombre

**Métodos principales:**

- `darDeAltaPaciente(Scanner sc)`: Registra un nuevo paciente
- `modificarPaciente(Scanner sc)`: Edita información del paciente
- `eliminarPaciente(Scanner sc)`: Elimina un paciente
- `buscarPacientePorId(String id)`: Busca por ID

---

### **model/cita/** - Gestión de Citas

#### `Cita.java`

Clase abstracta base para todas las citas.

**Atributos:**

- `id`: Identificador único autogenerado
- `paciente`: Referencia a `Paciente`
- `fechaHora`: Fecha y hora de la cita (`LocalDateTime`)
- `idMotivo`: ID del motivo de la cita
- `motivosDisponibles`: HashMap con motivos disponibles

**Métodos principales:**

- `getTipoCita()`: Retorna "MATUTINA" o "VESPERTINA" según la hora
- `getMotivo()`, `getDuracionMinutos()`: Información del motivo
- `terminaEn()`: Calcula la hora de finalización
- `mostrarCita()`: Imprime información formateada

---

#### `CitaMatutina.java`

Extiende de `Cita` para citas antes de las 12:00.

**Motivos disponibles:**

1. Consulta nutricional (60 min)
2. Chequeo de glucosa (40 min)
3. Pesaje mensual (15 min)

**Constructores:**

- `CitaMatutina(Paciente paciente, LocalDateTime fechaHora)`: Con motivo default (1)
- `CitaMatutina(Paciente paciente, LocalDateTime fechaHora, int idMotivo)`: Con motivo específico

---

#### `CitaVespertina.java`

Extiende de `Cita` para citas desde las 12:00 en adelante.

**Motivos disponibles:**

1. Consulta general de psicología (60 min)
2. Crisis nerviosa (90 min)
3. Cita infantil (30 min)

**Constructores:**

- `CitaVespertina(Paciente paciente, LocalDateTime fechaHora)`: Con motivo default (1)
- `CitaVespertina(Paciente paciente, LocalDateTime fechaHora, int idMotivo)`: Con motivo específico

---

#### `MotivoCita.java`

Encapsula la información de un motivo de consulta.

**Atributos:**

- `motivo`: Descripción del motivo
- `duracion`: Duración en minutos

**Métodos:**

- `getMotivo()`: Retorna la descripción
- `getDuracion()`: Retorna la duración en minutos

---

### **dao/** - Acceso a Datos (Data Access Objects)

#### `GestorPacientesDAO.java`

Maneja la persistencia de pacientes mediante serialización.

**Responsabilidades:**

- Guardar lista de pacientes en archivo binario (`pacientes.dat`)
- Cargar lista de pacientes desde archivo

**Métodos:**

- `guardarPaciente(ArrayList<Paciente> pacientes)`: Serializa y guarda pacientes
- `cargarPacientes()`: Deserializa y carga pacientes

---

#### `AgendaDAO.java`

Maneja la persistencia de citas mediante serialización.

**Responsabilidades:**

- Guardar lista de citas en archivo binario (`citas.dat`)
- Cargar lista de citas desde archivo

**Métodos:**

- `guardarCitas(ArrayList<Cita> citas)`: Serializa y guarda citas
- `cargarCitas()`: Deserializa y carga citas

---

### **utils/** - Utilidades e Interfaz

#### `Menu.java`

Utilería para mostrar menús y mensajes en consola.

**Métodos estáticos:**

- `mostrarMensaje(String mensaje, int lineasSeparacion)`: Muestra mensaje con bordes
- `mostrarMenuPrincipal()`: Menú principal del sistema
- `mostrarMensajeError(String mensaje)`: Muestra mensajes de error

---

#### `SubMenus.java`

Maneja la lógica de los submenús de pacientes y citas.

**Métodos principales:**

- `ejecutarMenuPaciente(Scanner sc, GestorPacientes, GestorPacientesDAO)`: Menú CRUD de pacientes
- `ejecutarMenuAgenda(Scanner sc, Agenda, AgendaDAO, GestorPacientes)`: Menú de citas
- `mostrarMenuPaciente()`: Interfaz del menú de pacientes
- `mostrarMenuAgenda()`: Interfaz del menú de citas

---

#### `ControladorCitas.java`

Controlador con toda la lógica de operaciones de citas.

**Métodos principales:**

- `manejarAgregarCita()`: Crea nueva cita (determina si es matutina o vespertina automáticamente)
- `manejarModicarCita()`: Edita cita existente (fecha, hora, nombre, motivo)
- `manejarCancelacionCita()`: Cancela una cita
- `manejarBusquedaCitas()`: Busca citas por paciente
- `manejarMostrarCitas()`: Muestra todas las citas

**Métodos privados de validación:**

- `preguntarFechaYHora()`: Solicita y valida fecha y hora
- `preguntarMotivo()`: Muestra motivos disponibles y valida selección
- `preguntarPaciente()`: Busca y selecciona paciente
- `modificarFecha()`, `modificarHora()`, `modificarNombre()`, `modificarMotivo()`: Métodos para edición

---

### `Main.java`

Punto de entrada de la aplicación.

**Flujo:**

1. Carga datos persistentes (pacientes y citas)
2. Inicializa contadores de IDs
3. Muestra menú principal
4. Gestiona navegación entre submenús
5. Guarda cambios al salir

---

## 🔗 Flujo de Dependencias

```
Main.java
├── GestorPacientesDAO.cargarPacientes()
├── GestorPacientes (inicializa)
├── AgendaDAO.cargarCitas()
├── Agenda (inicializa)
└── Loop principal
    ├── SubMenus.ejecutarMenuPaciente()
    │   └── GestorPacientes (CRUD)
    │       └── GestorPacientesDAO.guardarPaciente()
    │
    └── SubMenus.ejecutarMenuAgenda()
        └── ControladorCitas
            ├── Cita / CitaMatutina / CitaVespertina
            ├── Agenda (validación y gestión)
            └── AgendaDAO.guardarCitas()
```

---

## 🚀 Cómo Usar

### Compilar:

```bash
javac -d out Main.java model/*.java model/cita/*.java dao/*.java utils/*.java
```

### Ejecutar:

```bash
java -cp out Main
```

### Opciones del Menú Principal:

1. **Pacientes**: Gestionar pacientes (alta, modificación, eliminación, listado)
2. **Citas**: Gestionar citas (crear, modificar, cancelar, buscar, listar)
3. **Salir**: Cierra el sistema y guarda cambios

---

## 💾 Almacenamiento de Datos

Los datos se persisten automáticamente en archivos serializados:

- **`data/pacientes.dat`**: Contiene lista de pacientes
- **`data/citas.dat`**: Contiene lista de citas

Estos archivos se generan automáticamente al guardar datos.

---

## 📝 Notas de Diseño

- **Herencia**: `CitaMatutina` y `CitaVespertina` heredan de `Cita`
- **Polimorfismo**: Los diferentes tipos de citas implementan motivos específicos
- **Encapsulación**: Uso de private/public y validaciones en setters
- **Validación**: Verificación de disponibilidad horaria antes de agendar
- **Persistencia**: Serialización Java para guardar/cargar datos

---

## 🎓 Autor

Proyecto desarrollado con fines educativos en Programación Orientada a Objetos (POO) 2025.
