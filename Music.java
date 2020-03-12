import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class Music {

    public static void playMusic(){
        String musicLocation = "music\\8-Bit-Noise-3.wav";

        try{
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                //JOptionPane.showMessageDialog(null,"press ");
            }
            else
                System.out.println("can not find the file");
        }
        catch (Exception ee){

            ee.printStackTrace();
        }



    }
}
