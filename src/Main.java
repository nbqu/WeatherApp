<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
=======
import api.VilageFcstInfoService;

import java.io.IOException;
>>>>>>> 주형

public class Main {

    public static void main(String[] args) throws IOException {
        String serviceKey = "pV9LNHDXZHj6tA2pwp2vSUnN%2F1CkAZeTfQQSjnsxaO9WFCKN0A9vcp%2Becpy6Je6aEoeUdXIeEPI2nzbZZmyXPw%3D%3D"; //Juhyeong
        VilageFcstInfoService a = new VilageFcstInfoService(serviceKey);
        a.getbaseDateTime();
    }
}
