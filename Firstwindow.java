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

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.WindowAdapter;


public class Firstwindow extends Frame implements ActionListener{
    TextArea textArea = new TextArea();
    String filename="New File ";
  
    private JFileChooser fileChooser;
    Frame frame = new Frame( filename+"-Notepad", null);
    Frame fontSetupFrame;


    String text,fontName;
    Firstwindow(){
       
        Choice choice = new Choice();
      
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File", getFocusTraversalKeysEnabled());
        Menu menuEdit = new Menu("Edit", getFocusTraversalKeysEnabled());
        MenuItem fontSetupEdit = new MenuItem("Font setup");
        
        Menu setTheme = new Menu("Theme");
        
        MenuItem lightTheme = new MenuItem("Light");
        MenuItem darkTheme = new MenuItem("Dark", null);
        
        menuEdit.add(fontSetupEdit);
        menuEdit.add(setTheme);
        setTheme.add(lightTheme);
        setTheme.add(darkTheme);

        lightTheme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                themeSet("Light");
            }
        });
        darkTheme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                themeSet("Dark");
            }
        });

        
        
        Menu menuHelp = new Menu("Help", getFocusTraversalKeysEnabled());
        
        Panel panel = new Panel(new BorderLayout());

        frame.setSize(400, 400);
   
        
        frame.setMenuBar(menuBar);
        frame.setResizable(true);
        frame.setAlwaysOnTop(true);
      
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

        fontSetupEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent fontsetupp){
                fontSetup();
            }
        });
        openFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent openerror){
                openFile();
            }
        });

        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog,"Inspired by Windows Notepad \n Created by Pallab Gogoi.", "About", JOptionPane.INFORMATION_MESSAGE);
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
                try{
                    fontSetupFrame.dispose();
                }finally{
                    closeWindow(e);
            }}
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
        confirmFrame.setAlwaysOnTop(true);

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
        

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    private void fontSet(String selectedFontFamily, String selectedFontStyle,int selectedFontSize){
       int fontStyle;
   if (selectedFontStyle.equals("Plain")) {
       fontStyle = Font.PLAIN;
   } else if (selectedFontStyle.equals("Bold")) {
       fontStyle = Font.BOLD;
   } else if (selectedFontStyle.equals("Italic")) {
       fontStyle = Font.ITALIC;
   } else {
       fontStyle = Font.PLAIN; // Default to plain if the style is not recognized
   }
        Font font = new Font(selectedFontFamily,fontStyle,selectedFontSize);
       textArea.setFont(font);
 
    }
    private void themeSet(String choice){
        if(choice == "Light"){
         textArea.setBackground(Color.WHITE);
         textArea.setForeground(Color.BLACK);
        }
        if(choice =="Dark"){
         textArea.setBackground(Color.DARK_GRAY);
         textArea.setForeground(Color.CYAN);
        }
    }
    private void fontSetup(){
        
        fontSetupFrame = new Frame("Font Setup");
        fontSetupFrame.setSize(300, 300);
        centerWindow(fontSetupFrame);
        fontSetupFrame.setVisible(true);
        fontSetupFrame.setResizable(false);
        fontSetupFrame.setLayout(null);
        fontSetupFrame.setAlwaysOnTop(true);
        fontSetupFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing (WindowEvent e){
                fontSetupFrame.dispose();
            }
        });

        Label fontFamily = new Label("Family");
        fontFamily.setBounds(50, 50, 80, 40);
        Label fontStyle = new Label("Style");
        fontStyle.setBounds(50, 100, 80, 40);
        Label fontSize = new Label("Size");
        fontSize.setBounds(50, 150, 80, 40);
        Button saveFontButton = new Button("Save");
        saveFontButton.setBounds(100,220,80,40);
        fontSetupFrame.add(saveFontButton);

        Choice ffamily = new Choice();
        ffamily.setBounds(150,60,80,40);
        ffamily.add("Ariel");
        ffamily.add("Serif");
        ffamily.add("SansSerif");
        ffamily.add("Monospaced");


        Choice fstyle = new Choice();
        fstyle.setBounds(150,110,80,40);
        fstyle.add("Plain");
        fstyle.add("Italic");
        fstyle.add("Bold");

        Choice fsize = new Choice();
        fsize.setBounds(150,160,80,40);
        fsize.add("10");
        fsize.add("12");
        fsize.add("14");
        fsize.add("16");
        fsize.add("18");
        fsize.add("20");
        fsize.add("22");
        fsize.add("24");
    
        fontSetupFrame.add(ffamily);
        fontSetupFrame.add(fstyle);
        fontSetupFrame.add(fsize);

        fontSetupFrame.add(fontFamily);
        fontSetupFrame.add(fontStyle);
        fontSetupFrame.add(fontSize);

        saveFontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String selectedFontFamily = ffamily.getItem(ffamily.getSelectedIndex());
                String selectedFontStyle = fstyle.getItem(fstyle.getSelectedIndex());
                Integer selectedFontSize = Integer.valueOf(fsize.getItem(fsize.getSelectedIndex()));
                fontSet(selectedFontFamily, selectedFontStyle, selectedFontSize);
                try{
                    Thread.sleep(1000);
                    fontSetupFrame.dispose();
                }catch(Exception ef){
              
                }
            }
        });
    }
    
}
