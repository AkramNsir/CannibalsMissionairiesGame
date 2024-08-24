package application;

public abstract class Etat {
	private Etat pere;
	private Operation operation;
	abstract boolean test_but();
	
	public Operation getOperation() {
		return operation;
	}
	public Etat getPere() {
		return pere;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public void setPere(Etat pere) {
		this.pere = pere;
	}
}
