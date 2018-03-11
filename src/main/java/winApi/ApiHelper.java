package winApi;

import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import winApi.threads.CreateJson;
import winApi.threads.FillDataBase;
import winApi.threads.ParseFiles;

public class ApiHelper{

    public static MyKernel32.CRITICAL_SECTION criticalSection = new MyKernel32.CRITICAL_SECTION();

    /**
     * Позволяет получить время работы дочернего процесса по его дескриптору.
     *
     * @param thread
     *            Дескриптор для потока, который будет перезагружен.
     *
     * @return Время выполнения в миллисекундах
     */
    private static long getTimeJob(HANDLE thread){
        WinBase.FILETIME timeStart = new WinBase.FILETIME(), timeExit = new WinBase.FILETIME(),
                timeKernel = new WinBase.FILETIME(), timeUser = new WinBase.FILETIME();
        MyKernel32.INSTANCE.GetThreadTimes(thread, timeStart, timeExit, timeKernel, timeUser);
        return (timeExit.dwLowDateTime - timeStart.dwLowDateTime) / 10000;
    }

    /**
     * Запускает дочерние потоки с использованием критических секций.
     *
     * @param parseFiles
     *            Ссылка на объект, имплементирующий CallBack. В нем вызывается необходимая функция для работы с
     *            парсингом файлов.
     * @param createJson
     *            Ссылка на объект, имплементирующий CallBack. В нем вызывается необходимая функция для работы с
     *            созданием json файлов.
     * @param fillDataBase
     *            Ссылка на объект, имплементирующий CallBack. В нем вызывается необходимая функция для работы с
     *            заполнением базы данных.
     */
    public static void buildThreads(ParseFiles parseFiles, CreateJson createJson, FillDataBase fillDataBase) {

        DWORD flag = new DWORD(WinBase.CREATE_SUSPENDED);
        HANDLE[] hThreads = new HANDLE[3];
        System.out.println("Creating threads");
        hThreads[0] = MyKernel32.INSTANCE.CreateThread(null, null, parseFiles, null, flag, null);
        System.out.println("Thread 1 was created");
        hThreads[1] = MyKernel32.INSTANCE.CreateThread(null, null, createJson, null, flag, null);
        System.out.println("Thread 2 was created");
        hThreads[2] = MyKernel32.INSTANCE.CreateThread(null, null, fillDataBase, null, flag, null);
        System.out.println("Thread 3 was created");
        System.out.println("Initialize critical section");
        MyKernel32.INSTANCE.InitializeCriticalSection(criticalSection);
        for (int i = 0; i < 3; i++) {
            System.out.println("Run thread № " + (i+1));
            MyKernel32.INSTANCE.ResumeThread(hThreads[i]);
            MyKernel32.INSTANCE.WaitForSingleObject(hThreads[i], -1);
            System.out.println("Thread " + (i+1) + " was finished. Timework is " + getTimeJob(hThreads[i]) + "ms");
        }
        System.out.println("Closing threads");
        for (int i = 0; i < 3; i++) {
            MyKernel32.INSTANCE.CloseHandle(hThreads[i]);
        }
        System.out.println("Threads were removed");
        System.out.println("Removing critical section");
        MyKernel32.INSTANCE.DeleteCriticalSection(criticalSection);
        System.out.println("Critical section was removed");
    }

}
