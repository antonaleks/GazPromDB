package winApi.threads;

import com.sun.jna.Callback;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import controllers.CreateNewEntryFormController;
import entity.StreamsElement;
import org.apache.commons.lang3.SerializationUtils;
import properties.JsonManager;
import winApi.ApiHelper;
import winApi.MyKernel32;

import java.util.ArrayList;
import java.util.List;

public class CreateJson implements Callback {

    /**
     * Вызывает методы для работы с созданием json файлов.
     */
    public void callback(){
        WinNT.HANDLE handle = ApiHelper.enterMutexOrSemaphore(1);
        CreateNewEntryFormController.saveLog("Hi, it's new thread, id: " + MyKernel32.INSTANCE.GetCurrentThreadId());
        CreateNewEntryFormController.saveLog("Creating json file...");
        List<StreamsElement> elements = new ArrayList<>();
        byte[] lengthList = new byte[ApiHelper.SIZE_OF_INT_BYTE];
        IntByReference dwRead = new IntByReference();
        CreateNewEntryFormController.saveLog("Load data from pipes...");
        MyKernel32.INSTANCE.ReadFile(ApiHelper.pipeInRead.getValue(), lengthList, lengthList.length, dwRead, null);
        Integer elemsCount = SerializationUtils.deserialize(lengthList);
        for (int i = 0; i < elemsCount; i++) {
            byte[] sizeR = new byte[ApiHelper.SIZE_OF_INT_BYTE];
            MyKernel32.INSTANCE.ReadFile(ApiHelper.pipeInRead.getValue(), sizeR, sizeR.length, dwRead, null);
            Integer len = SerializationUtils.deserialize(sizeR);
            byte[] dataR = new byte[len];
            MyKernel32.INSTANCE.ReadFile(ApiHelper.pipeDataRead.getValue(), dataR, len, dwRead, null);
            StreamsElement elem = SerializationUtils.deserialize(dataR);
            elements.add(elem);
        }
        CreateNewEntryFormController.saveLog("Data has been downloaded");
        JsonManager.createJson(elements, CreateNewEntryFormController.getName());

        ApiHelper.leaveMutexOrSemaphore(handle);
        CreateNewEntryFormController.saveLog("Done!");

    }
}
