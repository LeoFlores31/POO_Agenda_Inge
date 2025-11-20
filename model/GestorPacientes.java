package model;

import java.util.ArrayList;
import java.util.Scanner;
import utils.Menu;

public class GestorPacientes {

    private ArrayList<Paciente> listaPacientes; 

    public GestorPacientes() {
        this.listaPacientes = new ArrayList<>();
    }

    public void agregarPaciente(Paciente paciente) {
        listaPacientes.add(paciente);
    }

    public ArrayList<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(ArrayList<Paciente> pacientes) {
        this.listaPacientes = pacientes;
    }

    public void inicializarContador(ArrayList<Paciente> pacientes) {
        int maxId = 0;

        for (Paciente p : pacientes) {
            String id = p.getId();
            if (id != null && id.length() > 1) {
                String numString = id.substring(1);
                try {
                    int idNumerico = Integer.parseInt(numString);
                    if (idNumerico > maxId) {
                        maxId = idNumerico;
                    }
                } catch (NumberFormatException e) {
                    System.err.println(" ❌ Error al parsear ID: " + id);
                }
            }
        }
        Paciente.setMaxId(maxId);
    }

    private boolean existeEmail(String email) { //recibe como parametro el email a verificar
        for (Paciente p : listaPacientes) { //for each para todos los pacientes de la lista
            if (p.getEmail().equalsIgnoreCase(email)) { //compara el email del paciente con el email recibido
                return true; // si lo encuentra regresa true
            }
        }
        return false; //si no lo encuentra regresa false
    }

    public void darDeAltaPaciente(Scanner sc) {
        System.out.print("Ingresa el nombre del paciente: ");
        String nombre = sc.nextLine(); 
        System.out.print("Ingresa el teléfono del paciente: ");
        String telefono = sc.nextLine();
        
        String email = ""; //variable donde se almacenara el email
        boolean emailValido = false; //bandera en false para hacer la validacion primero

        while (!emailValido) { //mientras la bandera sea false permite ingresar el email
            System.out.print("Ingresa el email del paciente: ");
            email = sc.nextLine(); 

            if (existeEmail(email)) { //valida el ingreso del correo y si existe imprime el error
                Menu.mostrarMensajeError(" ❌ Error: El correo '" + email + "' ya está registrado. Intenta con otro.");
            } else {
                emailValido = true; //si no existe rompe el bucle y permite avanzar
            }
        }

        Paciente nuevoPaciente = new Paciente(nombre, telefono, email); 
        listaPacientes.add(nuevoPaciente); 

        System.out.println(" ✅ ¡Paciente '" + nombre + "' agregado con éxito con el ID: " + nuevoPaciente.getId() + "!");
    }

    public void mostrarListaPacientes() {
        if (listaPacientes.isEmpty()) { 
            Menu.mostrarMensajeError(" ⚠️ No hay pacientes registrados");
            return; 
        }
        for (Paciente tempPaciente : listaPacientes) { 
            System.out.println(tempPaciente);
        }
    }

    public void modificarPaciente(Scanner sc) {
        if (listaPacientes.isEmpty()) { 
            Menu.mostrarMensajeError(" ⚠️ No hay pacientes para modificar"); 
            return; 
        }

        System.out.print("Ingresa el ID o el Nombre del paciente a modificar: ");
        String terminoBusqueda = sc.nextLine();

        ArrayList<Paciente> pacientesEncontrados = buscarPacientes(terminoBusqueda);

        if (pacientesEncontrados.isEmpty()) { 
            Menu.mostrarMensajeError(" ❌ Error: No se encontro ningun paciente con el ID o Nombre: " + terminoBusqueda);
        } else if (pacientesEncontrados.size() == 1) { 
            Paciente pacienteAModificar = pacientesEncontrados.get(0); 
            System.out.println(" ✅ Paciente encontrado:");
            System.out.println(pacienteAModificar); 
            procederConModificacion(pacienteAModificar, sc); 
        } else {
            System.out.println("Se encontraron " + pacientesEncontrados.size()
                    + " pacientes con ese nombre. ¿Cuál deseas modificar?");
            for (Paciente pac : pacientesEncontrados) {
                System.out.println(pac); 
            }

            System.out.print("Por favor, ingresa el ID exacto (ej. P002) del paciente: ");
            String idExacto = sc.nextLine(); 
            Paciente pacienteAModificar = buscarPacientePorId(idExacto); 

            if (pacienteAModificar == null) { 
                Menu.mostrarMensajeError(" ❌ Error: ID no valido o no encontrado en la lista de duplicados");
            } else {
                procederConModificacion(pacienteAModificar, sc); 
            }
        }
    }

    private void procederConModificacion(Paciente paciente, Scanner sc) { 
        System.out.println("Modificando a: " + paciente);

        System.out.print("Ingresa el nuevo nombre (deja en blanco para no cambiar [" + paciente.getNombre() + "]): ");
        String nuevoNombre = sc.nextLine();
        if (!nuevoNombre.isEmpty()) {
            paciente.setNombre(nuevoNombre);
        }

        System.out.print("Ingresa el nuevo teléfono (deja en blanco para no cambiar [" + paciente.getTelefono() + "]): ");
        String nuevoTelefono = sc.nextLine();
        if (!nuevoTelefono.isEmpty()) {
            paciente.setTelefono(nuevoTelefono);
        }

        System.out.print("Ingresa el nuevo email (deja en blanco para no cambiar [" + paciente.getEmail() + "]): ");
        String nuevoEmail = sc.nextLine();
        
        if (!nuevoEmail.isEmpty()) {
            if (!nuevoEmail.equalsIgnoreCase(paciente.getEmail()) && existeEmail(nuevoEmail)) { //valida si exite otro paciente con este email
                 Menu.mostrarMensajeError(" ❌ Error: El correo '" + nuevoEmail + "' ya pertenece a otro paciente."); //imprime error si lo encuentra
            } else {
                paciente.setEmail(nuevoEmail); //si no lo encuentra actualiza el email
            }
        }

        System.out.println(" ✅ ¡Paciente actualizado con éxito!");
        System.out.println(paciente);
    }

    public void eliminarPaciente(Scanner sc) {
        if (listaPacientes.isEmpty()) {
            Menu.mostrarMensajeError(" ⚠️ No hay pacientes para eliminar");
            return;
        }

        System.out.print("Ingresa el ID o el Nombre del paciente a eliminar: ");
        String terminoBusqueda = sc.nextLine();

        ArrayList<Paciente> pacientesEncontrados = buscarPacientes(terminoBusqueda);

        if (pacientesEncontrados.isEmpty()) {
            Menu.mostrarMensajeError(" ❌ Error: No se encontro ningun paciente con el ID o Nombre: " + terminoBusqueda);
        } else if (pacientesEncontrados.size() == 1) {
            Paciente pacienteAEliminar = pacientesEncontrados.get(0);
            String nombreEliminado = pacienteAEliminar.getNombre();

            listaPacientes.remove(pacienteAEliminar);
            System.out.println(" ✅ ¡Paciente '" + nombreEliminado + "' eliminado con éxito!");

        } else {
            System.out.println("Se encontraron " + pacientesEncontrados.size()
                    + " pacientes con ese nombre. ¿Cuál deseas eliminar?");
            for (Paciente pac : pacientesEncontrados) {
                System.out.println(pac);
            }

            System.out.print("Por favor, ingresa el ID exacto (ej. P002) del paciente: ");
            String idExacto = sc.nextLine();
            Paciente pacienteAEliminar = buscarPacientePorId(idExacto);

            if (pacienteAEliminar == null) {
                Menu.mostrarMensajeError(" ❌ Error: ID no valido o no encontrado en la lista de duplicados");
            } else {
                String nombreEliminado = pacienteAEliminar.getNombre();
                listaPacientes.remove(pacienteAEliminar);
                System.out.println(" ✅ ¡Paciente '" + nombreEliminado + "' eliminado con éxito!");
            }
        }
    }

    private ArrayList<Paciente> buscarPacientes(String terminoBusqueda) {
        ArrayList<Paciente> encontrados = new ArrayList<>();
        for (Paciente pac : listaPacientes) {
            if (pac.getId().equalsIgnoreCase(terminoBusqueda) ||
                    pac.getNombre().equalsIgnoreCase(terminoBusqueda)) {
                encontrados.add(pac);
            }
        }
        return encontrados;
    }

    public Paciente buscarPacientePorId(String id) {
        for (Paciente pac : listaPacientes) {
            if (pac.getId().equalsIgnoreCase(id)) {
                return pac;
            }
        }
        return null;
    }
}