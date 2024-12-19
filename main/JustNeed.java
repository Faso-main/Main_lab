package main;

public class JustNeed {
    public static void main(String[] args){

    }

    public static int Mod(long a,long b){
        if(b==0){
            return 0;
        }
        int ret = (int) (a/b);
        return ret;
    }

}
