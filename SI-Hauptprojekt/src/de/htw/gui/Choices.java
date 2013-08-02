package de.htw.gui;

public enum Choices {
	JA("Ja", "True"), NEIN("Nein", "False"), EGAL("Egal", "Thing");

	private String name;
	private String ontoEquivalent;

	private Choices(String n, String ontoEquivalent) {
		name = n;
		this.ontoEquivalent = ontoEquivalent;
	}

	public String getName() {
		return name;
	}

	public String getOntoEquivalent() {
		return ontoEquivalent;
	}
	
}
