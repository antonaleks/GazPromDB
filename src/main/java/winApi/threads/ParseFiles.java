package winApi.threads;

import com.sun.jna.Callback;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import controllers.CreateNewEntryFormController;
import entity.Component;
import entity.EnergyBalance;
import entity.MassBalance;
import entity.StreamsElement;
import org.apache.commons.lang3.SerializationUtils;
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
            List<StreamsElement> elements = txtParser.parseStreams(txtInput, components);
            CreateNewEntryFormController.setStreamsElements(elements);
            byte[] sizeElements= SerializationUtils.serialize(elements.size());
            IntByReference dwWritt = new IntByReference();
            CreateNewEntryFormController.saveLog("Writing data to pipes...");
            MyKernel32.INSTANCE.WriteFile(ApiHelper.pipeInWrite.getValue(), sizeElements, sizeElements.length, dwWritt, null);

            for (int i = 0; i < elements.size(); i++) {
                IntByReference dwWritten = new IntByReference();
                byte[] dataElem = SerializationUtils.serialize(elements.get(i));
                byte[] size = SerializationUtils.serialize(dataElem.length);
                MyKernel32.INSTANCE.WriteFile(ApiHelper.pipeInWrite.getValue(), size, size.length, dwWritten, null);
                MyKernel32.INSTANCE.WriteFile(ApiHelper.pipeDataWrite.getValue(), dataElem, dataElem.length, dwWritten, null);
            }
            CreateNewEntryFormController.saveLog("Data has been writing to pipes");
        }
        ApiHelper.leaveMutexOrSemaphore(handle);
        CreateNewEntryFormController.saveLog("Done!");


    }
}
