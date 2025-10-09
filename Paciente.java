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

    public static void main(String[] args) {
        Paciente paciente1 = new Paciente();
        paciente1.setId(1);
        paciente1.setNombre("Leonel F");
        paciente1.setTelefono("3334564092");

        System.out.println("Paciente: " + paciente1.getNombre() + ", Telefono: " + paciente1.getTelefono());
    }
}
