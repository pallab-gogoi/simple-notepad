import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.WindowAdapter;


public class Firstwindow extends Frame implements ActionListener{
    TextArea textArea = new TextArea();
    String filename="New File ";
    private JFileChooser fileChooser;
    Frame frame = new Frame( filename+"-Notepad", null);



    String text;
    Firstwindow(){
        System.out.println("Constructor");
        Choice choice = new Choice();

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File", getFocusTraversalKeysEnabled());
        Menu menuEdit = new Menu("Edit", getFocusTraversalKeysEnabled());
        Menu menuHelp = new Menu("Help", getFocusTraversalKeysEnabled());
        Panel panel = new Panel(new BorderLayout());

        //JPanel textJPanel = new JPanel(new BorderLayout());
        frame.setSize(400, 400);
        //frame.setLayout(null);
        
        frame.setMenuBar(menuBar);
        frame.setResizable(true);
        //frame.add(textArea, BorderLayout.CENTER );
        //frame.add(textJPanel, BorderLayout.CENTER);
        frame.add(choice); // here
        panel.add(textArea);
        frame.add(panel);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuHelp);

        MenuItem newWindow = new MenuItem("New window", null);
        MenuItem openFile = new MenuItem("Open file");
        MenuItem saveFile = new MenuItem("Save file", null);
        MenuItem exitWindow = new MenuItem("Exit", null);

        MenuItem github = new MenuItem("Github", null);
        MenuItem about = new MenuItem("About", null);

        //textArea.setBounds(20, 60, 360, 300);

        menuFile.add(newWindow);
        menuFile.add(openFile);
        menuFile.add(saveFile);
        menuFile.add(exitWindow);
        menuHelp.add(github);
        menuHelp.add(about);

        openFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent openerror){
                openFile();
            }
        });

        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                JOptionPane.showMessageDialog(null,"Inspired by Windows Notepad \n Created by Pallab Gogoi.", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        github.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try{
                    URI uri = new URI("https://github.com/pallab-gogoi/simple-notepad");
                    Desktop.getDesktop().browse(uri);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        newWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new Firstwindow();
            }
        });
        exitWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                closeWindow(null);
            }
        });
        saveFile.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                saveFileDialog();
            }});
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                closeWindow(e);
            }
        });
        centerWindow(frame);
        frame.setVisible(true);
    }
    private void openFile(){
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        int result = fileChooser.showOpenDialog(this);
         if (result == JFileChooser.APPROVE_OPTION) {
         File file = fileChooser.getSelectedFile();
              try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                  StringBuilder content = new StringBuilder();
                  String line;
                 while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                   }
                  textArea.setText(content.toString());
              } catch (IOException e) {
            showMessage("Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }
    private void saveFileDialog(){
        JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(fileChooser);

                if (option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    String filePath = file.getAbsolutePath();

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        String text = textArea.getText();
                        writer.write(text);

                        JOptionPane.showMessageDialog(fileChooser,"File saved successfully!");
                    } catch (IOException er) {
                        JOptionPane.showMessageDialog(fileChooser, "Error saving file: " + er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
    }
    private void centerWindow(Window window){
        Dimension screeDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (screeDimension.width - window.getWidth())/2;
        int centerY = (screeDimension.height - window.getHeight())/2;

        window.setLocation(centerX, centerY);
    }


    public void closeWindow(WindowEvent e) {
        Frame confirmFrame = new Frame("Confirm");
        Label confirmLabel =  new Label("Do you want to save cahnges to the file?");

        Button saveButton = new Button("Save");
        Button DontButton = new Button("Don't save");
        Button cancelButton = new Button("Cancel");

        saveButton.setBounds(20, 80, 80, 30);
        DontButton.setBounds(110, 80, 80, 30);
        cancelButton.setBounds(200, 80, 80, 30);

        confirmLabel.setBounds(30, 50, 330, 30);

        confirmFrame.setSize(300, 150);
        centerWindow(confirmFrame);
        confirmFrame.setVisible(true);
        confirmFrame.setLayout(null);
        confirmFrame.add(confirmLabel);
        confirmFrame.add(DontButton);
        confirmFrame.add(cancelButton);
        confirmFrame.add(saveButton);
        confirmFrame.setResizable(false);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                saveFileDialog();
                confirmFrame.dispose();
            }
        });

        DontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                frame.dispose();
                confirmFrame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                confirmFrame.dispose();
            }
        });
        System.out.println("Application closed..");
        System.out.println("Relesing Memory..");

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
