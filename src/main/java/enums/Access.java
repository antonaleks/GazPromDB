package enums;

public class Access {

    public enum Right{
        READ(0b00001),
        WRITE(0b00010),
        REMOVE(0b00100),
        UPDATE(0b01000),
        ADDUSER(0b10000),
        ALL(0b11111);

        private int code;

        Right(int code) {
            this.code = code;
        }

        int getCode(){
            return code;
        }
    }

    public static boolean checkAccess(int access, Right right){
        return (access & right.getCode()) == right.getCode();
    }
}
