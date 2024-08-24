package application;

public class Etat_can_miss extends Etat implements Cloneable {
	private int nbre_cannibales;
	private int nbre_missionnaires;
	private boolean bateau;
	
	public Etat_can_miss(int nbre_cannibales, int nbre_missionnaires, boolean bateau) {
		this.nbre_cannibales = nbre_cannibales;
		this.nbre_missionnaires = nbre_missionnaires;
		this.bateau = bateau;
	}
	
	@Override
	boolean test_but() {
		return (nbre_cannibales == 0 && nbre_missionnaires == 0 && bateau == false);
	}
	
	@Override
    public String toString() {
        return "Etat: Cannibales=" + nbre_cannibales + ", Missionnaires=" + nbre_missionnaires + ", Bateau=" + bateau;
    }
	
	public int getNbre_cannibales() {
		return nbre_cannibales;
	}
	
	public int getNbre_missionnaires() {
		return nbre_missionnaires;
	}
	
	public boolean isBateau() {
		return bateau;
	}
	
	public void setNbre_cannibales(int nbre_cannibales) {
		this.nbre_cannibales = nbre_cannibales;
	}
	
	public void setNbre_missionnaires(int nbre_missionnaires) {
		this.nbre_missionnaires = nbre_missionnaires;
	}
	
	public void setBateau(boolean bateau) {
		this.bateau = bateau;
	}	
}
