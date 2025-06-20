package sub_procesos;

public class Proveedor {
    private String id_proveedor, nombre, telefono;

	public String getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(String id_proveedor) {
		this.id_proveedor = id_proveedor;
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

	public Proveedor(String id_proveedor, String nombre, String telefono) {
		super();
		this.id_proveedor = id_proveedor;
		this.nombre = nombre;
		this.telefono = telefono;
	}
    
    
	
}
