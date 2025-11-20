package model;

// TODO Validación por correo

import java.util.ArrayList;
import java.util.Scanner;

import utils.Menu;

public class GestorPacientes {

    private ArrayList<Paciente> listaPacientes; // inicializa array list para pacientes

    public GestorPacientes() {
        this.listaPacientes = new ArrayList<>();
    }

    // temp
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
        Paciente.setMaxId(maxId);
    }

    public void darDeAltaPaciente(Scanner sc) {
        System.out.print("Ingresa el nombre del paciente: ");
        String nombre = sc.nextLine(); // recibe la entrada del usuario, soporta espacios
        System.out.print("Ingresa el teléfono del paciente: ");
        String telefono = sc.nextLine();
        System.out.print("Ingresa el email del paciente: ");
        String email = sc.nextLine(); 

        Paciente nuevoPaciente = new Paciente(nombre, telefono, email); // Constructor crea objeto nuevo P
        listaPacientes.add(nuevoPaciente); // agrega al paciente al array

        System.out.println(" ✅ ¡Paciente '" + nombre + "' agregado con éxito con el ID: " + nuevoPaciente.getId() + "!");
    }

    public void mostrarListaPacientes() {
        if (listaPacientes.isEmpty()) { // valida si el array esta vacio e imprime error en caso de
            Menu.mostrarMensajeError(" ⚠️ No hay pacientes registrados");
            return; // regresa al menu después de imprimir el error
        }
//        System.out.println("--- LISTA DE PACIENTES ---");
        for (Paciente tempPaciente : listaPacientes) { // for each paciente en el array y almacena en temp pac
            System.out.println(tempPaciente);
        }
    }

    public void modificarPaciente(Scanner sc) {
        if (listaPacientes.isEmpty()) { // valida si la lista esta vacia
            Menu.mostrarMensajeError(" ⚠️ No hay pacientes para modificar"); // imprime error
            return; // regresa al menu
        }

        System.out.print("Ingresa el ID o el Nombre del paciente a modificar: ");
        String terminoBusqueda = sc.nextLine();

        ArrayList<Paciente> pacientesEncontrados = buscarPacientes(terminoBusqueda);
        // manda llamar para buscar pacientes y regresa la lista coincidencias

        if (pacientesEncontrados.isEmpty()) { // valida si el resultado esta vacio
            Menu.mostrarMensajeError(" ❌ Error: No se encontro ningun paciente con el ID o Nombre" + terminoBusqueda);
        } else if (pacientesEncontrados.size() == 1) { // valida si se encontró un solo resultado
            Paciente pacienteAModificar = pacientesEncontrados.get(0); // obtienes el resultado que se encontro
            System.out.println(" ✅ Paciente encontrado:");
            System.out.println(pacienteAModificar); // imprime la información del paciente encontrado
            procederConModificacion(pacienteAModificar, sc); // llama al metodo para la modificacion
        } else {
            System.out.println("Se encontraron " + pacientesEncontrados.size()
                    + " pacientes con ese nombre. ¿Cuál deseas modificar?");
            for (Paciente pac : pacientesEncontrados) {
                System.out.println(pac); // si hubo varios resultados los muestra
            }

            System.out.print("Por favor, ingresa el ID exacto (ej. P002) del paciente: ");
            String idExacto = sc.nextLine(); // recibe el id exacto del paciente
            Paciente pacienteAModificar = buscarPacientePorId(idExacto); // llama metodo buscar para empatarlo

            if (pacienteAModificar == null) { // valida que exista
                Menu.mostrarMensajeError(" ❌ Error: ID no valido o no encontrado en la lista de duplicados");
            } else {
                procederConModificacion(pacienteAModificar, sc); // si es valido procede con la modificacion
            }
        }
    }

    private void procederConModificacion(Paciente paciente, Scanner sc) { // helper para las modificaciones
                                                                                 // despues de validar
        System.out.println("Modificando a: " + paciente);

        System.out.print("Ingresa el nuevo nombre (deja en blanco para no cambiar [" + paciente.getNombre() + "]): ");
        String nuevoNombre = sc.nextLine();
        if (!nuevoNombre.isEmpty()) {
            paciente.setNombre(nuevoNombre);
        }

        System.out
                .print("Ingresa el nuevo teléfono (deja en blanco para no cambiar [" + paciente.getTelefono() + "]): ");
        String nuevoTelefono = sc.nextLine();
        if (!nuevoTelefono.isEmpty()) {
            paciente.setTelefono(nuevoTelefono);
        }

        System.out.print("Ingresa el nuevo email (deja en blanco para no cambiar [" + paciente.getEmail() + "]): ");
        String nuevoEmail = sc.nextLine();
        if (!nuevoEmail.isEmpty()) {
            paciente.setEmail(nuevoEmail);
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
            Menu.mostrarMensajeError(" ❌ Error: No se encontro ningun paciente con el ID o Nombre" + terminoBusqueda);
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
