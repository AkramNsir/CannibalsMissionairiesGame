package application;

public interface Ouvert {
	boolean non_vide();
	void inserer(Etat e);
	Etat extraire();
	void detruire();
	boolean recherche(Etat e);
}
