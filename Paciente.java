public class Paciente {
    private int id;
    private String nombre;
    private String telefono;
    private String email;

    public Paciente() {
        this.id = 0;
        this.nombre = "";
        this.telefono = "";
        this.email = "";
    }

    public Paciente(int id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPaciente(int id) {
        if (this.id == id) {
            return getPaciente();
        } else {
            return "Paciente no encontrado con ID: " + id;
        }
    }

    public String getPaciente(String telefono) {
        if (this.telefono.equals(telefono)) {
            return getPaciente();
        } else {
            return "Paciente no encontrado con teléfono: " + telefono;
        }
    }
}

// get paciente (sobrecarga de métodos por id o por teléfono)
// get paciente por default me pida el id o por parámetro con el id o por
// teléfono
