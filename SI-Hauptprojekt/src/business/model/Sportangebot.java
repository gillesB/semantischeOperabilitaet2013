package business.model;

public class Sportangebot implements Comparable<Sportangebot>{
	
	public final static String TABLE = "Sportangebot";
	public final static String ONTO_CLASS = "Sport";
	public final static String ID = "Sportangebot.idSportangebot";
	public final static String NAME = "Sportangebot.name";
	
	private int idSportangebot;
	private String name;	
	
	public Sportangebot(int idSportangebot, String name) {
		super();
		this.idSportangebot = idSportangebot;
		this.name = name;
	}
	public int getIdSportangebot() {
		return idSportangebot;
	}
	public String getName() {
		return name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idSportangebot;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sportangebot other = (Sportangebot) obj;
		if (idSportangebot != other.idSportangebot)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return name;
	}
	@Override
	public int compareTo(Sportangebot o) {
		return name.compareTo(o.getName());
	}
	
	
	
	

}
