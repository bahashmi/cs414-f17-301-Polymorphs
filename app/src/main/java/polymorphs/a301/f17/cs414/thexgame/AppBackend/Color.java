package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/23/17.
 */

enum Color {
    WHITE,
    BLACK;

    public Color opposingColor(Color color){
        if(Color.WHITE == color){
            return Color.BLACK;
        }

        else{
            return Color.WHITE;
        }

    }
}
