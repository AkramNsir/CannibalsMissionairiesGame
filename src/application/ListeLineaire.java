package application;

import java.util.ArrayList;
import java.util.List;

public class ListeLineaire implements Ferme {
    private List<Etat> liste;

    public ListeLineaire() {
        liste = new ArrayList<>();
    }

    @Override
    public boolean non_vide() {
        return !liste.isEmpty();
    }

    @Override
    public void inserer(Etat e) {
        liste.add(e);
    }

    @Override
    public void detruire() {
        liste.clear();
    }

    @Override
    public boolean recherche(Etat e) {
        return liste.contains(e);
    }
}
