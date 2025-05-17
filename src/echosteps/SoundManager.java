package echosteps;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private Clip coinSound;
    private Clip winSound;
    private Clip loseSound;
    private boolean isMuted = false;
    private FloatControl effectsVolumeControl;
    private long lastCoinSoundTime = 0;
    private static final long COIN_SOUND_COOLDOWN = 0; // Minimum time between coin sounds in milliseconds

    public SoundManager() {
        try {
            // Load sound effects
            coinSound = loadSound("sounds/coin.wav");
            winSound = loadSound("sounds/win.wav");
            loseSound = loadSound("sounds/lose.wav");

            // Set up volume control for sound effects
            if (coinSound != null) {
                effectsVolumeControl = (FloatControl) coinSound.getControl(FloatControl.Type.MASTER_GAIN);
                effectsVolumeControl.setValue(-5.0f); // Set volume for effects
            }
        } catch (Exception e) {
            System.err.println("Error loading sounds: " + e.getMessage());
        }
    }

    private Clip loadSound(String path) {
        try {
            File soundFile = new File(path);
            if (!soundFile.exists()) {
                System.err.println("Could not find sound file: " + path);
                return null;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound " + path + ": " + e.getMessage());
            return null;
        }
    }

    public void playCoinSound() {
        if (!isMuted && coinSound != null) {
            long currentTime = System.currentTimeMillis();
            
            // Check if enough time has passed since last coin sound
            if (currentTime - lastCoinSoundTime >= COIN_SOUND_COOLDOWN) {
                // Stop any currently playing coin sound
                if (coinSound.isRunning()) {
                    coinSound.stop();
                }
                
                // Reset and start the sound
                coinSound.setFramePosition(0);
                coinSound.start();
                lastCoinSoundTime = currentTime;
            }
        }
    }

    public void playWinSound() {
        if (!isMuted && winSound != null) {
            winSound.setFramePosition(0);
            winSound.start();
        }
    }

    public void playLoseSound() {
        if (!isMuted && loseSound != null) {
            loseSound.setFramePosition(0);
            loseSound.start();
        }
    }

    public void toggleMute() {
        isMuted = !isMuted;
    }

    public void cleanup() {
        if (coinSound != null) {
            coinSound.stop();
            coinSound.close();
        }
        if (winSound != null) {
            winSound.stop();
            winSound.close();
        }
        if (loseSound != null) {
            loseSound.stop();
            loseSound.close();
        }
    }
} 