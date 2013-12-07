package cz.possoft.calculator.runner.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 * Main class for running GUI app. 
 * 
 * Note: Application can be executed by running something like: 
 * java -cp mortgage-calculator.jar:lib/TableLayout-20050920.jar cz.possoft.calculator.runner.swing.SwingAppRunner
 * 
 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>
 *
 */
public class SwingAppRunner 
{
	/**
	 * Running specified Java applet inside a JFrame. So applet is not forced to be used inside web page, but can be used
	 * inside swing JFrame.
	 * 
	 * @param appletclassName
	 * @param width width of frame
	 * @param height height of frame
	 * @param frameTitle 
	 */
    public void runApplet(String appletclassName, int width, int height, String frameTitle) 
    {

    	/**
    	 * Used for closing application when window is closed.
    	 * 
    	 * @author <a href="mailto:mposolda@gmail.com">Marek Posolda</a>    	 
    	 */
        class CloserWindowListener extends WindowAdapter 
        {
            
            public void windowClosing(WindowEvent e) 
            {                
            	System.exit(0);
            }

            
            public void windowActivated(WindowEvent e) 
            {            	
            }
        }

        JApplet applet = new JApplet();
        try 
        {
            Class<?> c = Class.forName(appletclassName);
            applet = (JApplet) (c.newInstance());
            JFrame frame = new JFrame(frameTitle);
            frame.getContentPane().add(applet);
            frame.setBounds(100, 0, width, height);
            applet.init();
            applet.start();
            frame.addWindowListener(new CloserWindowListener());
            frame.setVisible(true);
        } 
        catch (InstantiationException e) 
        {
            System.err.println("Instantiation exception: ");
            e.printStackTrace();
        } 
        catch (ClassNotFoundException e2) 
        {
            System.err.println("ClassNotFound Exception: ");
            e2.printStackTrace();
        } 
        catch (IllegalAccessException e3) 
        {
            System.err.println("IllegalAccessException: ");
            e3.printStackTrace();
        } 
        catch (Exception ex) 
        {
            System.out.println("Unrecognized exception catched:");
            ex.printStackTrace();
        } 
    }

    public static void main(String[] args) 
    {        
    	new SwingAppRunner().runApplet("cz.possoft.calculator.runner.swing.CalculatorApplet", 900, 735, "Mortgage calculator");
    }

}
