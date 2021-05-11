package org.apache.spark.deploy;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.spark.deploy.master.WorkerInfo;

public class SIngleton {
    static SIngleton sIngleton = new SIngleton();
    private String tik = "prot in the damn booty ass";
    private WorkerInfo workerInfo ;

    public static SIngleton getInstance(){
        if(sIngleton == null)
            sIngleton = new SIngleton();

        return sIngleton;
    }
    public void setTik(String tik){
        this.tik = tik;
    }

    public void setWorkerInfo(WorkerInfo workerInfo){
        this.workerInfo = workerInfo;
        System.out.println("workerInfo Berjaya set "+workerInfo.host());
    }

    public WorkerInfo getWorkerInfo() {
        return workerInfo;
    }

    public String getTik() {
        return tik;
    }
}
