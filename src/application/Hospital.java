package application;

public class Hospital {
    private String hostName = null, phoneNumber = null, streetAddress = null, city=null;
    private int vaccineCount = 0;
    /*
    Empty Constructor will default all fields to null
    Shouldn't be used
     */
    public Hospital() {
        hostName = phoneNumber = streetAddress = city = null;
    }

    /**
     * Hospital Constructor - should be used to create new Hostpital objects
     * @param host - hostName
     * @param phoneNum - phoneNumber
     * @param addrs - streetAddress
     * @param city - city
     */
    public Hospital(String host, String phoneNum, String addrs, String city){
        this.hostName = host;
        this.phoneNumber = phoneNum;
        this.streetAddress = addrs;
        this.city = city;
    }

    public String getHostName() {
        return hostName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public int getVaccineCount() {
        return vaccineCount;
    }

    public void setVaccineCount(int vaccineCount) {
        this.vaccineCount = vaccineCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hospital)) return false;
        Hospital hospital = (Hospital) o;
        return getStreetAddress().toUpperCase().compareTo(hospital.getStreetAddress().toUpperCase()) == 0 && getCity().toUpperCase().compareTo(hospital.getCity().toUpperCase())==0;
    }
}