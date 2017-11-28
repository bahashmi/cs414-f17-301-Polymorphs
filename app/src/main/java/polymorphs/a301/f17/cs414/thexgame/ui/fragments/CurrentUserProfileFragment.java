package polymorphs.a301.f17.cs414.thexgame.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.HomescreenActivity;

/**
 * Created by thenotoriousrog on 11/16/17.
 *
 * This class is used to create the user's profile fragment. This fragment is responsible for showing the current user's profile fragment which holds some current data info for that user.
 */

public class CurrentUserProfileFragment extends Fragment {

    private static final int PICK_IMAGE = 9005; // the action code to allow user's to pick the image that they would like to save as their profile picture, very important!!
    private CircleImageView profilePic; // holds our user's profile picture, which can be changed however we want it to be changed to.
    private HomescreenActivity homescreenActivity; // holds the homescreenActivity that we want to use when a user wants to do so.

    private Bitmap currUserProfilePic; // holds the bitmap of the current user's profile picture which should be retrieved or sent in by the homescreen activity.
    private String username = ""; // username of the current user.
    private String currGamesCount = ""; // holds the number of current games this user has.
    private String winPercentage = ""; // holds the win percentage that this user has.
    // TODO: create a global variable that will hold the user's profile picture, likely we will set this using a setter call from homescreenActivity.

    // Create setters here if we choose to go this route.

    // sets the homescreen activity for user's to be able to use.
    public void setHomescreenActivity(HomescreenActivity homescreenActivity)
    {
        this.homescreenActivity = homescreenActivity;
    }

    // sets the user's current profile picture.
    public void setCurrUserProfilePic(Bitmap pic)
    {
        this.currUserProfilePic = pic;
    }


    // this method will change the user's profile picture after they have chosen a picture they want their profile picture to be.
    private void changeProfilePic(Bitmap newProfilePic)
    {
        profilePic.setImageBitmap(newProfilePic); // set the new profile picture!
    }

    // Consider this method the constructor of the fragment class. Very important to get this thing fixed up.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /*
            We need to populate the globals above, we can do this a couple of ways:
                    1.) Create setters here in CurrentUserProfileFragment and have homescreenActivity call this setters before the fragment is opened
                    2.) Send in the information to the Fragment using Bundle arguments like I've done in the past (this is Android Standard so we should do this way, but I don't care)
         */

        // Grab bundle args here if we choose to go this route

        // TODO: need to somehow send in the user's profile picture so that we can change it when the user chooses the change his/her profile pic. This is very important.
            // this will likely be done by sending in that profile picture from the HomescreenActivity after homescreen activity pulls it from the database...
    }

    // calculates the size of the optimum image
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // decodes the bitmap with the correct size so that it's not too large.
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight)
    {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View profileUI = inflater.inflate(R.layout.profile, container, false);

        TextView profileUsername = (TextView) profileUI.findViewById(R.id.profileUsername); // gets the user's current number of games.
        String profUsername = homescreenActivity.getCurrentUser().getNickname(); // get the nickname of the current user.
        profileUsername.setText(profUsername); // set the username of the user.

        TextView currNumOfGames = (TextView) profileUI.findViewById(R.id.currentNumOfGames); // gets the text view showing the current number of games.
        String numOfGames = Integer.toString(homescreenActivity.getNumOfGames()); // grabs the number of games and converts it to a string.
        currNumOfGames.setText("Current games: " + numOfGames); // set the number of games for this user.

        TextView winLossPercentage = (TextView) profileUI.findViewById(R.id.winLossPercentage); // gets the user's win loss percentage.
        // todo: set the user's win loss percentage.

        if(!winPercentage.isEmpty()) // make sure that the win loss percentage is not empty.
        {
            int percentage = Integer.parseInt(winPercentage); // convert the percentage to an integer.

            if(percentage < 40) // user's percentage is bad, display the percentage as red.
            {
                winLossPercentage.setTextColor(Color.RED);
            }
            else if(percentage == 40 || percentage <= 69) // user's winloss percentage is average, display it as yellow.
            {
                winLossPercentage.setTextColor(Color.YELLOW);
            }
            else // user's winloss percentage is good, display it as green.
            {
                winLossPercentage.setTextColor(Color.GREEN);
            }
        }


        if(currUserProfilePic == null) // if user doesn't have a current profile picture then we need to resize the default image.
        {
            profilePic = (CircleImageView) profileUI.findViewById(R.id.profilePicture); // gets the user's profile picture.
            Bitmap resizedImg = decodeSampledBitmapFromResource(getResources(), R.drawable.blank_profile_image, 200,200); // resize the default image to be 200 by 200
            profilePic.setImageBitmap(resizedImg); // set the default image to be resized.
        }



        profilePic.setOnClickListener(new View.OnClickListener() {

            // when clicked allow user's to be able to select their image to change to change as their profile picture.
            @Override
            public void onClick(View view)
            {
                Intent getPicIntent = new Intent();
                getPicIntent.setType("image/*"); // this tells the intent that we are looking for anything that qualifies as an image.
                getPicIntent.setAction(Intent.ACTION_GET_CONTENT); // this tells the intent that the goal is to get content of some kind, in our case, an image.
                startActivityForResult(Intent.createChooser(getPicIntent, "Select Profile Pic"), PICK_IMAGE); // this starts our activity and also generates a document finder to let user's select the photo they want to use.
            }
        });


        return profileUI; // return the profile UI to populate the fragment.
    }

    private String filePathFromUri(Uri contentUri)
    {
        Cursor mediaCursor = null;
        try
        {
            String[] dataPath = { MediaStore.Images.Media.DATA };
            mediaCursor = homescreenActivity.getBaseContext().getContentResolver().query(contentUri,  dataPath, null, null, null);
            int column_index = mediaCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            mediaCursor.moveToFirst();
            return mediaCursor.getString(column_index);
        }
        finally // no matter what, finally will get called.
        {
            // close the media cursor if it has been set.
            if(mediaCursor != null)
            {
                mediaCursor.close();
            }
        }
    }

    // This method is called when the user changes their image and then the data is saved, which is very important.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent)
    {
        super.onActivityResult(requestCode, resultCode, resultIntent);

        if(requestCode == PICK_IMAGE) // user has selected an image to use a profile picture. Really cool.
        {
            if(resultIntent == null)
            {
                return; // force a return out of this method.
            }

            Uri selectedImageUri = resultIntent.getData(); // gets the Uri that the Intent returns after a picture is selected.

            // TODO: the app breaks when user's try to select a photo that is outside the domain of the first file picker.
            // TODO: add some additional checks to allow for photos to be selected and be sure to reject those that are not approved image files.

            try // attempt to get the user's picture from the stream and decode it.
            {
                InputStream imageStream = homescreenActivity.getContentResolver().openInputStream(selectedImageUri);
                Bitmap newProfilePic = BitmapFactory.decodeStream(imageStream);
                changeProfilePic(newProfilePic);
                homescreenActivity.saveAndChangeProfilePic(newProfilePic);
                imageStream.close(); // close the image stream
            }
            catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
                System.out.println("File not found exception has occurred!");
                Toast.makeText(homescreenActivity.getBaseContext(), "We can't use that file :( ", Toast.LENGTH_SHORT).show(); // tell the users that there image cannot be used for whatever reason.
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                System.out.println("An io exceptin occurred when trying to close the image stream");
                Toast.makeText(homescreenActivity.getBaseContext(), "Something went wrong! :( ", Toast.LENGTH_SHORT).show(); // tell the users that there image cannot be used for whatever reason.
            }
        }
    }

}
