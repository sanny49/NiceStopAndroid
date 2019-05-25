package com.example.nicestop;

public class License {
    private String LicenseNo;
    private String Address;
    private String To;

    public License() {
    }

    public License(String LicenseNo, String Address, String To) {
        this.LicenseNo = LicenseNo;
        this.Address = Address;
        this.To = To;
    }

    public String getLicenseNo() {
        return LicenseNo;
    }

    public void setLicenseNo(String LicenseNo) {
        this.LicenseNo = LicenseNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String To) {
        this.To = To;
    }
}
