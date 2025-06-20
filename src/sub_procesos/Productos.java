package sub_procesos;

import java.util.ArrayList;

public class Productos {
    private int id_producto, stock;
    private double precio;
    private String nom, tipo;
    private ArrayList<Proveedor> id_proveedorr;

    // Constructor
    public Productos(int id_producto, int stock, double precio, String nom, String tipo,
                     ArrayList<Proveedor> id_proveedorr) {
        this.id_producto = id_producto;
        this.stock = stock;
        this.precio = precio;
        this.nom = nom;
        this.tipo = tipo;
        this.id_proveedorr = id_proveedorr;
    }

    // Getters y Setters
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Proveedor> getId_proveedorr() {
        return id_proveedorr;
    }

    public void setId_proveedorr(ArrayList<Proveedor> id_proveedorr) {
        this.id_proveedorr = id_proveedorr;
    }

    // Alias para usar "tipo" como "categoría"
    public String getCategoria() {
        return tipo;
    }

    // Alias para precio de compra
    public double getPreC() {
        return precio;
    }

    // Alias para cantidad
    public int getCant() {
        return stock;
    }

    // Alias para código (id)
    public String getCod() {
        return String.valueOf(id_producto);
    }
}

