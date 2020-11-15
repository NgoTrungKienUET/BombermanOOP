package uet.oop.bomberman.sound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sound {
    public Player player;
    public Sound(String name)
    {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = new File(classLoader.getResource("music/" + name + ".mp3").getFile());
            FileInputStream fileInputStream = new FileInputStream(file);
            player = new Player(new BufferedInputStream(fileInputStream));
            new Thread() {
                public void run() {
                    try{
                        player.play();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
