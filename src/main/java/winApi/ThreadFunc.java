package winApi;

import com.sun.jna.Callback;

public class ThreadFunc implements Callback{
    public void callback(){
        MyKernel32.INSTANCE.EnterCriticalSection(ApiHelper.criticalSection);
        System.out.println("Hi, it's child thread, id: " + MyKernel32.INSTANCE.GetCurrentThreadId());
        MyKernel32.INSTANCE.LeaveCriticalSection(ApiHelper.criticalSection);
    }
}
