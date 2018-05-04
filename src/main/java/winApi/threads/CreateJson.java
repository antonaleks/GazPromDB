package winApi.threads;

import com.sun.jna.Callback;
import com.sun.jna.platform.win32.WinNT;
import controllers.CreateNewEntryFormController;
import properties.JsonManager;
import winApi.ApiHelper;
import winApi.MyKernel32;

public class CreateJson implements Callback {

    /**
     * Вызывает методы для работы с созданием json файлов.
     */
    public void callback(){
        WinNT.HANDLE handle = ApiHelper.enterMutexOrSemaphore(1);
        System.out.println(handle);
        CreateNewEntryFormController.saveLog("Hi, it's new thread, id: " + MyKernel32.INSTANCE.GetCurrentThreadId());
        CreateNewEntryFormController.saveLog("Creating json file...");
        System.out.println("Hi, it's new thread, id: " + MyKernel32.INSTANCE.GetCurrentThreadId());
        System.out.println("Creating json file...");
        JsonManager.createJson(CreateNewEntryFormController.getStreamsElements(), CreateNewEntryFormController.getName());

        ApiHelper.leaveMutexOrSemaphore(handle);
        CreateNewEntryFormController.saveLog("Done!");
        System.out.println("Done!");
    }
}
