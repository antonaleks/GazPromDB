package winApi.threads;

import com.sun.jna.Callback;
import com.sun.jna.platform.win32.WinNT;
import controllers.CreateNewEntryFormController;
import entity.Component;
import entity.EnergyBalance;
import entity.MassBalance;
import entity.StreamsElement;
import txtParsers.Parser;
import winApi.ApiHelper;
import winApi.MyKernel32;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ParseFiles implements Callback {

    /**
     * Вызывает методы для работы с парсингом файлов.
     */
    public void callback() throws SQLException, IOException {
        WinNT.HANDLE handle = ApiHelper.enterMutexOrSemaphore(0);
        CreateNewEntryFormController.saveLog("Hi, it's new thread, id: " + MyKernel32.INSTANCE.GetCurrentThreadId());
        CreateNewEntryFormController.saveLog("Parsing files...");
        Parser txtParser = new Parser();
        String txtInput = txtParser.parseTxtReport(CreateNewEntryFormController.getPathToTXT());
        if (txtInput != null) {
            List<Component> components = txtParser.parseComponent(txtInput);
            CreateNewEntryFormController.setStreamsElements(txtParser.parseStreams(txtInput, components));

        }
        ApiHelper.leaveMutexOrSemaphore(handle);
        CreateNewEntryFormController.saveLog("Done!");


    }
}
