package code.project.projectcode;

/*
Createed By Andrew McGuire
TEAM PANADA
PROJECT CODE
 */
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;



public class MorseToSound {

    MediaPlayer player;
    int soundID[] = {R.raw.dot,R.raw.dash};
    ArrayList<Integer> playlist = new ArrayList<Integer>();

    // Not used anymore was going to be the method of how to make it a playlist. 
    public void setNextMediaForMediaPlayer(MediaPlayer player, final int audio){
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
               mp = MediaPlayer.create(ProjectActivity.getAppContext(),audio);
                mp.start();
            }
        });
    }

    // This plays the sounds in the correct order, after the completion of the sound before
    void play ( final ArrayList<Integer> soundlist ,MediaPlayer player, final int index)
    {
        player = MediaPlayer.create(ProjectActivity.getAppContext(),soundlist.get(index));
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
       public void onCompletion(MediaPlayer mp){

           try {
               wait(500);
           } catch (Exception e) {
               e.printStackTrace();
           }
           if(soundlist.size() > index+1) {
               play(soundlist, mp, index + 1);
           }

           mp.release();
        }
    });
    }

    // This is for the Morse to Text feature to play the sounds as they are pressed
    void codeToSound(String Morse)
    {
        if (Morse.charAt(0) == '-')
        {
            player = MediaPlayer.create(ProjectActivity.getAppContext(),soundID[1]);

            // releases the mediaplyer back to memory on soudn completion
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                public void onCompletion(MediaPlayer mp){
                    mp.release();
                }
            });
            player.start();
        }
        else if (Morse.charAt(0) == '.')
        {
            player = MediaPlayer.create(ProjectActivity.getAppContext(),soundID[0]);
            // releases the mediaplyer back to memory on soudn completion
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                public void onCompletion(MediaPlayer mp){
                    mp.release();
                }
            });
            player.start();

        }
    }

    // This creates a list of sounds to be played from the Text to Morse
     void morseToSound(String morse)  {

         // separate on string letters
        String[] letters = morse.split(" ");

       for(int i = 0; i < letters.length; i++)
       {
           for( int x = 1 ; x < letters[i].length(); x++)
           {
                if (letters[i].charAt(x) == '-' )
                {
                   playlist.add(soundID[1]);
                }
               else if (letters[i].charAt(x) == '.' )
               {
                   playlist.add(soundID[0]);
               }
           }
           play(playlist,player,0);
    }
     }
}
