import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import api_data.RetrievingCoordinate;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class GUI extends JFrame implements ActionListener{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public RetrievingCoordinate rc = new RetrievingCoordinate();
    private JComboBox location1 = new JComboBox<>();
    private JComboBox location2 = new JComboBox<>();
    private JComboBox location3 = new JComboBox<>();
    private JSONArray top = null;
    private JSONArray mdl = null;
    private JSONArray leaf = null;
    public GUI() throws IOException, ParseException {
        super();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        vilageForcast();
        setTitle("WeatherApp");
        setVisible(true);

    }

    public void vilageForcast() throws IOException, ParseException {
        JPanel test = new JPanel();
        test.setLayout(new FlowLayout());
        add(test);

        top = rc.getParsedData("top");
        ArrayList<String> topList = rc.getLocationList(top);
        location1 = new JComboBox(topList.toArray(new String[topList.size()]));
        rc.setCode(top, location1.getSelectedItem().toString());
        location1.addActionListener(e -> {
            try {
                mdl = rc.getParsedData("mdl");
                ArrayList<String> mdlList = rc.getLocationList(mdl);
                location2 = new JComboBox(mdlList.toArray(new String[mdlList.size()]));
            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
        });

        test.add(location1);

        location2.addActionListener(e -> {
            try {
                leaf = rc.getParsedData("leaf");
                rc.setCode(mdl, location2.getSelectedItem().toString());
                ArrayList<String> leafList = rc.getLocationList(leaf);
                location3 = new JComboBox(leafList.toArray(new String[leafList.size()]));
                rc.setXY(leaf, location3.getSelectedItem().toString());
            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
        });
        test.add(location2);

        test.add(location3);

    }
    public void actionPerformed(ActionEvent e) {

    }
}
