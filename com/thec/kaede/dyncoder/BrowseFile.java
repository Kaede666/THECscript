import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//Made by Kaede666

public class BrowseFile extends JFrame implements ActionListener {
    JButton openButton, RunButton;
    JTextArea log;
    JFileChooser fc;
    File file;
    final boolean DEBUG = true;
    
    public boolean isReady = false, reload = false;
    
    public BrowseFile() {
        super();
        
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        fc = new JFileChooser();
        if (DEBUG) fc.setCurrentDirectory(new File("C:\\Users\\William\\Dropbox\\NetBeansProjects\\RSBot\\build\\classes"));

        openButton = new JButton("Chose Main Class");
        openButton.addActionListener(this);

        RunButton = new JButton("Run Class");
        RunButton.addActionListener(this);
        RunButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(RunButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
    
    public void addLog(String s){
        log.append(s + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }
    
    public File getClassFile(){
        return file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            if (openButton.getText().equalsIgnoreCase("Chose Main Class")) {
                int returnVal = fc.showOpenDialog(BrowseFile.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    addLog("Opening: " + file.getName() + ".");
                    if (!file.getName().contains(".class")){
                         addLog("Not a class file! Be careful.");
                    }
                    if (!RunButton.isEnabled()) RunButton.setEnabled(true);
                } else {
                     addLog("Open cancelled by user.");
                }
                
            }else if(openButton.getText().equalsIgnoreCase("Stop")) {
                RunButton.setText("Run Class");
                openButton.setText("Chose Main Class");
                isReady = false;
            }
            
        } else if (e.getSource() == RunButton) {
            if (RunButton.getText().equalsIgnoreCase("Run Class")) {
                RunButton.setText("Reload Class");
                openButton.setText("Stop");
                isReady = true;
            }else if (RunButton.getText().equalsIgnoreCase("Reload Class")) {
                reload = true;
            }
        }
    }
}