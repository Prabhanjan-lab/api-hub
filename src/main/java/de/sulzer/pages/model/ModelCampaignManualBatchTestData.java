/**
 *
 */
package de.sulzer.pages.model;

/**
 * @author Sulzer GmbH
 *
 */
public class ModelCampaignManualBatchTestData {

    private String VIN;
    private String PKN;
    private String PRN;
    private String MBT;
    private String Importer;

    /**
     *
     */
    public ModelCampaignManualBatchTestData() {
        super();
    }

    /**
     * @param vin
     * @param pkn
     * @param prn
     * @param mbt
     * @param importer
     */
    public ModelCampaignManualBatchTestData(String vin, String pkn, String prn, String mbt, String importer) {
        super();
        VIN = vin;
        PKN = pkn;
//		PRN = prn; // see below
        PRN = "K8C"; // fake/default value setting data, because data is visible, but not gettable from textarea
        MBT = mbt;
        Importer = importer;
    }

    /**
     * @return the vIN
     */
    public String getVIN() {
        return VIN;
    }

    /**
     * @param vIN the vIN to set
     */
    public void setVIN(String vIN) {
        VIN = vIN;
    }

    /**
     * @return the pKN
     */
    public String getPKN() {
        return PKN;
    }

    /**
     * @param pKN the pKN to set
     */
    public void setPKN(String pKN) {
        PKN = pKN;
    }

    /**
     * @return the pRN
     */
    public String getPRN() {
        return PRN;
    }

    /**
     * @param pRN the pRN to set
     */
    public void setPRN(String pRN) {
        PRN = pRN;
    }

    /**
     * @return the mBT
     */
    public String getMBT() {
        return MBT;
    }

    /**
     * @param mBT the mBT to set
     */
    public void setMBT(String mBT) {
        MBT = mBT;
    }

    /**
     * @return the importer
     */
    public String getImporter() {
        return Importer;
    }

    /**
     * @param importer the importer to set
     */
    public void setImporter(String importer) {
        Importer = importer;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ModelCampaignManualBatchTestData [VIN=" + VIN + ", PKN=" + PKN + ", PRN=" + PRN + ", MBT=" + MBT
                + ", Importer=" + Importer + "]";
    }

}
