package business.model;

public class Ort {
	
	private int idOrt;
	private String name;
	private boolean innen;
	
	public Ort(){
		
	}
	
	public Ort(int id, String name, boolean innen){
		this.idOrt = id;
		this.name = name;
		this.innen = innen;
	}
	
	public int getIdOrt() {
		return idOrt;
	}
	public String getName() {
		return name;
	}
	public boolean isInnen() {
		return innen;
	}

}
