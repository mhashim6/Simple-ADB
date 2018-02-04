package mhashim6.sadb.ui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author mhashim6
 */
@SuppressWarnings("serial")
class FileExplorer extends JFileChooser {

    private String fileName;

    FileExplorer(MainUI frame) {
        setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        setApproveButtonText("Select");
        setFileFilter(new FileNameExtensionFilter("Directories,flashable files & apk's", "apk", "img", "zip"));

        int filesMenu = showOpenDialog(frame);
        if (filesMenu == JFileChooser.APPROVE_OPTION) {
            setFileName(getSelectedFile().getPath());
        }

    }

    public String getFileName() {
        return fileName;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }
}