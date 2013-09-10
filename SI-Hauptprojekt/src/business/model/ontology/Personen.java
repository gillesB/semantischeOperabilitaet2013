package business.model.ontology;

public enum Personen {
	EINGESCHRAENKT("eingeschraenkte_Person"), ZAHLEND("zahlende_Person");
	
	private String name;
	 
	 private Personen(String n) {
	   name = n;
	 }
	 
	 public String getName() {
	   return name;
	 }
}
