package winApi;

import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import winApi.threads.CreateJson;
import winApi.threads.FillDataBase;
import winApi.threads.ParseFiles;

public class ApiHelper{

    public static MyKernel32.CRITICAL_SECTION criticalSection = new MyKernel32.CRITICAL_SECTION();
    public static void buildThreads(ParseFiles parseFiles, CreateJson createJson, FillDataBase fillDataBase) {

        DWORD flag = new DWORD(WinBase.CREATE_SUSPENDED);
        HANDLE[] hThreads = new HANDLE[3];
        System.out.println("Creating threads");
        hThreads[0] = MyKernel32.INSTANCE.CreateThread(null, null, parseFiles, null, flag, null);
        System.out.println("Thread 1 created");
        hThreads[1] = MyKernel32.INSTANCE.CreateThread(null, null, createJson, null, flag, null);
        System.out.println("Thread 2 created");
        hThreads[2] = MyKernel32.INSTANCE.CreateThread(null, null, fillDataBase, null, flag, null);
        System.out.println("Thread 3 created");
        System.out.println("Initialize critical section");
        MyKernel32.INSTANCE.InitializeCriticalSection(criticalSection);
        for (int i = 0; i < 3; i++) {
            System.out.println("Run thread № " + (i+1));
            MyKernel32.INSTANCE.ResumeThread(hThreads[i]);
            MyKernel32.INSTANCE.WaitForSingleObject(hThreads[i], -1);
        }
        System.out.println("Close threads");
        for (int i = 0; i < 3; i++) {
            MyKernel32.INSTANCE.CloseHandle(hThreads[i]);
        }
        System.out.println("Delete critical section");
        MyKernel32.INSTANCE.DeleteCriticalSection(criticalSection);
    }
}
