import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.awt.event.WindowAdapter;


public class Firstwindow extends Frame implements ActionListener{
    TextArea textArea = new TextArea();
    TextField fname = new TextField();
    String text,filename;
    Firstwindow(){
        System.out.println("Application running..");
        
        Frame frame = new Frame("Pallab's Notepad", null);
        Choice choice = new Choice();
        Button saveButton = new Button("SAVE");
        Label label = new Label("Save file name as :");
        

        
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        
        textArea.setBounds(20, 80, 360, 300);
        saveButton.setBounds(300, 40, 50, 30);
        fname.setBounds(150,40,150,30);
        label.setBounds(40,40,100,30);
        

        frame.add(saveButton);
        frame.add(textArea );
        frame.add(fname);
        frame.add(label);
        frame.setResizable(false);
       
        
        saveButton.addActionListener(this);
        //textArea.write
       frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Frame confirmFrame = new Frame("Confirm");
                Label confirmLabel = new Label("Are you sure you want to close the application ?");
                Button yesButton = new Button("Yes");
                Button cancelButton = new Button("Cancel");

                yesButton.setBounds(30, 80, 120, 30);
                cancelButton.setBounds(160, 80, 120, 30);

                confirmLabel.setBounds(30, 50, 330, 30);

                confirmFrame.setSize(300, 150);
                confirmFrame.setVisible(true);
                confirmFrame.setLayout(null);
                confirmFrame.add(confirmLabel);
                confirmFrame.add(yesButton);
                confirmFrame.add(cancelButton);
                confirmFrame.setResizable(false);

                yesButton.addActionListener(new ActionListener() {
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
        });

        frame.add(choice);

        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
       System.out.println("Button pressed");
       text = textArea.getText();
       filename = fname.getText();
       
       if(filename.isEmpty()){
            System.out.println("null");
            Frame warningFrame = new Frame("WARNING");
            Label warningLabel = new Label("File name can't be empty");
            warningLabel.setBounds(20, 40, 150,50);
            warningFrame.setSize(200, 100);
            warningFrame.setVisible(true);
            warningFrame.setLayout(null);
            warningFrame.add(warningLabel);
            warningFrame.setResizable(false);
            try{
                Thread.sleep(2000);
                warningFrame.dispose();
            }catch(Exception error){
                System.out.println("Error in warning");
            }
            warningFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                warningFrame.dispose();
               
                
            }
            });
       }
       else{
        System.out.println(filename);
        writeToFile(text);
       }
        
    }
    public void writeToFile(String text){
        if (text == null){
            System.out.println("Invalid stream");
            return;
        }else{
            System.out.println(text);
        try{
        FileWriter output = new FileWriter(filename);
            output.write(text);
            output.flush();
            output.close();

    
            Frame completeMessageFrame = new Frame("SUCCESS");
            Label completeMessageLabel = new Label("File created succesfully");
            completeMessageLabel.setBounds(20, 20, 150,50);
            completeMessageFrame.setSize(200, 100);
            completeMessageFrame.setVisible(true);
            completeMessageFrame.setLayout(null);
            completeMessageFrame.add(completeMessageLabel);
            try{
                Thread.sleep(2000);
                completeMessageFrame.dispose();
            }catch(Exception error){
                System.out.println("Error in warning");
            }
            completeMessageFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                completeMessageFrame.dispose();
               
        }});
            
                
            }
           

        
        catch(Exception exception){
            System.out.println("Exception caught");
        }
        
    }
}

}
