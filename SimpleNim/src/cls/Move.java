package cls;

import java.io.Serializable;
import java.*;

public class Move implements Serializable {


    // Relates move position to marbles remaining
    public int marbles;

    // Probability of choosing A=1 B=2 C=3
    public double probA;
    public double probB;
    public double probC;
    public int chosenMove;

    Move(int marb, double A, double B, double C) {
        this.marbles = marb;
        this.probA = A;
        this.probB = B;
        this.probC = C;
    }

    public int getMarbles() {
        return marbles;
    }
    public void setMarbles(int marbles) {
        this.marbles = marbles;
    }
    public double getProbA() {
        return probA;
    }
    public void setProbA(double probA) {
        this.probA = probA;
    }
    public double getProbB() {
        return probB;
    }
    public void setProbB(double probB) {
        this.probB = probB;
    }
    public double getProbC() {
        return probC;
    }
    public void setProbC(double probC) {
        this.probC = probC;
    }

    public int getChosenMove() {
        return chosenMove;
    }
    public void setChosenMove(int chosenMove) {
        this.chosenMove = chosenMove;
    }

    public Move newMove(int marb, double A, double B, double C) {


        return newMove(marb, A, B, C);
    }

    @Override
    public String toString() {
        return new StringBuffer(" Marbles: ").append(this.marbles + 1)
                .append(" ProbA: ").append(this.probA).append(" ProbB: ").append(this.probB).append(" ProbC: ").append(this.probC).toString();
    }

    // public Move rewardMove(move myMove, double nA, double nB, double nC) {

    // }



}