package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record {
    // region variables
    private int ID;
    private LocalDateTime DateTimeUTC;
    private boolean TapType;
    private String StopId;
    private String CompanyId;
    private String BusID;
    private String PAN;
    // endregion
    // region Constructor
    public Record(int id, String dateTimeUTC, boolean tapType, String stopId, String companyId, String busID, String pan) {
        ID = id;
        DateTimeUTC = LocalDateTime.parse(dateTimeUTC);
        TapType = tapType;
        StopId = stopId;
        CompanyId = companyId;
        BusID = busID;
        PAN = pan;
    }

    public Record(String record){
        String[] recordArr = record.split(",");
        ID = Integer.parseInt(recordArr[0].trim());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeUTC = LocalDateTime.parse(recordArr[1].trim(), formatter);
        TapType = recordArr[2].trim().equals("ON");
        StopId = recordArr[3].trim();
        CompanyId = recordArr[4].trim();
        BusID = recordArr[5].trim();
        PAN = recordArr[6].trim();
    }

    public Record(){}
    // endregion
    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s ",ID,DateTimeUTC,TapType,StopId,CompanyId,BusID,PAN);
    }
    //region Setter and Getter
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDateTime getDateTimeUTC() {
        return DateTimeUTC;
    }

    public void setDateTimeUTC(LocalDateTime dateTimeUTC) {
        DateTimeUTC = dateTimeUTC;
    }

    public boolean isTapOn() {
        return TapType;
    }

    public void setTapType(boolean tapType) {
        TapType = tapType;
    }

    public String getStopId() {
        return StopId;
    }

    public void setStopId(String stopId) {
        StopId = stopId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getBusID() {
        return BusID;
    }

    public void setBusID(String busID) {
        BusID = busID;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }
    //endregion
}
