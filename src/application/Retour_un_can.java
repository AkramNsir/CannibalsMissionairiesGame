package application;

public class Retour_un_can implements Operation {

	@Override
    public Etat action(Etat etat) {
        Etat_can_miss etatCourant = (Etat_can_miss) etat;
        int varLoc1 = etatCourant.getNbre_missionnaires();
        int varLoc2 = etatCourant.getNbre_cannibales() + 1;

        if (((etatCourant.isBateau() == false) && (1 <= (3 - etatCourant.getNbre_cannibales()))) &&
                ((varLoc1 == 0) || (varLoc1 >= varLoc2)) && (((3-varLoc1)==0) || ((3-varLoc1) >= (3-varLoc2)))) {
            return new Etat_can_miss(etatCourant.getNbre_cannibales() + 1, etatCourant.getNbre_missionnaires(), true);
        } else {
            return null;
        }
    }
}
