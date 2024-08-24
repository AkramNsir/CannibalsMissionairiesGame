package application;

import java.util.LinkedList;
import java.util.Queue;

public class FIFO implements Ouvert {
    private Queue<Etat> file;

    public FIFO() {
        file = new LinkedList<>();
    }

    @Override
    public boolean non_vide() {
        return !file.isEmpty();
    }

    @Override
    public void inserer(Etat e) {
        file.add(e);
    }

    @Override
    public Etat extraire() {
        return file.poll();
    }

    @Override
    public void detruire() {
        file.clear();
    }

    @Override
    public boolean recherche(Etat e) {
        return file.contains(e);
    }
}

