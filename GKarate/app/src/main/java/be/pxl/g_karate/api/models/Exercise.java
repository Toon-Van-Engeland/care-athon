package be.pxl.g_karate.api.models;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    private int _id;
    private List<Integer> handMovementsLeft;
    private List<Integer> handMovementsRight;

    public Exercise(int id) {
        this._id = id;
        handMovementsLeft = new ArrayList<>();
        handMovementsRight = new ArrayList<>();
    }

    public int getId() {
        return this._id;
    }

    public void addHandMovementLeft(int placement) {
        handMovementsLeft.add(placement);
    }

    public void addHandMovementRight(int placement) {
        handMovementsRight.add(placement);
    }

    public List<Integer> getHandMovementsLeft() {
        return this.handMovementsLeft;
    }

    public List<Integer> getHandMovementsRight() {
        return this.handMovementsRight;
    }
}
