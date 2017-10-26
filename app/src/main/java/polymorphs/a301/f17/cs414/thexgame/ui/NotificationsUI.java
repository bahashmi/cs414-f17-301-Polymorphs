package polymorphs.a301.f17.cs414.thexgame.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.HomescreenActivity;
import polymorphs.a301.f17.cs414.thexgame.Invitation;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.InviteListObserver;

/**
 * Created by thenotoriousrog on 10/13/17.
 *
 * This class is in charge of setting up the UI for Notifications when the user clicks the button!
 */

public class NotificationsUI extends Fragment implements InviteListObserver{

    private HashMap<String, Invitation> invitationsData = new HashMap<>(); // todo: we will likely want to change this to an invitations class. For now I am going to use strings just for us to have a UI up and running!
    private ArrayList<String> eventsData = new ArrayList<>(); // TODO: This needs to be pulled from the database so that users can be able to get information which is pretty important for us to get this working!

    private ListView invitationsList;  // holds the invitations list.
    private ListView eventsList; // holds the events list.
    private EventListAdapter eventListAdapter; // holds the EventListAdapter
    private InvitationsListAdapter invitationsListAdapter; // holds the invitations list adapter.

    // The first method reached when the method is called to be created!
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments(); // get the arguments for the fragment activity.
        // TODO: we should grab any arguments that we have sent to the Notifications UI pretty important!!
        DBIOCore.registerToCurrentUserInviteList(this);
    }

    // sets up the events list view.
    protected void setupEventsListView(View notificationsView)
    {
        /*
        eventsList = (ListView) notificationsView.findViewById(R.id.eventsListView); // grab the events list.

        // todo: this is only for testing. Remove this once we are pulling data from the database.
        for(int i = 0; i < 15; i++)
        {
            eventsData.add("Game won!");
        }

        eventListAdapter = new EventListAdapter(notificationsView.getContext(), R.layout.event_item, eventsData);
        eventsList.setAdapter(eventListAdapter);
        eventListAdapter.notifyDataSetChanged(); // tell the adapter that we have some information to show here!
        eventsList.setDivider(null);
        eventsList.setDividerHeight(10);
        */
    }

    // set up the invitations list view.
    protected void setupInvitationsListView(View notificationsView)
    {
        invitationsList = (ListView) notificationsView.findViewById(R.id.invitationsListView); // grab the inviations list.
        invitationsListAdapter = new InvitationsListAdapter(notificationsView.getContext(), R.layout.invitation_item,new ArrayList<>(invitationsData.values()) , (HomescreenActivity) getActivity()); // create the invitations adapter that we need to use.
        invitationsList.setAdapter(invitationsListAdapter);
        invitationsListAdapter.notifyDataSetChanged();
        invitationsList.setDivider(null);
        invitationsList.setDividerHeight(10);
    }

    // This the next method which also creates all of the UI elements.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View notificationsView = inflater.inflate(R.layout.notifications, container, false); // inflate our view of our main game.

        setupEventsListView(notificationsView); // setup the events list view
        setupInvitationsListView(notificationsView); // setup the inviatations list view.

        return notificationsView; // display the fragment for the users to see.
    }

    @Override
    public void inviteAdded(Invitation addedInvite, String precedingInviteKey) {
        invitationsListAdapter.add(addedInvite);
        invitationsData.put(precedingInviteKey, addedInvite);
        invitationsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void inviteChanged(Invitation changedInvite, String precedingInviteKey) {
        invitationsListAdapter.remove(invitationsData.get(precedingInviteKey));
        invitationsData.put(precedingInviteKey, changedInvite);
        invitationsListAdapter.add(changedInvite);
        invitationsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void inviteRemoved(Invitation removedInvite) {
        invitationsListAdapter.remove(removedInvite);
        String rmKey = "";
        for (String key : invitationsData.keySet()) {
            if (invitationsData.get(key).equals(removedInvite)) {
                rmKey = key;
                break;
            }
        }
        invitationsData.remove(rmKey);
        invitationsListAdapter.notifyDataSetChanged();
    }
}
