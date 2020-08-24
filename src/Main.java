import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import api.VilageFcstInfoService;
import api_data.CoordinateXY;
import api_data.RetrievingCoordinate;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);

        String serviceKey = "pV9LNHDXZHj6tA2pwp2vSUnN%2F1CkAZeTfQQSjnsxaO9WFCKN0A9vcp%2Becpy6Je6aEoeUdXIeEPI2nzbZZmyXPw%3D%3D"; //Juhyeong
        VilageFcstInfoService a = new VilageFcstInfoService(serviceKey);
        a.getbaseDateTime();

        //testing Coordinate method
        String top = sc.next();
        String mdl = sc.next();
        String leaf = sc.next();
        RetrievingCoordinate pair = new RetrievingCoordinate(top,mdl,leaf);
        System.out.println("in this driver class");
        System.out.println(pair.getX() + " " + pair.getY());
        System.out.println("---------");
        System.out.println(pair.getAr());
        System.out.println(pair.getAr2());
        System.out.println(pair.getAr3());
        //main에 top을







    }
}

//구현할 class를 실행시키는 용도
//인풋을 받는다
//api_data api를 동작시키는데 필요한 class

