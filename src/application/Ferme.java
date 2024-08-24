package application;

public interface Ferme {
	boolean non_vide();
	void inserer(Etat e);
	void detruire();
	boolean recherche(Etat e);
}
