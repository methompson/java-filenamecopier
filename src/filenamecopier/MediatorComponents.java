/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filenamecopier;

import java.awt.*;
import java.util.List;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
//import javax.swing.filechooser.FileFilter;
//import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Mat
 */


class Logger extends JFrame{
    
    Mediator med;
    JTextArea log = new JTextArea(7, 20);
    JPanel panel = new JPanel();
    
    Logger() {
        panel.add(log);
        this.add(panel);
        this.pack();
    }
    
    void addLog(String entry) {
        log.append(entry + "\n");
    }
}

/************************************************************************
 *                          File Name Classes
************************************************************************/

class FBatchTxtArea extends JTextArea {
    Mediator med;
    
    FBatchTxtArea(Mediator m) {
        super (14,40);
        med = m;
        med.getFileMed().registerFBatchTxt(this);
    }
}

class FilesList extends JList {
    
    Mediator med;
    DefaultListModel listModel;
    
    FilesList(Mediator m) {
        super(new DefaultListModel());
        listModel = new DefaultListModel();
        this.setModel(listModel);
        this.setDragEnabled(true);
        this.setTransferHandler(new FileTransferHandler());
        med = m;
        med.getFileMed().registerList(this);
    }

    
    DefaultListModel getList() {
        return listModel;
    }
    
    void clearList() {
        listModel.clear();
    }
}

class FClearBtn extends JButton implements Command {
    
    Mediator med;
    
    FClearBtn(ActionListener al, Mediator m) {
        super("Clear List");
        addActionListener(al);
        med = m;
        med.getFileMed().registerFClear(this);
    }
    
    public void execute() {
        med.getFileMed().fileClear();
    }
}

class FBrowseBatchLocBtn extends JButton implements Command {
    
    Mediator med;
    
    FBrowseBatchLocBtn(ActionListener al, Mediator m) {
        super("Browse");
        addActionListener(al);
        med = m;
        med.getFileMed().registerFBrowseBatchLoc(this);
    }
    
    public void execute() {
        med.getFileMed().fBrowseBatchLoc();
    }
}

class FBatchLocTxt extends JTextField {
    
    Mediator med;
    
    FBatchLocTxt(Mediator m) {
        super(24);
        med = m;
        med.getFileMed().registerFBatchLoc(this);
    }
}

class FBatchBtn extends JButton implements Command {
    
    Mediator med;
    
    FBatchBtn(ActionListener al, Mediator m) {
        super("Create Batch File");
        addActionListener(al);
        med = m;
        med.getFileMed().registerFBatch(this);
    }
    
    public void execute() {
        med.getFileMed().fBatch();
    }
}

class FShowBatchBtn extends JButton implements Command {
    
    Mediator med;
    
    FShowBatchBtn(ActionListener al, Mediator m) {
        super("Show Batch");
        addActionListener(al);
        med = m;
        med.getFileMed().registerFShowBatch(this);
    }
    
    public void execute(){
        med.getFileMed().fShowBatch();
    }
    
}

class FWildcardTxt extends JTextField {
    
    Mediator med;
    
    FWildcardTxt(Mediator m) {
        super(24);
        this.setText("*file*");
        med = m;
        med.getFileMed().registerFWildcardTxt(this);
    }
}

class FDoubleSlashCheck extends JCheckBox {
    Mediator med;
    
    FDoubleSlashCheck(Mediator m) {
        super("Use Double Slashes in Filename Pathes", false);
        med = m;
        med.getFileMed().registerFDoubleSlash(this);
    }
}

class FPauseCheck extends JCheckBox {
    Mediator med;
    
    FPauseCheck(Mediator m) {
        super("Add a pause at the end", true);
        med = m;
        med.getFileMed().registerFPause(this);
    }
}

/************************************************************************
 *                          Number Counter Classes
************************************************************************/

class CBatchTxtArea extends JTextArea {
    Mediator med;
    
    CBatchTxtArea(Mediator m) {
        super (14,40);
        med = m;
        med.getCounterMed().registerCBatchTxt(this);
    }
}

class CBeginTxt extends JTextField {
    
    Mediator med;
    
    CBeginTxt(Mediator m) {
        super(24);
        med = m;
        med.getCounterMed().registerCBeginTxt(this);
    }
}

class CEndTxt extends JTextField {
    
    Mediator med;
    
    CEndTxt(Mediator m) {
        super(24);
        med = m;
        med.getCounterMed().registerCEndTxt(this);
    }
}

class CWildcardTxt extends JTextField {
    
    Mediator med;
    
    CWildcardTxt(Mediator m) {
        super(24);
        this.setText("*num*");
        med = m;
        med.getCounterMed().registerCWildcardTxt(this);
    }
}

class CBatchLocTxt extends JTextField {
    
    Mediator med;
    
    CBatchLocTxt(Mediator m) {
        super(24);
        med = m;
        med.getCounterMed().registerCBatchLoc(this);
    }
}

class CBrowseBatchLocBtn extends JButton implements Command {
    
    Mediator med;
    
    CBrowseBatchLocBtn(ActionListener al, Mediator m) {
        super("Browse");
        addActionListener(al);
        med = m;
        med.getCounterMed().registerCBrowseBatchLoc(this);
    }
    
    public void execute() {
        med.getCounterMed().cBrowseBatchLoc();
    }
}

class CBatchBtn extends JButton implements Command {
    
    Mediator med;
    
    CBatchBtn(ActionListener al, Mediator m) {
        super("Create Batch File");
        addActionListener(al);
        med = m;
        med.getCounterMed().registerCBatch(this);
    }
    
    public void execute() {
        med.getCounterMed().cBatch();
    }
}

class CShowBatchBtn extends JButton implements Command {
    
    Mediator med;
    
    CShowBatchBtn(ActionListener al, Mediator m) {
        super("Show Batch");
        addActionListener(al);
        med = m;
        med.getCounterMed().registerCShowBatch(this);
    }
    
    public void execute(){
        med.getCounterMed().cShowBatch();
    }
    
}

class CPauseCheck extends JCheckBox {
    Mediator med;
    
    CPauseCheck(Mediator m) {
        super("Add a pause at the end", true);
        med = m;
        med.getCounterMed().registerCPause(this);
    }
}

/************************************************************************
 *                          Batch Output Classes
************************************************************************/

class BatchOutput extends JTextArea {
    Mediator med;
    
    BatchOutput(Mediator m) {
        super(14,40);
        med = m;
        med.registerBatchOutput(this);
    }
}

class BatchClearBtn extends JButton implements Command {
    Mediator med;
    
    BatchClearBtn(ActionListener al, Mediator m) {
        super("Clear Text");
        addActionListener(al);
        med = m;
        med.registerBatchClearBtn(this);
    }
    
    public void execute(){
        med.clearBatchOutput();
    }
}