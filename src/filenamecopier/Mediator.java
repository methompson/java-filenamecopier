/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filenamecopier;

import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Mat
 */
public class Mediator {
    Logger logger = new Logger();
    
    FileMediator fileMed;
    CounterMediator counterMed;
    
    BatchOutput batchOutput;
    BatchClearBtn bClearBtn;
    
    Mediator(){
        this.fileMed = new FileMediator(this);
        this.counterMed = new CounterMediator(this);
    }
    
    //Registration
    void registerBatchOutput(BatchOutput t) {
        this.batchOutput = t;
    }
    
    void registerBatchClearBtn(BatchClearBtn b){
        this.bClearBtn = b;
    }
    
    //Get Methods
    public FileMediator getFileMed(){
        return this.fileMed;
    }
    
    public CounterMediator getCounterMed(){
        return this.counterMed;
    }
    
    public Logger getLogger(){
        return this.logger;
    }
    
    public BatchOutput getBatchOutput() {
        return this.batchOutput;
    }
        
    //Methods
    String browseBatchLoc(String batchLocTxt) {
        File batchLoc = new File(batchLocTxt);
        JFileChooser chooser = new JFileChooser(batchLoc);
        
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FileFilter type1 = new FileNameExtensionFilter("Batch Files", "bat");
        chooser.addChoosableFileFilter(type1);
        int returnVal = chooser.showSaveDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File batchFile;
            
            if (chooser.getSelectedFile().getName().endsWith(".bat")){
                batchFile = chooser.getSelectedFile();
            } else {
                batchFile = new File(chooser.getSelectedFile().getAbsolutePath() + ".bat");
            }
            return batchFile.getAbsolutePath();
        } else {
            return null;
        }          
    }
    
    void clearBatchOutput(){
        this.batchOutput.setText("");
    }
    
    void showLog() {
        logger.setVisible(true);
    }

}

class FileMediator {
    
    Mediator parentMed;
    
    FilesList filesList;
    FClearBtn fClearBtn;
    FBrowseBatchLocBtn fBrowseBatchLocBtn;
    FBatchBtn fBatchBtn;
    FShowBatchBtn fShowBatchBtn;
    
    FBatchLocTxt fBatchLocTxt;
    FBatchTxtArea fBatchTxtArea;
    FWildcardTxt fWildcardTxt;
    
    FDoubleSlashCheck fDoubleSlashCheck;
    FPauseCheck fPauseCheck;
    
    FileMediator(Mediator m){
        this.parentMed = m;
    }
    
    void registerList(FilesList l){
        filesList = l;
        DefaultListModel listModel = (DefaultListModel) filesList.getList();
    }
    
    void registerFClear(FClearBtn b) {
        fClearBtn = b;
    }
    
    void registerFBrowseBatchLoc(FBrowseBatchLocBtn b) {
        fBrowseBatchLocBtn = b;
    }
    
    void registerFBatch(FBatchBtn b) {
        fBatchBtn = b;
    }
    
    void registerFShowBatch(FShowBatchBtn b){
        fShowBatchBtn = b;
    }
    
    void registerFBatchLoc(FBatchLocTxt t) {
        fBatchLocTxt = t;
    }
    
    void registerFBatchTxt(FBatchTxtArea t){
        fBatchTxtArea = t;
    }
    
    void registerFWildcardTxt(FWildcardTxt t) {
        fWildcardTxt = t;
    }
    
    void registerFDoubleSlash(FDoubleSlashCheck c) {
        fDoubleSlashCheck = c;
    }
    
    void registerFPause(FPauseCheck c) {
        fPauseCheck = c;
    }
    
    //Methods
    
    void fileClear() {
        DefaultListModel listModel = (DefaultListModel) filesList.getList();
        listModel.clear();
    }
    
    void fBrowseBatchLoc() {
        fBatchLocTxt.setText(parentMed.browseBatchLoc(fBatchLocTxt.getText()));
    }
    
    void fShowBatch() {
       //Retrieves files from list in array form. Creates new String array
        //Adds each file name as a String to the String array.
        Object[] list = filesList.getList().toArray();
        String[] files = new String[list.length];
        for (int x = 0; x < list.length; x++){
            files[x] = list[x].toString();
        }
        
        //Error checking: Whether batch text is actually used, whether they have
        //a wild card and whether any files have been selected.
        if (fBatchTxtArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Have Batch Text");
            parentMed.getLogger().addLog("Batch Text Area Empty");
        } else if (fWildcardTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Have a Wildcard set For the Batch");
            parentMed.getLogger().addLog("Wildcard Empty");
        } else if (files.length < 1) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Select Files");
            parentMed.getLogger().addLog("Not Files Selected");
        } else {
            BatchActions.fShowOutput(parentMed.getBatchOutput(), files, fBatchTxtArea.getText(), fWildcardTxt.getText(), fDoubleSlashCheck.isSelected(), fPauseCheck.isSelected());
        }
    }
    
    void fBatch() {
        //Retrieves files from list in array form. Creates new String array
        //Adds each file name as a String to the String array.
        Object[] list = filesList.getList().toArray();
        String[] files = new String[list.length];
        for (int x = 0; x < list.length; x++){
            files[x] = list[x].toString();
        }

        //Error checking: Whether the user has a file save location, whether batch
        //text is actually used, whether they have a wild card and whether any files
        //have been selected.
        if (fBatchLocTxt.getText().isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Select a Location to Save the Batch File");
            parentMed.getLogger().addLog("Batch File Empty");
        } else if (fBatchTxtArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Have Batch Text");
            parentMed.getLogger().addLog("Batch Text Area Empty");
        } else if (fWildcardTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Have a Wildcard set For the Batch");
            parentMed.getLogger().addLog("Wildcard Empty");
        } else if (files.length < 1) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Select Files");
            parentMed.getLogger().addLog("Not Files Selected");
        } else {
            BatchActions.fSaveToBatch(files, new File(fBatchLocTxt.getText()), fBatchTxtArea.getText(), fWildcardTxt.getText(), fDoubleSlashCheck.isSelected(), fPauseCheck.isSelected());
        }
    }
}

class CounterMediator {
    Mediator parentMed;
    
    CBatchTxtArea cBatchTxtArea;
    CBeginTxt cBeginTxt;
    CEndTxt cEndTxt;
    CWildcardTxt cWildcardTxt;
    CBatchLocTxt cBatchLocTxt;
    
    CBrowseBatchLocBtn cBrowseBatchLocBtn;
    CBatchBtn cBatchBtn;
    CShowBatchBtn cShowBatchBtn;
    
    CPauseCheck cPauseCheck;
    
    CounterMediator(Mediator m){
        this.parentMed = m;
    }
    
    //Registration
    void registerCBatchTxt(CBatchTxtArea t) {
        cBatchTxtArea = t;
    }
    
    void registerCBeginTxt(CBeginTxt t) {
        cBeginTxt = t;
    }
    
    void registerCEndTxt(CEndTxt t) {
        cEndTxt = t;
    }
    
    void registerCWildcardTxt(CWildcardTxt t) {
        cWildcardTxt = t;
    }
    
    void registerCBatchLoc(CBatchLocTxt t) {
        cBatchLocTxt = t;
    }
    
    void registerCBrowseBatchLoc(CBrowseBatchLocBtn b) {
        cBrowseBatchLocBtn = b;
    }
    
    void registerCShowBatch(CShowBatchBtn b) {
        cShowBatchBtn = b;
    }
    
    void registerCBatch(CBatchBtn b) {
        cBatchBtn = b;
    }
    
    void registerCPause(CPauseCheck c) {
        cPauseCheck = c;
    }
    
    //Methods
    
    void cBrowseBatchLoc() {
        cBatchLocTxt.setText(parentMed.browseBatchLoc(cBatchLocTxt.getText()));
    }
    
    void cBatch() {
        
        if (cBeginTxt.getText().isEmpty() || cEndTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You must set both the beginning and ending number");
        } else if (cBatchLocTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Select a Location to Save the Batch File");
        } else if (cWildcardTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Have a Wildcard set For the Batch");
        } else if (cBatchTxtArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Have Batch Text");
        } else {
            BatchActions.cSaveToBatch(new File(cBatchLocTxt.getText()),
                    Integer.parseInt(cBeginTxt.getText()), Integer.parseInt(cEndTxt.getText()),
                    cBatchTxtArea.getText(), cWildcardTxt.getText(), cPauseCheck.isSelected());
        }
        
    }
    
    void cShowBatch() {
        if (cBeginTxt.getText().isEmpty() || cEndTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You must set both the beginning and ending number");
        } else if (cWildcardTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Have a Wildcard set For the Batch");
        } else if (cBatchTxtArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You Need to Have Batch Text");
        } else {
            BatchActions.cShowOutput(parentMed.getBatchOutput(),
                    Integer.parseInt(cBeginTxt.getText()), Integer.parseInt(cEndTxt.getText()),
                    cBatchTxtArea.getText(), cWildcardTxt.getText(), cPauseCheck.isSelected());
        }

    }
}
