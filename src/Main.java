import java.io.IOException;
import java.util.Scanner;

import api.VilageFcstInfoService;
import api_data.RetrievingCoordinate;
import api_data.MidFcstRetrievingCoordinate;

import org.json.simple.parser.ParseException;


public class Main {

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
        Scanner sc = new Scanner(System.in);

        String serviceKey = "pV9LNHDXZHj6tA2pwp2vSUnN%2F1CkAZeTfQQSjnsxaO9WFCKN0A9vcp%2Becpy6Je6aEoeUdXIeEPI2nzbZZmyXPw%3D%3D"; //Juhyeong
<<<<<<< HEAD
        GUI g = new GUI();
=======


        //testing Coordinate method
        String top = sc.next();
        String mdl = sc.next();
        String leaf = sc.next();
        RetrievingCoordinate pair = new RetrievingCoordinate(top,mdl,leaf);
        System.out.println("in this driver class");
        System.out.println(pair.getX() + " " + pair.getY());
        System.out.println("---------");
        System.out.println(pair.getAr().get(0));
        System.out.println(pair.getAr().get(1));
        System.out.println(pair.getAr().get(2));
        VilageFcstInfoService a = new VilageFcstInfoService(serviceKey, pair.getX(), pair.getY());
        a.getbaseDateTime();
        a.runAPI();
        //main에 top을







>>>>>>> KimSeokHyeon
    }
}

//구현할 class를 실행시키는 용도
//인풋을 받는다
//api_data api를 동작시키는데 필요한 class

