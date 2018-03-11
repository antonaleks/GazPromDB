package winApi.threads;

import com.sun.jna.Callback;
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
        MyKernel32.INSTANCE.EnterCriticalSection(ApiHelper.criticalSection);
        System.out.println("Hi, it's new thread, id: " + MyKernel32.INSTANCE.GetCurrentThreadId());
        System.out.println("Filling data base...");
        DataBaseInsertHelper dataBaseInsertHelper = new DataBaseInsertHelper();

        CreateNewEntryFormController.setPathToSVG(new Converter().parseFile(CreateNewEntryFormController.getPathToDXF()));

        dataBaseInsertHelper.fillDataBase(CreateNewEntryFormController.getTypeId(),
                CreateNewEntryFormController.getPathToXML(),
                CreateNewEntryFormController.getPathToDXF(),
                CreateNewEntryFormController.getPathToMainFile(),
                CreateNewEntryFormController.getPathToTXT(),
                CreateNewEntryFormController.getName(),
                CreateNewEntryFormController.getPathToSVG());

        System.out.println("Done!");
        MyKernel32.INSTANCE.LeaveCriticalSection(ApiHelper.criticalSection);
    }
}
