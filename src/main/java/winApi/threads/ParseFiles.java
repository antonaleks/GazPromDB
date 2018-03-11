package winApi.threads;

import com.sun.jna.Callback;
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
        MyKernel32.INSTANCE.EnterCriticalSection(ApiHelper.criticalSection);
        System.out.println("Hi, it's new thread, id: " + MyKernel32.INSTANCE.GetCurrentThreadId());
        System.out.println("Parsing files...");
        Parser txtParser = new Parser();
        String txtInput = txtParser.parseTxtReport(CreateNewEntryFormController.getPathToTXT());
        if (txtInput != null) {
            List<Component> components = txtParser.parseComponent(txtInput);
            CreateNewEntryFormController.setStreamsElements(txtParser.parseStreams(txtInput, components));
            System.out.println("Done!");
        }
        MyKernel32.INSTANCE.LeaveCriticalSection(ApiHelper.criticalSection);

    }
}
