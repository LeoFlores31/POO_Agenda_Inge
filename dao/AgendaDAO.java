package dao;

import model.cita.Cita;

import java.io.*;
import java.util.ArrayList;

public class AgendaDAO {
    private final String RUTA_ARCHIVO = "data/citas.dat";
    private final String DIRECTORIO = "data";

    public boolean guardarCitas(ArrayList<Cita> citas) {
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
            objectOut.writeObject(citas);
            System.out.println("✅ Datos de citas guardados correctamente en " + RUTA_ARCHIVO);
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error al guardar las citas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Cita> cargarCitas() {
        File directorio = new File(DIRECTORIO);

        if (!directorio.exists()) {
            if (!directorio.mkdirs()) {
                System.err.println("❌ Error: No se pudo crear el directorio de datos: " + DIRECTORIO);
                return null;
            }
        }

        ArrayList<Cita> citas = new ArrayList<>();
        try (
                FileInputStream fileIn = new FileInputStream(RUTA_ARCHIVO);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        ) {
            citas = (ArrayList<Cita>) objectIn.readObject();
            System.out.println("✅ Datos de citas cargados correctamente desde " + RUTA_ARCHIVO);
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ Archivo de datos no encontrado. Se inicia con lista vacía de citas.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error al cargar las citas: " + e.getMessage());
            e.printStackTrace();
        }
        return citas;
    }
}
