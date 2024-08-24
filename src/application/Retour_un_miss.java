package application;

public class Retour_un_miss implements Operation{

	@Override
    public Etat action(Etat etat) {
        Etat_can_miss etatCourant = (Etat_can_miss) etat;
        int varLoc1 = etatCourant.getNbre_missionnaires() + 1;
        int varLoc2 = etatCourant.getNbre_cannibales();

        if (((etatCourant.isBateau() == false) && (1 <= (3 - etatCourant.getNbre_missionnaires()))) &&
                ((varLoc1 == 0) || (varLoc1 >= varLoc2)) && (((3-varLoc1)==0) || ((3-varLoc1) >= (3-varLoc2)))) {
            return new Etat_can_miss(etatCourant.getNbre_cannibales(), etatCourant.getNbre_missionnaires() + 1, true);
        } else {
            return null;
        }
    }
}
