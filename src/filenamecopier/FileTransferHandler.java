/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filenamecopier;
/**
 *
 * @author Mat
 */

import java.util.List;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.io.*;

public class FileTransferHandler extends TransferHandler {
    public boolean canImport(TransferHandler.TransferSupport info){
        if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
            return false;
        }
        
        return true;
    }
    
    public boolean importData(TransferHandler.TransferSupport data) {
        Transferable t = data.getTransferable();

        try {
            
            JList list = (JList)data.getComponent();
            DefaultListModel listModel = (DefaultListModel)list.getModel();
            
            List<File> l = (List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);
            
            //Set to only accept AC3 and DTS files for now.
            for (File f : l) {
                if (!f.isDirectory() && !listModel.contains(f.getAbsolutePath())){
                    if (f.getName().length() >= 4) {
                        
                        /*if (f.getName().substring(f.getName().length()-4).equals(".ac3") || f.getName().substring(f.getName().length()-4).equals(".dts")) {
                            listModel.addElement(f.getAbsolutePath());
                        }
                        if (f.getName().endsWith(".ac3") || f.getName().endsWith(".dts")) {
                            listModel.addElement(f.getAbsolutePath());
                        }*/
                        listModel.addElement(f.getAbsolutePath());
                    }
                }
            }
               
        } catch (UnsupportedFlavorException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }
        
        
        
    
}
