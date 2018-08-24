package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Frame;
import java.util.Locale;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentLookupScope;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.GUIComponentID;

public class CommandShowAboutTest {
    /**
     * Tests the UI construction. This includes successful opening, finding by the ComponentID,
     * asserting that the frame is showing/visible, has the correct size and title and still shows
     * after trying to open it a second time.
     */
    @Test
    public void UITest() {
        LanguageManager langman = LanguageManager.getInstance();
        langman.setLocale(Locale.GERMAN);
        
        CommandShowAbout command = new CommandShowAbout();
        command.execute();
        
        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        robot.settings().componentLookupScope(ComponentLookupScope.ALL);
        FrameFixture frameFixture = WindowFinder.findFrame(GUIComponentID.FRAME_ABOUT.toString()).using(robot);
        Frame frame = frameFixture.target();
        
        assertNotNull(frameFixture);
        assertNotNull(frame);
        
        assertTrue(frame.isShowing());
        assertTrue(frame.isVisible());
        assertTrue(frame.getSize().equals(CommandShowAbout.DIMENSION));
        assertTrue(frame.getTitle().equals(langman.getString(LanguageKey.MENU_ABOUT)));
        
        command.execute();
        langman.setLocale(Locale.ENGLISH);
        frameFixture = WindowFinder.findFrame(GUIComponentID.FRAME_ABOUT.toString()).using(robot);
        frame = frameFixture.target();
        assertNotNull(frameFixture);
        assertNotNull(frame);
        
        assertTrue(frame.isShowing());
        assertTrue(frame.isVisible());
        assertTrue(frame.getSize().equals(CommandShowAbout.DIMENSION));
        assertTrue(frame.getTitle().equals(langman.getString(LanguageKey.MENU_ABOUT)));
        
        command.windowClosing(null);
        assertFalse(command.isOpened());
        langman.setLocale(Locale.GERMAN);
        
        // These don't do anything
        command.windowActivated(null);
        command.windowClosed(null);
        command.windowDeactivated(null);
        command.windowDeiconified(null);
        command.windowIconified(null);
        command.windowOpened(null);
        
        frameFixture.cleanUp();
    }
}
