package com.sentri.service;

import com.sentri.model.Location;
import com.sentri.model.Network;
import com.sentri.model.Node;
import com.sentri.utils.MatlabHelper;
import java.util.List;

/**
 * Created by sanjun.yyj on 12/29/14.
 */
public class VacantModel {
    DataHolder  dh = DataHolder.getInstance();

    /**
     * This funcion is for vacant network data pre-processing use only.  It
     * returns a rssEmpty matrix which will be used as the standard matrix when
     * sensing the vacant network.   It also returns abnormalLink which identify
     * those links with a higher strange variance, they may be affected by the
     * environment.
     */
    public void initVacantData() {
        //read data for vacant network
        dh.vacantData.setFilename(dh.vacantConfig.getDataFile());
        dh.vacantData.loadData();
        double[][] data = dh.vacantData.getData();

        //Ignore the first and last certain lines of the vacant data
        data = MatlabHelper.minus(data, 0, data.length, 0, 1,
                                  dh.vacantConfig.getNodeIdOffset());
        data = MatlabHelper.subMatrix(data,
                                      dh.vacantConfig.getIgnoreTime(),
                                      data.length - 2 * dh.vacantConfig.getIgnoreTime(),
                                      0, data[0].length);
        dh.dataAfterIgnore = data.clone();

        //Process the time columns to get a time with 'second' scale
        double[] timestamp = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            timestamp[i] = data[i][dh.network.getNumNode() + 1] * 3600
                    + data[i][dh.network.getNumNode() + 2] * 60
                    + data[i][dh.network.getNumNode() + 3]
                    + data[i][dh.network.getNumNode() + 4] / 1000;
        }

        //Define the first and last time stamps with 'second' scale
        double startTime = timestamp[0];
        //If the vacant data is too short, shorter than the pre-defined  'vacantNetworkSeconds',
        //then set the end of the file as the last index
        double endTime = startTime + dh.vacantConfig.getSensingTime();
        if (endTime > timestamp[timestamp.length-1]) {
            endTime = timestamp[timestamp.length-1];
        }

        int startIndex = MatlabHelper.findFirstIndexOfMore(timestamp, startTime);
        int endIndex = MatlabHelper.findFirstIndexOfMoreOrEqual(timestamp, endTime);

        //Define the data matrix we finally use for vacant network sensing
        data = MatlabHelper.subMatrix(data, startIndex, endIndex - startIndex + 1, 0, data[0].length);

        dh.vacantData.setData(data);
    }

    /**
     * Descrition: get the average rss of the vacant network
     *   Only one node transmits at a time. The first value of each line in the
     *   data files indicates the node that is reporting its data. The 2nd column
     *   to the 29th (since there are 28 total nodes) are the received signal
     *   strength (RSS) values from each of the nodes to the reporting node.
     *   For example, if the first column is 3, then the 2nd column would represent
     *   the RSS from node 0 to node 3. Remember that the nodes' ID start from 0.
     *   The nodes to transmit to themselves will always report "-45".
     */
    public void getRssEmpty() {
        double[][] data = dh.vacantData.getData();
        double[] ids = MatlabHelper.getColumn(data, 0);
        double[][] rssData = MatlabHelper.subMatrix(data, 0, data.length, 1, dh.network.getNumNode());

        double[][] rssEmpty = new double[dh.network.getNumNode()][dh.network.getNumNode()];

        for (int i = 0; i < dh.network.getNumNode(); i++) {
            int[] indexs = MatlabHelper.findEqual(ids, i);
            for (int j = 0; j < dh.network.getNumNode(); j++) {
                double[] nodeIData = MatlabHelper.getData(rssData, indexs, j);
                int nNotZero = MatlabHelper.findNotEqual(nodeIData, 0).length;
                if ((nNotZero == 0) || (i == j)) {
                    rssEmpty[i][j] = 0;
                } else {
                    rssEmpty[i][j] = MatlabHelper.sum(nodeIData) / nNotZero;
                }
            }
        }

        rssEmpty = MatlabHelper.divide(MatlabHelper.add(rssEmpty, MatlabHelper.reverse(rssEmpty)), 2);
        dh.network.setRssEmpty(rssEmpty);
    }

    /**
     * Function to identify the links that doesn't work in a normal way.  To
     * identify those links that with a high variance (higher than the
     * threshold), which means the link may be affected when sensing the vacant
     * network.
     */
    public void getAbnormalLinks() {
        //to save one full-block data
        double[][] blockRssTemp = MatlabHelper.zeros(dh.network.getNumNode());
        //to save each link's all value over time
        List<Double>[][] linkValue = MatlabHelper.cell(dh.network.getNumNode(), dh.network.getNumNode());
        double[][] trilOnes = MatlabHelper.tril(MatlabHelper.ones(dh.network.getNumNode()), -1);
        int[][] linkIndex = MatlabHelper.findNotEqual(trilOnes, 0);

        //use ID=1 to divide the data into several blocks
        //double[][] data = dh.vacantData.getData();
        double[][] data = dh.dataAfterIgnore;
        int[] blockStartRows = MatlabHelper.findEqual(
                MatlabHelper.getColumn(data, 0), 0);

        //cancel last block
        for (int blockId = 0; blockId < blockStartRows.length-1; blockId++) {
            int startRow = blockStartRows[blockId];
            int endRow = 0;
            if (blockId != blockStartRows.length - 1) {
                endRow = blockStartRows[blockId+1];
            } else {
                endRow = data.length;
            }
            int numRows = endRow - startRow;
            //if number of line in a block is larger than numNodes,
            //then discard this block data
            if (numRows > dh.network.getNumNode()) {
                continue;
            }

            double[][] blockData = MatlabHelper.subMatrix(data,
                                                          startRow, endRow - startRow,
                                                          0, dh.network.getNumNode()+1);
            double[] blockIndex = MatlabHelper.getColumn(blockData, 0);
            double[][] blockRss = MatlabHelper.subMatrix(blockData,
                                                         0, blockData.length,
                                                         1, dh.network.getNumNode());


            //if numRows<=numNodes, then reorganize the RSS data into the blockDataAll Matrix,
            //let the lost line to be zero
            for (int nodeId = 0; nodeId < dh.network.getNumNode(); nodeId++) {
                int[] nodeGroup = MatlabHelper.findEqual(blockIndex, nodeId);
                if (nodeGroup == null || nodeGroup.length == 0) {
                    continue;
                } else if (nodeGroup.length > 1) {
                    continue;
                }
                for (int i = 0; i < dh.network.getNumNode(); i++) {
                    blockRssTemp[nodeId][i] = blockRss[nodeGroup[0]][i];
                }
            }

            //if any temp(i,j) or temp(j,i)=0.  then symmetrical value set to be zero
            for (int i = 0; i < dh.network.getNumNode(); i++) {
                for (int j = 0; j < dh.network.getNumNode(); j++) {
                    if (blockRssTemp[i][j] == 0) {
                        blockRssTemp[j][i] = 0;
                    }
                }
            }

            blockRssTemp = MatlabHelper.divide(
                    MatlabHelper.add(blockRssTemp,
                                     MatlabHelper.reverse(blockRssTemp)),
                    2);

            //if linkvalue=0, then discard this value
            double[][] linkValueTemp = MatlabHelper.getData(blockRssTemp, linkIndex);
            for (int i = 0; i < dh.network.getNumNode(); i++) {
                for (int j = 0; j < dh.network.getNumNode(); j++) {
                    double value = linkValueTemp[i][j];
                    if (value != 0) {
                        linkValue[i][j].add(value);
                    }
                }
            }
        }

        //calculate variance for each link
        double[][] variance = MatlabHelper.zeros(dh.network.getNumNode(), dh.network.getNumNode());
        for (int i = 0; i < dh.network.getNumNode(); i++) {
            for (int j = 0; j < dh.network.getNumNode(); j++) {
                variance[i][j] = MatlabHelper.variance(linkValue[i][j]);
            }
        }

        dh.network.setAbnormalLinks(MatlabHelper.findMore(variance, dh.vacantConfig.getThreshold()));
        for (int j = 0; j < dh.network.getNumNode(); j++) {
            for (int i = 0; i < dh.network.getNumNode(); i++) {
                if (dh.network.getAbnormalLinks()[i][j] == 1) {
                }
            }
        }
    }

    public void removeAbnormalLinks() {
        //remove abnormal links
        for (int i = 0; i < dh.network.getAbnormalLinks().length; i++) {
            for (int j = 0; j < dh.network.getAbnormalLinks()[0].length; j++) {
                if (dh.network.getAbnormalLinks()[i][j] == 1) {
                    dh.network.getDist()[i][j] = 0;
                }
            }
        }
    }

    public void createNetwork() {

    }


    public void initVacant() {
        initVacantData();
        getRssEmpty();
        getAbnormalLinks();
        removeAbnormalLinks();
    }
}
