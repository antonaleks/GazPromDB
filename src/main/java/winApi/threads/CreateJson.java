package winApi.threads;

import com.sun.jna.Callback;
import controllers.CreateNewEntryFormController;
import entity.StreamsElement;
import properties.JsonManager;
import winApi.ApiHelper;
import winApi.MyKernel32;

import java.util.List;

public class CreateJson implements Callback {
    public void callback(){
        MyKernel32.INSTANCE.EnterCriticalSection(ApiHelper.criticalSection);
        System.out.println("Hi, it's new thread, id: " + MyKernel32.INSTANCE.GetCurrentThreadId());
        System.out.println("Creating json file...");
        JsonManager.createJson(CreateNewEntryFormController.getStreamsElements(), CreateNewEntryFormController.getName());
        System.out.println("Done!");
        MyKernel32.INSTANCE.LeaveCriticalSection(ApiHelper.criticalSection);
    }
}
