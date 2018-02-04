package mhashim6.sadb.ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import mhashim6.sadb.exceptions.FileFailException;

/**
 * @author mhashim6
 */
@SuppressWarnings("serial")
class FileSaveDialog extends JFileChooser {

    public FileSaveDialog(MainUI frame, String whatToSave) throws FileFailException {

        setDialogTitle("Save AS");
        setFileFilter(new FileNameExtensionFilter(".log, .txt", "log", "txt"));

        int userSelection = showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {

            File file = getSelectedFile();

            try {
                file.createNewFile();
            } catch (IOException e1) {
                throw new FileFailException(file.getName());
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(whatToSave);
            } catch (IOException e) {
                throw new FileFailException(file.getName());

            }

        }

    }

}
