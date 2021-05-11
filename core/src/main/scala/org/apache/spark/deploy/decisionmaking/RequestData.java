package org.apache.spark.deploy.decisionmaking;

public class RequestData {
    String mm;
    String mc;
    String wn;
    String wmn;
    String wcn;

    public RequestData(String mm, String mc, String wn, String wmn, String wcn, String ds, String ac, String mec) {
        this.mm = mm;
        this.mc = mc;
        this.wn = wn;
        this.wmn = wmn;
        this.wcn = wcn;
        this.ds = ds;
        this.ac = ac;
        this.mec = mec;
    }

    String ds;
    String ac;
    String mec;

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getWn() {
        return wn;
    }

    public void setWn(String wn) {
        this.wn = wn;
    }

    public String getWmn() {
        return wmn;
    }

    public void setWmn(String wmn) {
        this.wmn = wmn;
    }

    public String getWcn() {
        return wcn;
    }

    public void setWcn(String wcn) {
        this.wcn = wcn;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }



    public String getMec() {
        return mec;
    }

    public void setMec(String mec) {
        this.mec = mec;
    }
}
