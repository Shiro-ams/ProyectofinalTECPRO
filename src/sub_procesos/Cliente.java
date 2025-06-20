package sub_procesos;

public class Cliente {
	private int id_cliente;
	private String nom,telefono;
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Cliente(int id_cliente, String nom, String telefono) {
		super();
		this.id_cliente = id_cliente;
		this.nom = nom;
		this.telefono = telefono;
	}
	
	
}
