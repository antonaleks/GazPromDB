package winApi;

import com.sun.jna.*;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

import java.util.Arrays;
import java.util.List;

public interface MyKernel32 extends Kernel32 {

    MyKernel32 INSTANCE = (MyKernel32) Native.loadLibrary("kernel32", MyKernel32.class,W32APIOptions.DEFAULT_OPTIONS);

    /**
     * Уменьшает счет времени приостановки работы потока.
     * Когда счет времени приостановки работы уменьшается до нуля, выполнение потока продолжается.
     *
     * @param hThread
     *            Дескриптор для потока, который будет перезагружен.
     *            Дескриптор должен иметь право доступа THREAD_SUSPEND_RESUME.
     *
     * @return Если функция завершается успешно, величина возвращаемого значения -
     *         предшествующий счет времени приостановки работы потока.
     *         Если функция завершается с ошибкой, величина возвращаемого значения равна - (минус) 1.
     *         Чтобы получить дополнительные данные об ошибках, вызовите GetLastError.
     */
    DWORD ResumeThread(HANDLE hThread);

    /**
     * Создает поток, который выполняется в пределах виртуального адресного пространства вызывающего процесса.
     *
     * @param lpThreadAttributes
     *            Данный аргумент определяет, может ли создаваемый поток быть унаследован дочерним процессом.
     * @param dwStackSize
     *            Размер стека в байтах. Если передать 0, то будет использоваться значение по-умолчанию (1 мегабайт).
     * @param lpStartAddress
     *            Адрес функции, которая будет выполняться потоком.
     *            Т.е. можно сказать, что функция, адрес которой передаётся в этот аргумент,
     *            является создаваемым потоком.
     * @param lpParameter
     *            Указатель на переменную, которая будет передана в поток.
     * @param dwCreationFlags
     *            Флаги создания. Здесь можно отложить запуск выполнения потока.
     * @param lpThreadId
     *            Указатель на переменную, куда будет сохранён идентификатор потока.
     *
     * @return Если функция завершается успешно, величина возвращаемого значения - дескриптор нового потока.
     *         Если функция завершается с ошибкой, величина возвращаемого значения - ПУСТО (NULL).
     *         Чтобы получать дополнительные данные об ошибках, вызовите GetLastError.
     */
    HANDLE CreateThread(Pointer lpThreadAttributes, Pointer dwStackSize, Callback lpStartAddress, Pointer lpParameter, DWORD dwCreationFlags, Pointer lpThreadId);

    /**
     * Извлекает информацию о распределении интервалов времени для заданного потока.
     *
     * @param hThread
     *            Дескриптор потока, информация о распределении интервалов времени которого разыскивается.
     *            Этот дескриптор должен быть создан с правом доступа THREAD_QUERY_INFORMATION.
     * @param lpCreationTime
     *            Указатель на структуру FILETIME, которая принимает момент создания потока.
     * @param lpExitTime
     *            Указатель на структуру FILETIME, которая принимает момент выхода потока.
     *            Если поток не вышел, содержание этой структуры не определенное.
     * @param lpKernelTime
     *            Указатель на структуру FILETIME, которая принимает величину времени,
     *            в ходе которого поток выполнялся в привилегированном режиме (режиме ядра).
     * @param lpUserTime
     *            Указатель на структуру FILETIME, которая принимает величину времени,
     *            в ходе которого поток выполнялся в непривилегированном (пользовательском) режиме.
     *
     * @return Если функция завершается успешно, величина возвращаемого значения - не ноль.
     *         Если функция завершается с ошибкой, величина возвращаемого значения - ноль.
     */
    boolean GetThreadTimes(HANDLE hThread, FILETIME lpCreationTime, FILETIME lpExitTime, FILETIME lpKernelTime, FILETIME lpUserTime);

    /**
     * Ждет монопольное использование указанного объекта критической секции.
     * Функция возвращает значение тогда, когда вызывающему потоку предоставляют монопольное использование.
     *
     * @param lpCriticalSection
     *            Указатель на объект критической секции.
     */
    void EnterCriticalSection(CRITICAL_SECTION lpCriticalSection);

    /**
     * Освобождает монопольное использование, давая возможность другому потоку стать монопольным пользователем
     * и получить доступ к защищенному ресурсу.
     *
     * @param lpCriticalSection
     *            Указатель на объект критической секции.
     */
    void LeaveCriticalSection(CRITICAL_SECTION lpCriticalSection);

    /**
     * Освобождает системные ресурсы, которые были распределены, когда объект критической секции был инициализирован.
     * После того, как эта функция вызвалась, объект критической секции больше не может использоваться для синхронизации.
     *
     * @param lpCriticalSection
     *            Указатель на объект критической секции.
     */
    void DeleteCriticalSection(CRITICAL_SECTION lpCriticalSection);

    /**
     * Заполняют поля структуры, адресуемой lpCriticalSection.
     *
     * @param lpCriticalSection
     *            Указатель на объект критической секции.
     */
    void InitializeCriticalSection(CRITICAL_SECTION lpCriticalSection);

    class CRITICAL_SECTION extends Structure {

        public WinDef.INT_PTR DebugInfo;  // Используется операционной системой
        public long LockCount;  // Счетчик использования этой критической секции
        public long RecursionCount;  // Счетчик повторного захвата из нити-владельца
        public WinDef.INT_PTR OwningThread;  // Уникальный ID нити-владельца
        public WinDef.INT_PTR LockSemaphore;  // Объект ядра используемый для ожидания
        public WinDef.INT_PTR SpinCount;  // Количество холостых циклов перед вызовом ядра

        protected List<String> getFieldOrder() {
            return Arrays.asList("DebugInfo", "LockCount", "RecursionCount", "OwningThread", "LockSemaphore", "SpinCount");
        }

        public CRITICAL_SECTION() {
        }

    }

    /**
     * Создает или открывает событие.
     *
     * @param lpEventAttributes
     *           указатель наследования возвращенного дескриптора дочерними процессами и указатель,
     *           используемого дескриптора защиты. Ставим значение NULL – это означает, что дескриптор не наследуется
     *           и используется дескриптор защиты по умолчанию.
     * @param bManualReset
     *            тип события. Если false – автоматический сброс, true –ручной.
     * @param bInitialState
     *           начальное состояние события. True – сигнальное.
     * @param lpName
     *          имя события, которое позволяет обращаться к нему из потоков разных процессов.
     *          Это имя хранится в ядре, поэтому можно к нему обращаться. Если имя не задается,
     *          то создается безымянное, тогда может использоваться только в рамках одного процесса.
     * @return Функция возвращает дескриптор события, иначе NULL. После вызова можно проверить,
     *         существовало это событие или нет. Делается это так: If  (GetLastError == ErrorAlreadyExists).

     */
    HANDLE CreateEvent(WinBase.SECURITY_ATTRIBUTES lpEventAttributes,
                       boolean bManualReset, boolean bInitialState, String lpName);

    /**
     * Переводит событие в сигнальное состояние.
     *
     * @param hEvent
     *            Дескриптор события
     * @return Функция возвращает ненулевое значение, иначе NULL.
     */
    boolean SetEvent(HANDLE hEvent);

    /**
     * Переводит событие в несигнальное состояние.
     *
     * @param hEvent
     *            Дескриптор события
     *
     * @return Функция возвращает ненулевое значение, иначе NULL.
     */
    boolean ResetEvent(HANDLE hEvent);

    /**
     * Создает семафор.
     *
     * @param lpSemaphoreAttributes
     *           указатель наследования возвращенного дескриптора дочерними процессами,
     *           указатель использования дескриптора защиты (безопасности), по умолчанию NULL – дескриптор
     *           не наследуется дочерними процессами и используется дескриптор защиты по умолчанию.
     * @param lInitialCount
     *            начальное значение счетчика.
     * @param lMaximumCount
     *           максимальное число счетчика (больше нуля).
     * @param lpName
     *         имя, используется для того, чтобы этот семафор можно было вызывать из различных процессов.
     *         Если имя соответствует уже существующему объекту, то параметры InitialCount,
     *         MaximumCount не переопределяются.
     * @return Если удалось создать семафор, функция возвращает HANDLE, если нет –  NULL.

     */
    HANDLE CreateSemaphore(HANDLE lpSemaphoreAttributes, int lInitialCount, int lMaximumCount, String lpName);

    /**
     * Увеличение счетчика семафора
     *
     * @param hSem
     *           Увеличение счетчика семафора
     * @param lReleaseCount
     *            число (больше нуля), на которое должен увеличится счетчик. Если при указанном значении
     *            счетчик должен превысить максимальное значение, указанное при создании семафора,
     *            функция вернет FALSE и значение счетчика останется прежним.
     * @param lpPreviousCount
     *          выходной параметр, указатель на 32-разрядную переменную, в которую возвращается значение счетчика.
     * @return Функция возвращает дескриптор события, иначе NULL. После вызова можно проверить,
     *         существовало это событие или нет. Делается это так: If  (GetLastError == ErrorAlreadyExists).

     */
    boolean ReleaseSemaphore(HANDLE hSem, long lReleaseCount, String lpPreviousCount);

    /**
     * Создание мьютекса.
     *
     * @param lpMutexAttributes
     *           указатель наследования возвращенного дескриптора дочерними процессами,
     *           указатель использования дескриптора защиты (безопасности), по умолчанию NULL – дескриптор
     *           не наследуется дочерними процессами и используется дескриптор защиты по умолчанию.
     * @param bInitialOwner
     *           если false, создаем мьютекс в сигнальном состоянии, который не захвачен ни одним потоком
     *           (счетчик рекурсии и id потока = 0), если true – мьютекс сразу же передается вызвавшему
     *           его потоку, то есть переходит в несигнальное состояние (счетчик рекурсии = 1 и id потока = id
     *           вызвавшего потока).
     * @param lpName
     *          имя мьютекса, которое позволяет обращаться к нему из потоков разных процессов.
     *          Это имя хранится в ядре, поэтому можно к нему обращаться. Если имя не задается,
     *          то создается безымянное, тогда может использоваться только в рамках одного процесса.
     * @return Функция возвращает дескриптор мьютекса, иначе NULL.
     */
    HANDLE CreateMutex(HANDLE lpMutexAttributes, boolean bInitialOwner, String lpName);

    /**
     * Уменьшает счетчик рекурсии в мьютексе на 1.
     *
     * @param hMutex
     *           Указатель на мьютекс.
     * @return Если какой-либо другой поток попытается вызвать ReleaseMutex, функция просто вернет FALSE.

     */
    boolean ReleaseMutex(HANDLE hMutex);

    int EVENT_ALL_ACCESS = 0x1f0003;

    int INFINITE = 0xFFFFFFFF;

    /**
     * Функция для создания анонимного пайпа.
     *
     * @param hReadPipe
     *            Дескриптор чтения из пайпа.
     * @param hWritePipe
     *            Дескриптор записи в пайп.
     * @param lpPipeAttributes
     *            Дескриптор безопасности.
     * @param nSize
     *            Размер буфера ввода-вывода в байтах.
     * @return При успешном завершении функция возвращает TRUE и два дескриптора.
     */
     boolean CreatePipe(HANDLEByReference hReadPipe,
                              HANDLEByReference hWritePipe,
                              WinBase.SECURITY_ATTRIBUTES lpPipeAttributes, int nSize);

    /**
     * Функция ReadFile читает данные из файла, начиная с позиции, обозначенной указателем файла.
     * После того, как операция чтения была закончена, указатель файла перемещается на число
     * действительно прочитанных байтов, если дескриптор файла не создан с атрибутом асинхронной операции.
     * Если дескриптор файла создается для асинхронного ввода - вывода, приложение должно переместить
     * позицию указателя файла после операции чтения.
     *
     * @param hFile
     *           Дескриптор файла (или пайпа).
     * @param lpBuffer
     *           Указатель на буфер данных.
     * @param nNumberOfBytesToRead
     *           Число байтов для считывания из буфера.
     * @param lpNumberOfBytesRead
     *           (выходной) число считанных байтов
     * @param lpOverlapped
     *            предназначен для асинхронного вывода.
     *            Анонимные пайпы поддерживают только синхронную передачу данных,
     *            поэтому этот параметр = NULL во всех работах.
     * @return Функция ReadFile возвращает значение тогда, когда выполнено одно из ниже перечисленных  условий:
                - операция записи завершается на записывающем конце канала,
                - затребованное число байтов прочитано,
                - или происходит ошибка.
                Если функция завершается успешно, величина возвращаемого значения - не ноль.
                Если функция завершается с ошибкой, величина возвращаемого значения - ноль.
                Чтобы получить дополнительные сведения об ошибке, вызовите GetLastError.
     */
    boolean ReadFile(HANDLE hFile, byte[] lpBuffer, int nNumberOfBytesToRead,
                     IntByReference lpNumberOfBytesRead, WinBase.OVERLAPPED lpOverlapped);

    /**
     * Функция WriteFile пишет данные в файл (пайп) с места, обозначенного указателем позиции в файле.
     * Эта функция предназначена и для синхронной, и для асинхронной операции.
     *
     * @param hFile
     *            Дескриптор файла (или пайпа).
     * @param lpBuffer
     *            Указатель на буфер данных.
     * @param nNumberOfBytesToWrite
     *            Число байтов для записи в буфер.
     * @param lpNumberOfBytesWritten
     *           (выходной) число записанных байтов.
     * @param lpOverlapped
     *           предназначен для асинхронного вывода. Анонимные пайпы поддерживают
     *           только синхронную передачу данных, поэтому этот параметр = NULL во всех работах.
     * @return Если функция завершается успешно, величина возвращаемого значения - не ноль.
     *         Если функция завершается с ошибкой, величина возвращаемого значения - ноль.
     *         Чтобы получить дополнительные сведения об ошибке, вызовите GetLastError.
     */
    boolean WriteFile(HANDLE hFile, byte[] lpBuffer, int nNumberOfBytesToWrite,
                      IntByReference lpNumberOfBytesWritten,
                      WinBase.OVERLAPPED lpOverlapped);

    /**
     * Мониторинг изменений в папке.
     *
     * @param directory
     *            дескриптор директории за которой будет проводится наблюдение.
     *            Это значение мы получим, выполнив CreateFile.
     * @param info
     *            указатель на буфер в который буду записываться обнаруженные изменения.
     *            Структура записей в буфере соответствует структуре FILE_NOTIFY_INFORMATION.
     *            Буфер может записываться как синхронно, так и асинхронно в зависимости от заданных параметров.
     * @param length
     *            размер буфера lpBuffer в байтах.
     * @param watchSubtree
     *            True указывает на то, что в результаты мониторинга будут попадать также изменения в подкаталогах.
     * @param notifyFilter
     *            фильтр событий. Может состоять из одного или нескольких флагов.
     * @param bytesReturned
     *             случае синхронного вызова функции этот параметр будет содержать количество байт информации,
     *             записанной в буфер. Для асинхронных вызовов значение этого параметра остается неопределенным..
     * @param overlapped
     *            указатель на структуру OVERLAPPED, которая поставляет данные,
     *            которые будут использоваться во время асинхронной операции. Это значение может быть равным null.
     * @param completionRoutine
     *           указатель на callback-функцию. Этот параметр может устанавливаться в значение null.
     * @return Если функция выполнилась успешно, возвращается ненулевое значение.
     */
    public boolean ReadDirectoryChangesW(HANDLE directory,
                                         WinNT.FILE_NOTIFY_INFORMATION info, int length,
                                         boolean watchSubtree, int notifyFilter,
                                         IntByReference bytesReturned, WinBase.OVERLAPPED overlapped,
                                         OVERLAPPED_COMPLETION_ROUTINE completionRoutine);
}
