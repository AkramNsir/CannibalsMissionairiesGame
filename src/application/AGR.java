package application;

public class AGR {
    private Etat etatInitial;
    private Ferme ferme;
    private Ouvert ouvert;
    private Operation[] operations;

    public AGR(Etat etatInitial, Ferme ferme, Ouvert ouvert, Operation[] operations) {
        this.etatInitial = etatInitial;
        this.ferme = ferme;
        this.ouvert = ouvert;
        this.operations = operations;
    }

    Etat Explorer() {
        ouvert.inserer(etatInitial);

        while (ouvert.non_vide()) {
            Etat etatActuel = ouvert.extraire();
            ferme.inserer(etatActuel);

            if (etatActuel.test_but()) {
                return etatActuel;
            }
            
            for (Operation operation : operations) {
                Etat nouvelEtat = operation.action(etatActuel);
                if (nouvelEtat != null && !ferme.recherche(nouvelEtat)) {
                    nouvelEtat.setPere(etatActuel); // Fixer l'état actuel comme parent du nouvel état
                    ouvert.inserer(nouvelEtat);
                }
            }
        }

        return null;
    }

    String chemin() {
        StringBuilder sb = new StringBuilder();
        Etat etatActuel = Explorer();
        int count = 0;

        if (etatActuel != null) {
            while (etatActuel != null) {
                sb.insert(0, etatActuel.toString() + "\n");
                etatActuel = etatActuel.getPere();
                count++;
            }
            System.out.println("Solution en "+count+"étapes :");
            return sb.toString();
        } else {
            return "Aucune solution trouvée.";
        }
    }

}
