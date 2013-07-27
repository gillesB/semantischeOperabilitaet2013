package business.model.ontology;

public enum Ziele {

	FITNESS("Fitness"), FREIZEITVERGUEGEN("Freizeitvergnuegen"), SELF_DEFENSE("SelfDefense"), SOZIALKONTAKTE("Sozialkontakte"), WETTBEWERB("Wettbewerb");
	
	private String name;
	 
	 private Ziele(String n) {
	   name = n;
	 }
	 
	 public String getName() {
	   return name;
	 }
}
