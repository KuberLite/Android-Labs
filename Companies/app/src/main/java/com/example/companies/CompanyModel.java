package com.example.companies;

public class CompanyModel {
    public String email;

    public String location;

    public String phone;

    public String socialNetworkLink;

    public CompanyModel(String email, String location, String phone, String socialNetworkLink) {
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.socialNetworkLink = socialNetworkLink;
    }
}
