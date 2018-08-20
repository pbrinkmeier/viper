package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Frame;

import javax.swing.text.JTextComponent;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentLookupScope;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.GUIComponentID;
import edu.kit.ipd.pp.viper.view.MainWindow;

public class CommandShowStandardTest {
    /**
     * Tests the UI construction. This includes successful opening, finding by the ComponentID,
     * asserting that the frame is showing/visible, has the correct size and title and still shows
     * after trying to open it a second time. Additionally checks whether the content of the
     * text area equals the standard library code.
     */
    @Test
    public void testUI() {
        MainWindow gui = new MainWindow(false);
        
        CommandShowStandard command = new CommandShowStandard(gui.getInterpreterManager());
        command.execute();
        
        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        robot.settings().componentLookupScope(ComponentLookupScope.ALL);
        FrameFixture frameFixture = WindowFinder.findFrame(GUIComponentID.FRAME_SHOW_STD.toString()).using(robot);
        Frame frame = frameFixture.target();
        LanguageManager langman = LanguageManager.getInstance();
        
        assertNotNull(frameFixture);
        assertNotNull(frame);
        
        assertTrue(frame.isShowing());
        assertTrue(frame.isVisible());
        assertTrue(frame.getSize().equals(CommandShowStandard.DIMENSION));
        assertTrue(frame.getTitle().equals(langman.getString(LanguageKey.STANDARD_LIBRARY)));
        
        command.execute();
        frameFixture = WindowFinder.findFrame(GUIComponentID.FRAME_SHOW_STD.toString()).using(robot);
        frame = frameFixture.target();
        assertNotNull(frameFixture);
        assertNotNull(frame);
        
        assertTrue(frame.isShowing());
        assertTrue(frame.isVisible());
        assertTrue(frame.getSize().equals(CommandShowStandard.DIMENSION));
        assertTrue(frame.getTitle().equals(langman.getString(LanguageKey.STANDARD_LIBRARY)));
        
        JTextComponent textArea = frameFixture.textBox(GUIComponentID.FRAME_SHOW_STD_TEXTAREA.toString()).target();
        assertTrue(textArea.getText().equals(gui.getInterpreterManager().getStandardLibraryCode()));
        
        frameFixture.cleanUp();
    }
}
