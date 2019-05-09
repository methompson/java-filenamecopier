/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filenamecopier;

import java.io.*;
import javax.swing.*;
/**
 *
 * @author Mat
 */
public class BatchActions {
    
    public static void fSaveToBatch(String[] files, File batchLoc, String batchCommands, String wildcard, boolean doubleSlashes, boolean pause) {
        PrintWriter out;
        String line;
        
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(batchLoc)));
            for (String s : files) {
                if (doubleSlashes) {
                    s = s.replace("\\", "\\\\");
                    s = s.replace("/", "//");
                }
                line = batchCommands.replace(wildcard, s);
                
                line = newLineReplace(line);
                
                //System.out.println("Index of \\n: " + line.indexOf("\n") + " Index of \\r: " + line.indexOf("\r"));
                out.println(line + "\r\n");
            }
            
            if (pause){
                out.println("pause");
            }
            
            out.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
            JOptionPane.showMessageDialog(new JFrame(), "Failed to save file");
        }
    }
    
    public static void cSaveToBatch(File batchLoc, int begin, int end, String batchCommands, String wildcard, boolean pause){
        PrintWriter out;
        String line;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(batchLoc)));
            if (begin > end) {
                for (int i = end; i <= begin; i++) {
                    line = batchCommands.replace(wildcard, Integer.toString(i));
                    line = newLineReplace(line);
                    out.println(line + "\r\n");
                }
            } else {
                for (int i = begin; i <= end; i++) {
                    line = batchCommands.replace(wildcard, Integer.toString(i));
                    line = newLineReplace(line);
                    out.println(line + "\r\n");
                }
            }
            
            if (pause){
                out.println("pause");
            }
            
            out.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
            JOptionPane.showMessageDialog(new JFrame(), "Failed to save file");
        }
    }
    
    public static void fShowOutput(BatchOutput output, String[] files, String batchCommands, String wildcard, boolean doubleSlashes, boolean pause) {
        String line;
        
        //output.setText("");
        
        for (String s : files) {
            if (doubleSlashes) {
                s = s.replace("\\", "\\\\");
                s = s.replace("/", "//");
            }
            line = batchCommands.replace(wildcard, s);

            line = newLineReplace(line);

            //System.out.println("Index of \\n: " + line.indexOf("\n") + " Index of \\r: " + line.indexOf("\r"));
            output.append(line + "\r\n\r\n");
        }

        if (pause){
            output.append("pause");
        }
        output.append("\r\n");
    }
    
    public static void cShowOutput(BatchOutput output, int begin, int end, String batchCommands, String wildcard, boolean pause){
        String line;
        //output.setText("");
        
        if (begin > end) {
            for (int i = end; i <= begin; i++) {
                line = batchCommands.replace(wildcard, Integer.toString(i));
                line = newLineReplace(line);
                output.append(line + "\r\n\r\n");
            }
        } else {
            for (int i = begin; i <= end; i++) {
                line = batchCommands.replace(wildcard, Integer.toString(i));
                line = newLineReplace(line);
                output.append(line + "\r\n\r\n");
            }
        }

        if (pause){
            output.append("pause");
        }
        output.append("\r\n");
    }
    
    public static String newLineReplace(String text){
        text = text.replace("\n\r", "\r\n");
                
        text = text.replace("\r", "\r\n");
        text = text.replace("\r\n\n", "\r\n");
        text = text.replace("\n", "\r\n");
        text = text.replace("\r\r\n", "\r\n");
        return text;
    }
}
