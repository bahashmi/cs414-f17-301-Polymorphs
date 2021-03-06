package polymorphs.a301.f17.cs414.thexgame.ui.listeners;

import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;

import polymorphs.a301.f17.cs414.thexgame.ui.BoardUI;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.HomescreenActivity;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * This will control what happens when the user decides to swipe horizontally on one of the games. The reason for the class is because we are now able to update some of the items in the InGameFragment such as
 * activities as well as being able to update the items on the board and keeping the code a little cleaner. Look in the methods and the comments below to understand what is happening here.
 */

public class GamePageChangeListener implements ViewPager.OnPageChangeListener {

    private HomescreenActivity homescreenActivity; // a copy of the homescreen layout.
    private BoardUI game; // holds a copy of the game that the user is working on which is pretty important!!!

    public GamePageChangeListener(HomescreenActivity homescreenActivity)
    {
        this.homescreenActivity = homescreenActivity;
        game = (BoardUI) homescreenActivity.findViewById(R.id.chessboard); // grabs the chessboard that we are working with.
    }

    // do not pay attention to this.
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // not implemented, this is listening for the pager to be started to be changed.
    }


    // This is the real important thing here. This method will listen for when the user switches a page. When they do, we will display a little snack bar showing the game number to the user!
    @Override
    public void onPageSelected(int position)
    {
        // removed this as it is causing issues on the phone for Andy, need to find a new way to inform users that are being used by Roger, pretty important!
        BoardUI currentBoard = homescreenActivity.getGameAt(position);
        if (currentBoard.getGameState() == 0) {
           currentBoard.displayInProgressCaption();
        } else if (currentBoard.getGameState() == 1) {
            currentBoard.displayWinnerCaption();
        } else if (currentBoard.getGameState() == 2) {
            currentBoard.displayTieCaption();
        }
        homescreenActivity.switchToGameAt(position);
    }


    // This tells the android ui when the state of thew view pager changes. Not useful for us likely at all for our app.
    @Override
    public void onPageScrollStateChanged(int state) {} // nothing for this user.
}
