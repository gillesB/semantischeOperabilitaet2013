package business.model.ontology;

public enum KoerperlicheEinschraenkungen {
	BEINBEREICH("Beinbereich"), ARMBEREICH("Armbereich"), HOEHENANGST("Hoehenangst");
	
	private String name;
	 
	 private KoerperlicheEinschraenkungen(String n) {
	   name = n;
	 }
	 
	 public String getName() {
	   return name;
	 }

}
