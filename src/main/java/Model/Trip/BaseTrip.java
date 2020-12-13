package Model.Trip;

import Model.Graph.Edge;
import Model.Graph.Stop;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Locale;

public class BaseTrip{
    // region variables
    private LocalDateTime Started;
    private LocalDateTime Finished;
    private int DurationSecs;
    private String FromStopId;
    private String ToStopId;
    private float ChargeAmount;
    private String CompanyId;
    private String BusID;
    private String PAN;
    private String Status = "Incomplete";
    // endregion variables
    public BaseTrip(LocalDateTime started, String fromStopId, String companyId, String busID, String PAN) {
        Started = started;
        FromStopId = fromStopId;
        CompanyId = companyId;
        BusID = busID;
        this.PAN = PAN;
    }

    public void addInfo(LocalDateTime finished,String toStopId){
        Finished = finished;
        ToStopId = toStopId;
        DurationSecs = (int)ChronoUnit.SECONDS.between(Started, Finished);
        Status = FromStopId.equals(toStopId) ? "Cancelled" : "Completed";
    }

    public void calculatePrice (Stop info){
        int stopNum = Integer.parseInt(FromStopId.toLowerCase().replace("stop",""));
        LinkedList<Edge> priceTableForStop = info.getPriceTableForStop(stopNum); // get price table for a particular stop
        // Incomplete trip: get max price among all possible price
        if (ToStopId==null){
            ChargeAmount = priceTableForStop.stream().max(Edge::compareTo).get().getWeight();
            return;
        }
        int toNum = Integer.parseInt(ToStopId.toLowerCase().replace("stop",""));
        // Completed trip: get the price for stop X and stop Y
        for (Edge price : priceTableForStop) {
            if (price.getDestination() == toNum){
                ChargeAmount =  price.getWeight();
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "" + parseDateTime(Started) + ","+
                parseDateTime(Finished) + ","+
                 DurationSecs + ","+
                 FromStopId  + ","+
                 ToStopId  + ","+
                 ChargeAmount + ","+
                 CompanyId  + ","+
                 BusID  + ","+
                 PAN  + ","+
                 Status;
    }

    private String parseDateTime(LocalDateTime dt){
        if (dt == null)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dt.format(formatter);
    }

    public LocalDateTime getStarted() {
        return Started;
    }

    public LocalDateTime getFinished() {
        return Finished;
    }

    public int getDurationSecs() {
        return DurationSecs;
    }

    public String getFromStopId() {
        return FromStopId;
    }

    public String getToStopId() {
        return ToStopId;
    }

    public float getChargeAmount() {
        return ChargeAmount;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public String getBusID() {
        return BusID;
    }

    public String getPAN() {
        return PAN;
    }

    public String getStatus() {
        return Status;
    }
}
