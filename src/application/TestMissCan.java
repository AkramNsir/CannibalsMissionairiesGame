package application;

public class TestMissCan {
	public static void main(String [] args) {
		Etat etatInitial = new Etat_can_miss(3,3,true);
		Ferme fermeMissCan = new ListeLineaire();
		Ouvert ouvertMissCan = new FIFO();
		Operation[] operations_can_miss = {new Depart_un_can(), new Depart_deux_can(), new Depart_un_can_un_miss(),
				new Depart_deux_miss(), new Depart_un_miss(), new Retour_un_can(), new Retour_deux_can(),
				new Retour_un_can_un_miss(), new Retour_deux_miss(), new Retour_un_miss()};
		AGR agr = new AGR(etatInitial, fermeMissCan, ouvertMissCan, operations_can_miss);
		Etat solution = agr.Explorer();
		System.out.println("Solution: "+ solution);
		
		if (solution != null) {
            System.out.println("Solution trouvée:");
            System.out.println(agr.chemin());
        } else {
            System.out.println("Aucune solution trouvée.");
        }   
	}

}
