package procesos;

import java.util.ArrayList;
import sub_procesos.Productos;

public class Stock {
    private ArrayList<Productos> lista;

    public Stock() {
        lista = new ArrayList<>();
    }

    public void Agregar(Productos p) {
        lista.add(p);
    }

    public int Tamaño() {
        return lista.size();
    }

    public Productos Obtener(int i) {
        return lista.get(i);
    }

    public ArrayList<Productos> BuscarPorCategoria(String categoria) {
        ArrayList<Productos> resultado = new ArrayList<>();
        for (Productos p : lista) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public ArrayList<Productos> BuscarPorNombre(String nombre) {
        ArrayList<Productos> resultado = new ArrayList<>();
        for (Productos p : lista) {
            if (p.getNom().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}

