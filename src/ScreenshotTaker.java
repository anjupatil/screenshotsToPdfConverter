import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotTaker {

    public static void main(String[] args) {
        // Create a JFrame and a JPanel
        JFrame frame = new JFrame("Screenshot Taker");
        JPanel panel = new JPanel();

        // Create a "Take Screenshot" button and add it to the panel
        JButton screenshotButton = new JButton("Take Screenshot");
        panel.add(screenshotButton);

        // Create a "Save PDF" button and add it to the panel
        JButton saveButton = new JButton("Save PDF");
        panel.add(saveButton);

        // Add the panel to the frame
        frame.add(panel);

        // Set the frame size and make it visible
        frame.setSize(400, 100);
        frame.setVisible(true);

        // Create an array to store the screenshots
        BufferedImage[] screenshots = new BufferedImage[100];
        final int[] screenshotCount = {0};

        // Add an action listener to the "Take Screenshot" button



        screenshotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user to select an area on the screen
                //frame.setVisible(false);
                frame.setVisible(false);

                // Hide the JFrame after 5 seconds
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException f) {
                    // Do nothing
                }
                BufferedImage screenshot = null;

                try {

                    screenshot = new Robot().createScreenCapture(
                            new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())
                    );

                } catch (AWTException ex) {
                    ex.printStackTrace();
                }

                // Add the screenshot to the array
                screenshots[screenshotCount[0]] = screenshot;
                screenshotCount[0]++;
                frame.setVisible(true);
            }
        });
        frame.setVisible(true);

        // Add an action listener to the "Save PDF" button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a PDF document
                Document document = new Document(PageSize.B2);
                try {
                    PdfWriter.getInstance(document, new FileOutputStream("d:screenshots.pdf"));
                    document.open();

                    // Add each screenshot to the PDF document
                    for (int i = 0; i < screenshotCount[0]; i++) {
                        Image image = Image.getInstance(screenshots[i], null);
                        document.add(image);
                    }

                    // Close the PDF document
                    document.close();
                } catch (DocumentException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}