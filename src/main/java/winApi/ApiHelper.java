package winApi;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;
import controllers.CreateNewEntryFormController;
import enums.SynchronizationMode;
import winApi.threads.CreateJson;
import winApi.threads.FillDataBase;
import winApi.threads.ParseFiles;

import static enums.SynchronizationMode.Event;
import static enums.SynchronizationMode.Mutex;
import static enums.SynchronizationMode.Semaphore;
import static winApi.MyKernel32.INFINITE;

public class ApiHelper {


    public static HANDLE semaphoreOrMutexHandle;
    public static HANDLE[] hEvent;
    public static WinNT.HANDLEByReference pipeInRead = new WinNT.HANDLEByReference(),
            pipeInWrite = new WinNT.HANDLEByReference(),
            pipeDataRead = new WinNT.HANDLEByReference(),
            pipeDataWrite = new WinNT.HANDLEByReference();
    public static final int SIZE_OF_INT_BYTE = 81;
    public static final int SIZE_OF_PIPE = 10000;

    /**
     * Позволяет получить время работы дочернего процесса по его дескриптору.
     *
     * @param thread Дескриптор для потока, который будет перезагружен.
     * @return Время выполнения в миллисекундах
     */
    private static long getTimeJob(HANDLE thread) {
        WinBase.FILETIME timeStart = new WinBase.FILETIME(), timeExit = new WinBase.FILETIME(),
                timeKernel = new WinBase.FILETIME(), timeUser = new WinBase.FILETIME();
        MyKernel32.INSTANCE.GetThreadTimes(thread, timeStart, timeExit, timeKernel, timeUser);
        return (timeExit.dwLowDateTime - timeStart.dwLowDateTime) / 10000;
    }

    /**
     * Запускает дочерние потоки с использованием критических секций.
     *
     * @param parseFiles   Ссылка на объект, имплементирующий CallBack. В нем вызывается необходимая функция для работы с
     *                     парсингом файлов.
     * @param createJson   Ссылка на объект, имплементирующий CallBack. В нем вызывается необходимая функция для работы с
     *                     созданием json файлов.
     * @param fillDataBase Ссылка на объект, имплементирующий CallBack. В нем вызывается необходимая функция для работы с
     *                     заполнением базы данных.
     */
    public static void buildThreads(ParseFiles parseFiles, CreateJson createJson, FillDataBase fillDataBase) {

        DWORD flag = new DWORD(WinBase.CREATE_SUSPENDED);
        HANDLE[] hThreads = new HANDLE[3];
        hEvent = new HANDLE[3];
        switch (CreateNewEntryFormController.getMode()) {
            case Event:
                for (int j = 0; j < 3; j++) {
                    hEvent[j] = Kernel32.INSTANCE.CreateEvent(null, true, false, "" + j);
                }
                break;
            case Semaphore:
                semaphoreOrMutexHandle = MyKernel32.INSTANCE.CreateSemaphore(null, 1, 1, null);
                break;
            case Mutex:
                semaphoreOrMutexHandle = MyKernel32.INSTANCE.CreateMutex(null, false, null);
                break;
        }
        CreateNewEntryFormController.saveLog("Creating pipes");
        MyKernel32.INSTANCE.CreatePipe(pipeInRead, pipeInWrite, null, 0);
        MyKernel32.INSTANCE.CreatePipe(pipeDataRead, pipeDataWrite, null, SIZE_OF_PIPE);

        CreateNewEntryFormController.saveLog("Creating threads");
        hThreads[0] = MyKernel32.INSTANCE.CreateThread(null, null, parseFiles, null, flag, null);

        CreateNewEntryFormController.saveLog("Thread 1 was created");
        hThreads[1] = MyKernel32.INSTANCE.CreateThread(null, null, createJson, null, flag, null);

        CreateNewEntryFormController.saveLog("Thread 2 was created");
        hThreads[2] = MyKernel32.INSTANCE.CreateThread(null, null, fillDataBase, null, flag, null);

        CreateNewEntryFormController.saveLog("Thread 3 was created");
        for (int i = 0; i < 3; i++) {

            CreateNewEntryFormController.saveLog("Run thread № " + (i + 1));
            MyKernel32.INSTANCE.ResumeThread(hThreads[i]);
            if (CreateNewEntryFormController.getMode() == Event)
                MyKernel32.INSTANCE.SetEvent(hEvent[i]);
            MyKernel32.INSTANCE.WaitForSingleObject(hThreads[i], INFINITE);
            CreateNewEntryFormController.saveLog("Thread " + (i + 1) + " was finished. Timework is " + getTimeJob(hThreads[i]) + "ms");

        }
        if (CreateNewEntryFormController.getMode() == Event)
            for (int j = 0; j < 3; j++)
                MyKernel32.INSTANCE.CloseHandle(hEvent[j]);
        else MyKernel32.INSTANCE.CloseHandle(semaphoreOrMutexHandle);
        CreateNewEntryFormController.saveLog("Closing threads");
        for (int i = 0; i < 3; i++) {
            MyKernel32.INSTANCE.CloseHandle(hThreads[i]);
        }
        CreateNewEntryFormController.saveLog("Closing pipes");
        MyKernel32.INSTANCE.CloseHandle(pipeInWrite.getValue());
        MyKernel32.INSTANCE.CloseHandle(pipeInRead.getValue());
        MyKernel32.INSTANCE.CloseHandle(pipeDataWrite.getValue());
        MyKernel32.INSTANCE.CloseHandle(pipeDataRead.getValue());

        CreateNewEntryFormController.saveLog("Threads were removed");
        CreateNewEntryFormController.saveLog("Pipes were removed");
    }

    public static HANDLE enterMutexOrSemaphore(int idEvent) {
        HANDLE handle = CreateNewEntryFormController.getMode() == Event ? hEvent[idEvent] : semaphoreOrMutexHandle;
        MyKernel32.INSTANCE.WaitForSingleObject(handle, INFINITE);
        return handle;
    }

    public static void leaveMutexOrSemaphore(HANDLE handle) {
        switch (CreateNewEntryFormController.getMode()) {
            case Semaphore:
                MyKernel32.INSTANCE.ReleaseSemaphore(handle, 1, null);
            case Mutex:
                MyKernel32.INSTANCE.ReleaseMutex(handle);
        }
    }

}

