package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;
import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.GameSnapshotListObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListObserver;

/**
 * Created by athai on 10/18/17.
 */

public final class Driver implements UsernameListObserver,GameSnapshotListObserver { // will implement GameListObserver
    private HashMap<String, String> usernames;    // the master list of registered users usernames
    private HashMap<String,Game> games;
    private String currentGameKey = "";

    public Driver(){
        this.usernames = new HashMap<>();
        this.games = new HashMap<>();
    }

    public HashMap<String, Game> getGames(){
        return games;
    }

    /**
     * Builds a new game and adds it to the set of current games.
     * @param nickname1 - the inviting user for this game
     * @param nickname2 - the invited user for this game
     * @throws IllegalArgumentException - if either passed user are not registered with the system
     */
    public void createGame(String nickname1,String nickname2) throws IllegalArgumentException{
        //must check if both players are registered
        if(isRegistered(nickname1) && isRegistered(nickname2)) {
            Game game = new Game(nickname1, nickname2);
            GameSnapshot snapshot = new GameSnapshot(game);
            String key = DBIOCore.addGameSnapshot(snapshot);
            games.put(key,game);
        }
        else{
            throw new IllegalArgumentException("ERROR: both passed users must be registered");
        }
    }

    /**
     * Sets the active game of the set of active games.
     * @param key - the key of the game to set as active
     * @throws IllegalArgumentException - if the passed index is out of bounds, i.e. greater or equal to the number of active games
     */
    public void setCurrentGameKey(String key) throws IllegalArgumentException {
        if (!games.containsKey(key)) {
            throw new IllegalArgumentException("ERROR: given index is out of bounds");
        }
        currentGameKey = key;
    }

    /**
     * Checks if the passed user has it's nickname in the system, i.e. has registered to the system
     * @param nickname - the nickname to check
     * @return true if the user is registered to the system, false if not
     */
    private boolean isRegistered(String nickname){
        if (usernames.containsValue(nickname)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is the method the UI will use to validate moves the player is attempting. This method will
     * decide if the move is valid and if so will update the game state and return true.
     * @param nickname - the current user making the move
     * @param fromRow - the x coordinate of the moves starting tile
     * @param fromCol- the y coordinate of the moves starting tile
     * @param toRow- the x coordinate of the moves ending tile
     * @param toCol- the y coordinate of the moves ending tile
     * @return -1 if the move was invalid, 1 if the move was successful, 0 if the move ended the game
     */
    public int makeMove(String nickname, int fromRow, int fromCol, int toRow,int toCol) {
        int result = games.get(currentGameKey).makeMove(nickname,fromRow,fromCol,toRow,toCol);
        if (result == 0) {
            // book keeping for a finished game should go here. Will need to wait until the DB is set up to handle games and game results
        } else if (result > 1) {
            // book keeping to sync the opponents board with the new game state. Will need to wait until the DB is set up to handle games
        }
        return result;
    }

    /**
     * This method should be used if a user wishes to forfeit a game in progress. That user will be marked as the looser
     * if successful and the opponent will be marked as the winner.
     * @param nickname - the user that wishes to quit
     * @return - true if the user is part of the current game and the quit operation was successful, false if otherwise
     */
    public boolean quitGame(String nickname) {
        if (games.get(currentGameKey).getP1Nickname().equals(nickname) || games.get(currentGameKey).getP2Nickname().equals(nickname)) {
            // preform quit operation, will need to wait until DB is caught up
            return true;
        }
        return false;
    }

    // --------------- Data retrieval/Observer methods --------------- //

    @Override
    public void usernameAdded(String addedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, addedUsername);
    }

    @Override
    public void usernameChanged(String changedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, changedUsername);
    }

    @Override
    public void usernameRemoved(String removedUsername) {
        String rmKey = "";
        for (String key : usernames.keySet()) {
            if (usernames.get(key).equals(removedUsername)) {
                rmKey = key;
                break;
            }
        }
        if (rmKey != "") {
            usernames.remove(rmKey);
        }
    }

    /**
     * @param addedSnapshot - snapshot to convert to a game
     * @param precedingSnapshotKey - snapshot's key from database
     */
    @Override
    public void snapshotAdded(GameSnapshot addedSnapshot, String precedingSnapshotKey) {
        Game tempGame = new Game(addedSnapshot.getNicknameWhite(),addedSnapshot.getNicknameBlack());
        games.put(precedingSnapshotKey,tempGame);
        //update that game with snapshot
        tempGame.updateFromSnapshot(addedSnapshot);
    }

    @Override
    public void snapshotChanged(GameSnapshot changedSnapshot, String precedingSnapshotKey) {
        //update game with new snapshot
        Game tempGame = new Game(changedSnapshot.getNicknameWhite(),changedSnapshot.getNicknameBlack());
        games.put(precedingSnapshotKey,tempGame);
        //Update game with snapshot
        tempGame.updateFromSnapshot(changedSnapshot);
    }

    @Override
    public void snapshotRemoved(GameSnapshot removedSnapshot) {
        //remove game from list
        String rmKey = "";
        for (String key : games.keySet()) {
            if (games.get(key).equals(removedSnapshot)) {
                rmKey = key;
                break;
            }
        }
        if (rmKey != "") {
            games.remove(rmKey);
        }
    }
}
