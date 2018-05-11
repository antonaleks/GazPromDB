package winApi.threads;

import com.sun.jna.Callback;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import controllers.CreateNewEntryFormController;
import controllers.ObjGroupController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import winApi.MyKernel32;

import java.util.Optional;

public class CheckFolder implements Callback {
    /**
     * Вызывает методы для работы с созданием json файлов.
     */
    int FILE_LIST_DIRECTORY = 0x1;
    int FILE_SHARE_READ = 0x1;
    int FILE_SHARE_WRITE = 0x2;
    int FILE_SHARE_DELETE = 0x4;
    int OPEN_EXISTING = 3;
    int FILE_FLAG_BACKUP_SEMANTICS = 0x2000000;
    int FILE_NOTIFY_CHANGE_FILE_NAME = 0x1;
    int FILE_NOTIFY_CHANGE_DIR_NAME = 0x2;
    int FILE_NOTIFY_CHANGE_LAST_WRITE = 0x10;

    public void callback() {

        int BUFSIZE = 2048;
        String myDocs = "C:\\subd\\GazPromDB\\src\\main\\resources\\chemcad_models";

        System.out.println("Monitoring name changes in " + myDocs + " and subdirectories.");
        while (true) {
            WinNT.FILE_NOTIFY_INFORMATION pBuf = new WinNT.FILE_NOTIFY_INFORMATION(2048);
            WinNT.HANDLE hDir = Kernel32.INSTANCE.CreateFile(myDocs, FILE_LIST_DIRECTORY, FILE_SHARE_READ | FILE_SHARE_WRITE |
                    FILE_SHARE_DELETE, null, OPEN_EXISTING, FILE_FLAG_BACKUP_SEMANTICS, null);

            try {
                IntByReference bytesReturned = new IntByReference();
                if (MyKernel32.INSTANCE.ReadDirectoryChangesW(hDir, pBuf, BUFSIZE, true, FILE_NOTIFY_CHANGE_FILE_NAME |
                                FILE_NOTIFY_CHANGE_DIR_NAME | FILE_NOTIFY_CHANGE_LAST_WRITE, bytesReturned,
                        null, null)) {
                    String[] actions = new String[]{"(unknown action) ", "Added ", "Removed ",
                            "Modified ", "Old name ", "New name "};
                    WinNT.FILE_NOTIFY_INFORMATION pCurrent = pBuf;
                    while (pCurrent != null) {
                        // Read file length (in bytes) at offset 8
                        int fileLen = pCurrent.FileNameLength;
                        // Read file name (fileLen/2 characters) from offset 12
                        String file = pCurrent.getFilename();
                        // Read action at offset 4
                        int action = pCurrent.Action;
                        if (action < 1 || action >= actions.length) action = 0;
                        System.out.println((actions[action] + file));
                        CreateNewEntryFormController.saveLog((actions[action] + file));
                        CreateNewEntryFormController.setFileChange(true);
                        CreateNewEntryFormController.setFileChangeName(file);

                        // Read NextEntryOffset at offset 0 and move pointer to next structure if needed
                        int inc = pCurrent.NextEntryOffset;
                        pCurrent = inc != 0 ? pCurrent.next() : null;
                    }
                } else
                    System.out.println("ReadDirectoryChangesW failed. ");
            } finally {
                MyKernel32.INSTANCE.CloseHandle(hDir);
            }
        }

    }
}
