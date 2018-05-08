package winApi.threads;

import com.sun.jna.Callback;
import com.sun.jna.platform.win32.WinNT;
import controllers.CreateNewEntryFormController;
import converters.Converter;
import db.DataBaseInsertHelper;
import winApi.ApiHelper;
import winApi.MyKernel32;

public class FillDataBase implements Callback {

    /**
     * Вызывает методы для работы с заполнением базы данных.
     */
    public void callback(){
        WinNT.HANDLE handle = ApiHelper.enterMutexOrSemaphore(2);
        CreateNewEntryFormController.saveLog("Hi, it's new thread, id: " + MyKernel32.INSTANCE.GetCurrentThreadId());
        CreateNewEntryFormController.saveLog("Filling data base...");
        DataBaseInsertHelper dataBaseInsertHelper = new DataBaseInsertHelper();

        CreateNewEntryFormController.setPathToSVG(new Converter().parseFile(CreateNewEntryFormController.getPathToDXF()));

        dataBaseInsertHelper.fillDataBase(CreateNewEntryFormController.getTypeId(),
                CreateNewEntryFormController.getPathToXML(),
                CreateNewEntryFormController.getPathToDXF(),
                CreateNewEntryFormController.getPathToMainFile(),
                CreateNewEntryFormController.getPathToTXT(),
                CreateNewEntryFormController.getName(),
                CreateNewEntryFormController.getPathToSVG());

        ApiHelper.leaveMutexOrSemaphore(handle);

        CreateNewEntryFormController.saveLog("Done!");
    }
}
