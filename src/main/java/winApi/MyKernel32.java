package winApi;

import com.sun.jna.*;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.W32APIOptions;

import java.util.Arrays;
import java.util.List;

public interface MyKernel32 extends Kernel32 {

    static MyKernel32 INSTANCE = (MyKernel32) Native.loadLibrary("kernel32", MyKernel32.class,W32APIOptions.DEFAULT_OPTIONS);

    DWORD ResumeThread(HANDLE hThread);

    HANDLE CreateThread(Pointer lpThreadAttributes, Pointer dwStackSize, Callback lpStartAddress, Pointer lpParameter, DWORD dwCreationFlags, Pointer lpThreadId);

    void EnterCriticalSection(CRITICAL_SECTION lpCriticalSection);

    void LeaveCriticalSection(CRITICAL_SECTION lpCriticalSection);

    void DeleteCriticalSection(CRITICAL_SECTION lpCriticalSection);

    boolean InitializeCriticalSection(CRITICAL_SECTION lpCriticalSection);

    public static class CRITICAL_SECTION extends Structure {

        public WinDef.INT_PTR DebugInfo;
        public long LockCount;
        public long RecursionCount;
        public WinDef.INT_PTR OwningThread;
        public WinDef.INT_PTR LockSemaphore;
        public WinDef.INT_PTR SpinCount;

        protected List<String> getFieldOrder() {
            return Arrays.asList("DebugInfo", "LockCount", "RecursionCount", "OwningThread", "LockSemaphore", "SpinCount");
        }

        public CRITICAL_SECTION() {
        }

    }
}
