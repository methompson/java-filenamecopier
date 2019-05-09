/*
 * TODO:
 * Bugs
 * - Fix GUI issues with expanding lists that expand slightly. Add permanent
 * scroll bars (FIXED)
 * - Add both /r and /n to each new line in batch output (FIXED)
 * 
 * Features
 * - Add option to open up Window and display the batch outcome. Make the outcome editable
 * - Add button to batch viewer to run the batches (negating the need to save the bat file
 * - Add button to batch viewer to save edited outcome as a batch file
 * - Add a clear button
 * - Make buttons append rather than clear and add
 * 
 * - Allow Folders to be added and the option to add either folders, files or both
 * 
 * - Automatically Save options
 * - Batch command saver
 * 
 */
package filenamecopier;

import java.awt.*;
import java.util.List;
import java.io.*;
//import javax.swing.filechooser.FileFilter;
//import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mat
 */

interface Command {
    void execute();
}

public class FileNameCopier extends JFrame implements ActionListener, ChangeListener {

    Mediator med = new Mediator();
    
    public static void main(String[] args) {
        new FileNameCopier();
    }
    
    FileNameCopier() {
        this.setTitle("File Name Copier");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        //JPanel fileNamePanel = new JPanel();
        //fileNamePanel.add(new FileNamePanel(med));
        
        FileNamePanel fileNamePanel = new FileNamePanel(med);
        
        //JPanel numberPanel = new JPanel();
        //numberPanel.add(new NumberPanel(med));
        
        NumberPanel numberPanel = new NumberPanel(med);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(this);
        tabbedPane.add("By File Name", fileNamePanel);
        tabbedPane.add("Counter", numberPanel);
        
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scrollPane = new JScrollPane(new BatchOutput(med), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        resultPanel.add(scrollPane);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new BatchClearBtn(this, med));
        buttonPanel.add(new JButton("Save"));
        buttonPanel.add(new JButton("Run"));
        
        resultPanel.add(buttonPanel);
        
        
        JPanel allPanels = new JPanel();
        allPanels.add(tabbedPane);
        allPanels.add(resultPanel);
        
        //this.add(tabbedPane);
        this.add(allPanels);
        this.pack();
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        Command comd = (Command) ae.getSource();
        comd.execute();
        
        //System.out.println(ae.getSource());
    }

    public void stateChanged(ChangeEvent changeEvent) {
        
        
    }
    
}


class FileNamePanel extends JPanel implements ActionListener {
    
    FileNamePanel(Mediator med) {
        JLabel label;
        
        this.setLayout(new GridLayout2(0,2, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        label = new JLabel("Files List");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(label);
        
        FilesList fileList = new FilesList(med);
        
        JScrollPane scrollPane = new JScrollPane(fileList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(100, 150));
        
        this.add(scrollPane);
        
        JPanel browsePanel = new JPanel();
        browsePanel.setLayout(new GridLayout2(0,2, 10, 10));
        browsePanel.add(new FBrowseBatchLocBtn(this, med));
        browsePanel.add(new FBatchLocTxt(med));
        
        label = new JLabel("Batch File Location");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(label);
        this.add(browsePanel);
        
        label = new JLabel("Text for each file");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(label);
        
        scrollPane = new JScrollPane(new FBatchTxtArea(med), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        
        label = new JLabel("Wildcard");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(label);
        this.add(new FWildcardTxt(med));
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
        optionsPanel.add(new FDoubleSlashCheck(med));
        optionsPanel.add(new FPauseCheck(med));
        
        this.add(new JLabel(""));
        this.add(optionsPanel);
                
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(new FClearBtn(this, med));
        buttonsPanel.add(new FBatchBtn(this, med));
        buttonsPanel.add(new FShowBatchBtn(this, med));

        this.add(new JLabel(""));
        this.add(buttonsPanel);
        
        
    }
    
    public void actionPerformed(ActionEvent ae) {
        Command comd = (Command) ae.getSource();
        comd.execute();
    }
    
}

class NumberPanel extends JPanel implements ActionListener {
    NumberPanel(Mediator med) {
        JLabel label;
        
        this.setLayout(new GridLayout2(0, 2, 10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        label = new JLabel("Beginning Number");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(label);
        this.add(new CBeginTxt(med));
        
        label = new JLabel("Ending Number");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(label);
        this.add(new CEndTxt(med));
        
        JPanel browsePanel = new JPanel();
        browsePanel.setLayout(new GridLayout2(0,2, 10, 10));
        browsePanel.add(new CBrowseBatchLocBtn(this, med));
        browsePanel.add(new CBatchLocTxt(med));
        
        label = new JLabel("Batch File Location");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(label);
        this.add(browsePanel);

        label = new JLabel("Text for each file");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(label);
        this.add(new JScrollPane(new CBatchTxtArea(med), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        
        label = new JLabel("Wildcard");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(label);
        this.add(new CWildcardTxt(med));
        
        this.add(new JLabel(""));
        this.add(new CPauseCheck(med));
        
        this.add(new JLabel(""));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(new CBatchBtn(this, med));
        buttonsPanel.add(new CShowBatchBtn(this, med));
        this.add(buttonsPanel);
    }
    
    public void actionPerformed(ActionEvent ae) {
        Command comd = (Command) ae.getSource();
        comd.execute();
    }
}