package dao;

import model.Paciente;

import java.io.*;
import java.util.ArrayList;

public class GestorPacientesDAO {
    private final String RUTA_ARCHIVO = "data/pacientes.dat";
    private final String DIRECTORIO = "data";

    public boolean guardarPaciente(ArrayList<Paciente> pacientes) {
        File directorio = new File(DIRECTORIO);

        if (!directorio.exists()) {
            if (!directorio.mkdirs()) {
                System.err.println("❌ Error: No se pudo crear el directorio de datos: " + DIRECTORIO);
                return false;
            }
        }

        try (
                FileOutputStream fileOut = new FileOutputStream(RUTA_ARCHIVO);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        ) {
            objectOut.writeObject(pacientes);
            System.out.println("✅ Datos de Pacientes guardados correctamente en " + RUTA_ARCHIVO);
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error al guardar los pacientes: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Paciente> cargarPacientes() {
        File directorio = new File(DIRECTORIO);

        if (!directorio.exists()) {
            if (!directorio.mkdirs()) {
                System.err.println("❌ Error: No se pudo crear el directorio de datos: " + DIRECTORIO);
                return null;
            }
        }
        
        ArrayList<Paciente> pacientes = new ArrayList<>();
        try (
                FileInputStream fileIn = new FileInputStream(RUTA_ARCHIVO);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        ) {
            pacientes = (ArrayList<Paciente>) objectIn.readObject();
            System.out.println("✅ Datos de pacientes cargados correctamente desde " + RUTA_ARCHIVO);
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Archivo de datos no encontrado. Se inicia con lista vacía de pacientes.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error al cargar los pacientes: " + e.getMessage());
            e.printStackTrace();
        }
        return pacientes;
    }
}
