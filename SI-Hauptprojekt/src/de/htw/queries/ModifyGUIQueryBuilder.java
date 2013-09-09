package de.htw.queries;

import java.util.HashSet;
import java.util.Set;

import business.model.ontology.KoerperlicheEinschraenkungen;
import business.model.ontology.Personen;

public class ModifyGUIQueryBuilder {
	
    private boolean wantsToPay = false;
    private KoerperlicheEinschraenkungen[] einschraenkungen    = new KoerperlicheEinschraenkungen[0];
    
    public Set<Personen> execute() {
    	HashSet<Personen> personen = new HashSet<Personen>();
    	
    	if (wantsToPay) {
            ModifyGUIQueries.queryPrice(personen);
        }

        if (einschraenkungen.length > 0) {
            ModifyGUIQueries.queryKoerperlicheEinschraenkungen(personen);
        }
    	
    	return personen;
    }  
    
    public boolean isWantsToPay() {
		return wantsToPay;
	}

	public void setWantsToPay(boolean wantsToPay) {
		this.wantsToPay = wantsToPay;
	}

	public KoerperlicheEinschraenkungen[] getEinschraenkungen() {
        return einschraenkungen;
    }

    public void setEinschraenkungen(
            KoerperlicheEinschraenkungen[] einschraenkungen) {
        this.einschraenkungen = einschraenkungen;
    }

}
