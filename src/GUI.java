import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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
                System.out.println("processing...");
                data = vf.runAPI();
                displayData();
            } catch (IOException | ParseException | java.text.ParseException ioException) {
                ioException.printStackTrace();
            }
        });
        test.add(location1);
        test.add(location2);
        test.add(location3);
        test.add(printCode);


    }

    public void displayData() throws java.text.ParseException {
        JPanel weatherPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        weatherPanel.setLayout(new FlowLayout());

        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat oldTimeFormat = new SimpleDateFormat("HHmm");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("M/d");
        SimpleDateFormat newTimeFormat = new SimpleDateFormat("HH:mm");

        for (vilageFcstData d:
         data) {
            JPanel weatherSubPanel = new JPanel();
            weatherSubPanel.setLayout(new FlowLayout());
            Date date = oldDateFormat.parse(d.getFcstDate());
            Date time = oldTimeFormat.parse(d.getFcstTime());
            JLabel newDate = new JLabel(newDateFormat.format(date));
            JLabel newTime = new JLabel(newTimeFormat.format(time));

            weatherSubPanel.add(newDate);
            weatherSubPanel.add(newTime);

            for (Map.Entry<String, Double> e: d.getData().entrySet()) {
                JLabel line = new JLabel("key : " + e.getKey() + ", value : " + e.getValue().toString() + "\n");
                //System.out.println(e.getKey() + " : " + e.getValue());
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
