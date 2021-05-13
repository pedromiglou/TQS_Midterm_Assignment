package tqsua.midterm_assignment.model;

public class AirQuality {
    private Long timeToLeave;

    private Integer humidity;
    private Integer aqius;
    private Integer aqicn;
    private String mainus;
    private String maincn;

    public AirQuality(Integer humidity, Integer aqius, Integer aqicn, String mainus, String maincn) {
        this.humidity = humidity;
        this.aqius = aqius;
        this.aqicn = aqicn;
        this.maincn = maincn;
        this.mainus = mainus;
        //timeout -> 30 min = 1800s = 1800000ms
        this.timeToLeave = System.currentTimeMillis() + 1800000;
    }

    public AirQuality() {
    }

    public Integer getAqicn() {
        return aqicn;
    }

    public void setAqicn(Integer aqicn) {
        this.aqicn = aqicn;
    }

    public String getMainus() {
        return mainus;
    }

    public void setMainus(String mainus) {
        this.mainus = mainus;
    }

    public String getMaincn() {
        return maincn;
    }

    public void setMaincn(String maincn) {
        this.maincn = maincn;
    }

    public Long getTimeToLeave() {
        return timeToLeave;
    }

    public void setTimeToLeave(Long timeToLeave) {
        this.timeToLeave = timeToLeave;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getAqius() {
        return aqius;
    }

    public void setAqius(Integer aqius) {
        this.aqius = aqius;
    }
}
