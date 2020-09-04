import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import api.VilageFcstInfoService;
import api.vilageFcstData;
import api_data.RetrievingCoordinate;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class GUI extends JFrame implements ActionListener{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public RetrievingCoordinate rc = new RetrievingCoordinate();
    private JComboBox location1 = new JComboBox<>();
    private final JComboBox location2 = new JComboBox<>();
    private final JComboBox location3 = new JComboBox<>();
    private JSONArray top = null;
    private JSONArray mdl = null;
    private JSONArray leaf = null;
    private final JLabel printCode = new JLabel();

    private ArrayList<vilageFcstData> data;

    public GUI() throws IOException, ParseException, java.text.ParseException {
        super();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        villageForecast();

        setTitle("WeatherApp");
        setVisible(true);
    }

    public void villageForecast() throws IOException, ParseException, java.text.ParseException {

        JPanel test = new JPanel();
        test.setLayout(new FlowLayout());

        add(test, BorderLayout.NORTH);

        top = rc.getParsedData("top");
        ArrayList<String> topList = rc.getLocationList(top);
        location1 = new JComboBox(topList.toArray(new String[topList.size()]));

        location1.addActionListener(e -> {
            rc.initCode();
            rc.setCode(top, location1.getSelectedItem().toString());
            try {
                mdl = rc.getParsedData("mdl");
            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
            ArrayList<String> mdlList = rc.getLocationList(mdl);
            location2.setModel(new DefaultComboBoxModel());
            for (String s:
                 mdlList) {
                location2.addItem(s);
            }
            printCode.setText(rc.getCode());

        });
        //김천시, 대구시 code
        location2.addActionListener(e -> {
            rc.initCode();
            rc.setCode(mdl, location2.getSelectedItem().toString());

            try {
                leaf = rc.getParsedData("leaf");
            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
            ArrayList<String> leafList = rc.getLocationList(leaf);
            location3.setModel(new DefaultComboBoxModel());
            for (String s: leafList) {
                location3.addItem(s);
            }
            printCode.setText(rc.getCode());
        });

        location3.addActionListener(e -> {
            rc.initCode();
            rc.setCode(leaf, location3.getSelectedItem().toString());
            rc.setXY(leaf, location3.getSelectedItem().toString());
            printCode.setText(rc.getCode()+" "+rc.getX()+" "+rc.getY());
            try {
                VilageFcstInfoService vf = new VilageFcstInfoService("pV9LNHDXZHj6tA2pwp2vSUnN%2F1CkAZeTfQQSjnsxaO9WFCKN0A9vcp%2Becpy6Je6aEoeUdXIeEPI2nzbZZmyXPw%3D%3D", rc.getX(), rc.getY());
                data = vf.runAPI();
                displayVilData();
            } catch (IOException | ParseException | java.text.ParseException ioException) {
                ioException.printStackTrace();
            }
        });
        test.add(location1);
        test.add(location2);
        test.add(location3);
        test.add(printCode);


    }

    public void displayVilData() throws java.text.ParseException {
        String[] passingData = {"UUU", "VVV", "WAV", "VEC", "WSD"};
        String[] rainingData = {"PTY", "S06", "R06"};
        HashMap<String, String> dict = new HashMap<String, String>(){{
            put("POP", "강수확률");
            put("PTY", "강수형태");
            put("R06", "강수량");
            put("REH", "습도");
            put("S06", "적설량");
            put("SKY", "날씨");
            put("T3H", "기온");
            put("TMN", "최저기온");
            put("TMX", "최고기온");
        }};
        HashMap<String, String> degDict = new HashMap<String, String>(){{
            put("POP", "%");
            put("R06", "mm");
            put("REH", "%");
            put("S06", "cm");
            put("T3H", "°C");
            put("TMN", "°C");
            put("TMX", "°C");
        }};
        HashMap<Integer, String> PTYDict = new HashMap<Integer, String>(){{
            put(1, "비");
            put(2, "비/눈");
            put(3, "눈");
            put(4, "소나기");
        }};
        HashMap<Integer, String> SKYDict = new HashMap<Integer, String>(){{
            put(1, "맑음");
            put(3, "구름 많음");
            put(4, "비");
        }};
        JPanel weatherPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        weatherPanel.setLayout(new GridLayout(4, 4));

        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat oldTimeFormat = new SimpleDateFormat("HHmm");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("M/d");
        SimpleDateFormat newTimeFormat = new SimpleDateFormat("HH:mm");

        for (int i = 0; i < 16; i++) { // 최근 48시간
            String msg;
            vilageFcstData d = data.get(i);
            JPanel weatherSubPanel = new JPanel();
            weatherSubPanel.setLayout(new FlowLayout());
            Date date = oldDateFormat.parse(d.getFcstDate());
            Date time = oldTimeFormat.parse(d.getFcstTime());
            JLabel newDate = new JLabel(newDateFormat.format(date));
            JLabel newTime = new JLabel(newTimeFormat.format(time));

            weatherSubPanel.add(newDate);
            weatherSubPanel.add(newTime);

            for (Map.Entry<String, Double> e : d.getData().entrySet()) {
                if (Arrays.asList(passingData).contains(e.getKey())) // 불필요한 자료 pass
                    continue;
                if (Arrays.asList(rainingData).contains(e.getKey()) && e.getValue().intValue() == 0) // 강수, 적설량 0이면 pass
                    continue;
                if (e.getKey().equals("PTY"))
                    msg = dict.get(e.getKey()) + " : " + PTYDict.get(e.getValue().intValue());
                else if(e.getKey().equals("SKY"))
                    msg = dict.get(e.getKey()) + " : " + SKYDict.get(e.getValue().intValue());
                else
                    msg = dict.get(e.getKey()) + " : " + e.getValue().intValue() + degDict.get(e.getKey());
                JLabel line = new JLabel(msg);
                weatherSubPanel.add(line);
            }
            weatherPanel.add(weatherSubPanel);
    }
        add(scrollPane);
        add(weatherPanel, BorderLayout.CENTER);

    }
    public void actionPerformed(ActionEvent e) {

    }
}

/*
* POP : 강수확률
* PTY : 강수형태
* R06 : 강수량
* REH : 습도
* S06 : 적설량
* SKY : 하늘상태
* T3H : 3시간 기온, TMN : 최저기온, TMX : 최고기온
*
* */
