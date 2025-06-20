package procesos;

import java.util.ArrayList;

import sub_procesos.Productos;
import sub_procesos.Proveedor;

public class Detalle_pedidos {
private ArrayList<Pedidos>id_pedido;
private int id_detalle;
private int cantidad, temperatura;
private double subtotal;
private String tamaño_vaso;
private String tipo_leche;
private int Catidad_azucar;
private Productos id_producto;
public Detalle_pedidos(ArrayList<Pedidos> id_pedido, int id_detalle, int cantidad, int temperatura, double subtotal,
		String tamaño_vaso, String tipo_leche, int catidad_azucar, Productos id_producto) {
	super();
	this.id_pedido = id_pedido;
	this.id_detalle = id_detalle;
	this.cantidad = cantidad;
	this.temperatura = temperatura;
	this.subtotal = subtotal;
	this.tamaño_vaso = tamaño_vaso;
	this.tipo_leche = tipo_leche;
	Catidad_azucar = catidad_azucar;
	this.id_producto = id_producto;
}
public ArrayList<Pedidos> getId_pedido() {
	return id_pedido;
}
public void setId_pedido(ArrayList<Pedidos> id_pedido) {
	this.id_pedido = id_pedido;
}
public int getId_detalle() {
	return id_detalle;
}
public void setId_detalle(int id_detalle) {
	this.id_detalle = id_detalle;
}
public int getCantidad() {
	return cantidad;
}
public void setCantidad(int cantidad) {
	this.cantidad = cantidad;
}
public int getTemperatura() {
	return temperatura;
}
public void setTemperatura(int temperatura) {
	this.temperatura = temperatura;
}
public double getSubtotal() {
	return subtotal;
}
public void setSubtotal(double subtotal) {
	this.subtotal = subtotal;
}
public String getTamaño_vaso() {
	return tamaño_vaso;
}
public void setTamaño_vaso(String tamaño_vaso) {
	this.tamaño_vaso = tamaño_vaso;
}
public String getTipo_leche() {
	return tipo_leche;
}
public void setTipo_leche(String tipo_leche) {
	this.tipo_leche = tipo_leche;
}
public int getCatidad_azucar() {
	return Catidad_azucar;
}
public void setCatidad_azucar(int catidad_azucar) {
	Catidad_azucar = catidad_azucar;
}
public Productos getId_producto() {
	return id_producto;
}
public void setId_producto(Productos id_producto) {
	this.id_producto = id_producto;
}

}
