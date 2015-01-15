package com.sentri.io;

import com.google.gson.Gson;
import com.sentri.utils.Constants;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjun.yyj on 11/16/14.
 */
public class DataFile {
    private String filename = null;

    private double[][] data = null;

    private int numLineData = 0;

    private int numLineAbnormal = 0;

    //final except abnormal
    private int numLineNormal = 0;

    private int offset = 0;

    public DataFile() {

    }

    public DataFile(String filename) {
        this.filename = filename;
        this.loadData();
    }

    public void loadData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            List<String[]> lines = new ArrayList<String[]>();

            numLineData = 0;
            numLineNormal = 0;
            numLineAbnormal = 0;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(Constants.DATA_FILE_SPLIT, -1);
                if (columns.length != Constants.DATA_COLUMN_NUM) {
                    System.out.println("line " + numLineData  + " in " + filename + " parse error:" + line);
                    numLineAbnormal++;
                } else {
                    lines.add(columns.clone());
                    numLineNormal++;
                }
                numLineData++;
            }
            br.close();

            data = new double[numLineNormal][Constants.DATA_COLUMN_NUM];
            for (int i = 0; i < lines.size(); i++) {
                String[] columns = lines.get(i);
                try {
                for (int j = 0; j < columns.length; j++) {
                    if (columns[j].trim().isEmpty()) {
                        data[i][j] = 0;
                    } else {
                        data[i][j] = Integer.valueOf(columns[j].trim());
                    }
                }
                } catch (Exception e) {
                    System.out.println(new Gson().toJson(columns));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean loadData(int size) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            List<String[]> lines = new ArrayList<String[]>();
            //System.out.println(offset);

            numLineData = 0;
            numLineNormal = 0;
            numLineAbnormal = 0;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(Constants.DATA_FILE_SPLIT, -1);
                if (columns.length != Constants.DATA_COLUMN_NUM) {
                    System.out.println("line " + numLineData  + " in " + filename + " parse error:" + line);
                    numLineAbnormal++;
                } else {
                    if ((numLineData >=offset) && (numLineData < offset+size)) {
                        lines.add(columns.clone());
                    }
                    numLineNormal++;
                }
                numLineData++;
            }
            br.close();

            data = new double[size][Constants.DATA_COLUMN_NUM];
            if (lines.size() < size) {
                return false;
            }
            for (int i = 0; i < lines.size(); i++) {
                String[] columns = lines.get(i);
                try {
                    for (int j = 0; j < columns.length; j++) {
                        if (columns[j].trim().isEmpty()) {
                            data[i][j] = 0;
                        } else {
                            data[i][j] = Integer.valueOf(columns[j].trim());
                        }
                    }
                } catch (Exception e) {
                    System.out.println(new Gson().toJson(columns));
                }
            }
            offset = offset + size;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public double[][] getData() {
        return data;
    }

    public void setData(double[][] data) {
        this.data = data;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


}
