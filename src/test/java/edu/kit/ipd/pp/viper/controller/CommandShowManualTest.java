package edu.kit.ipd.pp.viper.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.swing.Icon;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.ComponentLookupScope;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;

import edu.kit.ipd.pp.viper.view.GUIComponentID;
import edu.kit.ipd.pp.viper.view.ToolBar;

public class CommandShowManualTest {    
    /**
     * Tests the loading of all icons.
     */
    @Test
    public void loadIconTest() {
        CommandShowManual command = new CommandShowManual();
        
        assertNotNull(command.loadIcon(ToolBar.ICON_NEW));
        assertNotNull(command.loadIcon(ToolBar.ICON_OPEN));
        assertNotNull(command.loadIcon(ToolBar.ICON_SAVE));
        assertNotNull(command.loadIcon(ToolBar.ICON_FORMAT));
        assertNotNull(command.loadIcon(ToolBar.ICON_PARSE));
        assertNotNull(command.loadIcon(ToolBar.ICON_NEXTSTEP));
        assertNotNull(command.loadIcon(ToolBar.ICON_PREVIOUSSTEP));
        assertNotNull(command.loadIcon(ToolBar.ICON_NEXTSOLUTION));
        assertNotNull(command.loadIcon(ToolBar.ICON_CANCEL));
        assertNotNull(command.loadIcon(ToolBar.ICON_ZOOM_IN));
        assertNotNull(command.loadIcon(ToolBar.ICON_ZOOM_OUT));
        
        assertNotNull(command.loadIcon(ToolBar.ICON_NEW).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_OPEN).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_SAVE).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_FORMAT).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_PARSE).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_NEXTSTEP).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_PREVIOUSSTEP).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_NEXTSOLUTION).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_CANCEL).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_ZOOM_IN).getIcon());
        assertNotNull(command.loadIcon(ToolBar.ICON_ZOOM_OUT).getIcon());
        
        Dimension dim = new Dimension(CommandShowManual.ICON_SIZE, CommandShowManual.ICON_SIZE);
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_NEW).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_OPEN).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_SAVE).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_FORMAT).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_PARSE).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_NEXTSTEP).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_PREVIOUSSTEP).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_NEXTSOLUTION).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_CANCEL).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_ZOOM_IN).equals(dim));
        assertTrue(this.getIconDimensions(command, ToolBar.ICON_ZOOM_OUT).equals(dim));
    }
    
    private Dimension getIconDimensions(CommandShowManual command, String path) {
        Icon icon = command.loadIcon(path).getIcon();
        return new Dimension(icon.getIconWidth(), icon.getIconHeight());
    }
    
    /**
     * Tests the UI construction. This includes successful opening, finding by the ComponentID,
     * asserting that the frame is showing/visible, has the correct size and title and still shows
     * after trying to open it a second time.
     */
    @Test
    public void UITest() {
        LanguageManager langman = LanguageManager.getInstance();
        langman.setLocale(Locale.GERMAN);
        
        CommandShowManual command = new CommandShowManual();
        command.execute();
        
        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        robot.settings().componentLookupScope(ComponentLookupScope.ALL);
        FrameFixture frameFixture = WindowFinder.findFrame(GUIComponentID.FRAME_MANUAL.toString())
                .withTimeout(SharedTestConstants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .using(robot);
        Frame frame = frameFixture.target();
        
        assertNotNull(frameFixture);
        assertNotNull(frame);
        
        assertTrue(frame.isShowing());
        assertTrue(frame.isVisible());
        assertTrue(frame.getSize().equals(CommandShowManual.DIMENSION));
        assertTrue(frame.getTitle().equals(langman.getString(LanguageKey.MANUAL)));
        
        command.execute();
        langman.setLocale(Locale.ENGLISH);
        frameFixture = WindowFinder.findFrame(GUIComponentID.FRAME_MANUAL.toString())
                .withTimeout(SharedTestConstants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .using(robot);
        frame = frameFixture.target();
        assertNotNull(frameFixture);
        assertNotNull(frame);
        
        assertTrue(frame.isShowing());
        assertTrue(frame.isVisible());
        assertTrue(frame.getSize().equals(CommandShowManual.DIMENSION));
        assertTrue(frame.getTitle().equals(langman.getString(LanguageKey.MANUAL)));
        
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
