package model;//TODO Investigar que son los métodos estáticos (atributo estático)

public class Paciente {

    private static int totalPacientes = 0;

    private String id;
    private String nombre;
    private String telefono;
    private String email;

    public Paciente() {
        this.id = "P" + String.format("%03d", ++totalPacientes);
        this.nombre = "";
        this.telefono = "";
        this.email = "";
    }

    public Paciente(String nombre, String telefono, String email) {
        this.id = "P" + String.format("%03d", ++totalPacientes);
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaciente() {
        return "ID: " + id + " | Nombre: " + nombre + " | Teléfono: " + telefono + " | Email: " + email;
    }

    public String getPacientePorID(String id) {
        if (this.id.equals(id)) {
            return getPaciente();
        } else {
            return "Paciente no encontrado con ID: " + id;
        }
    }

    public String getPacientePorTelefono(String telefono) {
        if (this.telefono.equals(telefono)) {
            return getPaciente();
        } else {
            return "Paciente no encontrado con teléfono: " + telefono;
        }
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

// get paciente (sobrecarga de métodos por id o por teléfono)
// get paciente por default me pida el id o por parámetro con el id o por
// teléfono
