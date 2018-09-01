package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Frame;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.swing.text.JTextComponent;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentLookupScope;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.GUIComponentID;

public class CommandShowStandardTest extends ControllerTest {
    /**
     * Tests the UI construction. This includes successful opening, finding by the ComponentID,
     * asserting that the frame is showing/visible, has the correct size and title and still shows
     * after trying to open it a second time. Additionally checks whether the content of the
     * text area equals the standard library code.
     */
    @Test
    public void uiTest() {
        LanguageManager langman = LanguageManager.getInstance();
        langman.setLocale(Locale.GERMAN);
        
        CommandShowStandard command = new CommandShowStandard(this.gui.getInterpreterManager());
        command.execute();
        
        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        robot.settings().componentLookupScope(ComponentLookupScope.ALL);
        FrameFixture frameFixture = WindowFinder.findFrame(GUIComponentID.FRAME_SHOW_STD.toString())
                .withTimeout(SharedTestConstants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .using(robot);
        Frame frame = frameFixture.target();
        
        assertNotNull(frameFixture);
        assertNotNull(frame);
        
        assertTrue(frame.isShowing());
        assertTrue(frame.isVisible());
        // This test causes AssertionErrors when run on a machine with a tiling window manager. The WM ignores the
        // dimensions set by Swing, therefore we cannot rely on this test to succeed on every machine.
        // assertTrue(frame.getSize().equals(CommandShowStandard.DIMENSION));
        assertTrue(frame.getTitle().equals(langman.getString(LanguageKey.STANDARD_LIBRARY)));
        
        command.execute();
        frameFixture = WindowFinder.findFrame(GUIComponentID.FRAME_SHOW_STD.toString())
                .withTimeout(SharedTestConstants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .using(robot);
        frame = frameFixture.target();
        assertNotNull(frameFixture);
        assertNotNull(frame);
        
        assertTrue(frame.isShowing());
        assertTrue(frame.isVisible());
        assertTrue(frame.getTitle().equals(langman.getString(LanguageKey.STANDARD_LIBRARY)));
        
        JTextComponent textArea = frameFixture.textBox(GUIComponentID.FRAME_SHOW_STD_TEXTAREA.toString()).target();
        assertTrue(textArea.getText().equals(this.gui.getInterpreterManager().getStandardLibraryCode()));
        
        command.windowClosing(null);
        assertFalse(command.isOpened());
        langman.setLocale(Locale.ENGLISH);
        
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
