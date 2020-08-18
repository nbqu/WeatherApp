import api.VilageFcstInfoService;

public class Main {

    public static void main(String[] args) {
        String serviceKey = "pV9LNHDXZHj6tA2pwp2vSUnN%2F1CkAZeTfQQSjnsxaO9WFCKN0A9vcp%2Becpy6Je6aEoeUdXIeEPI2nzbZZmyXPw%3D%3D"; //Juhyeong
        VilageFcstInfoService a = new VilageFcstInfoService(serviceKey);
        a.getbaseDateTime();
    }
}
