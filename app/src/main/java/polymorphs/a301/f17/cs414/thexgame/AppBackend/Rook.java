package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/22/17.
 */

class Rook extends Piece {

    public Rook(int myRow,int myCol,boolean available,Color color){
        super(myRow,myCol,available,color);
    }


    @Override
    /**
     * @return true if valid Rook move, false otherwise
     */
    public boolean isValidMove(int toRow,int toCol){
        if(!super.isValidMove(toRow,toCol)){
            return false;
        }

        if(super.getRow() == toRow || super.getCol() == toCol){
            return true;
        }

        return false;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Rook)) return false;
        Rook otherRook = (Rook) o;
        return (super.getRow() == otherRook.getRow() && super.getCol() == otherRook.getCol() && super.getColor().equals(otherRook.getColor()));
    }

    public String toString(){
        return super.toString();
    }
}
