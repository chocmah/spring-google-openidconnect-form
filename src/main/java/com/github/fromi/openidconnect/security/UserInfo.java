package com.github.fromi.openidconnect.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {
    private final String id;
    private final String name;
    private final String givenName;
    private final String familyName;
    private final String gender;
    private final String picture;
    private final String link;
    private final String email;
    private final String verified_email;
    private final String hd;
    @JsonCreator
    public UserInfo(@JsonProperty("id") String id,
                    @JsonProperty("name") String name,
                    @JsonProperty("given_name") String givenName,
                    @JsonProperty("family_name") String familyName,
                    @JsonProperty("gender") String gender,
                    @JsonProperty("picture") String picture,
                    @JsonProperty("email") String email,
                    @JsonProperty("verified_email") String verified_email,
                    @JsonProperty("hd") String hd,
                    @JsonProperty("link") String link) {
        this.id = id;
        this.name = name;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gender = gender;
        this.picture = picture;
        this.link = link;
        this.email = email;
        this.verified_email = verified_email;
        this.hd = hd;
       
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGender() {
        return gender;
    }

    public String getPicture() {
        return picture;
    }

    public String getLink() {
        return link;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getVerifiedEmail() {
        return verified_email;
    }
    public String getHd(){
    	return hd;
    }
}
