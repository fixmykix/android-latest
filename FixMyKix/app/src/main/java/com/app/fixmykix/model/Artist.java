package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Artist implements Parcelable{
    @SerializedName("id")
    private Long id;
    @SerializedName("email")
    private String email;
    @SerializedName("mobile_number")
    private String mobileNumber;
    @SerializedName("password_digest")
    private String passwordDigest;
    @SerializedName("registration_token")
    private String registrationToken;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("gender")
    private String gender;
    @SerializedName("date_of_birth")
    private String dateOfBirth;
    @SerializedName("profile_image")
    private ProfileImage profileImage;
    @SerializedName("fb_device_token")
    private String fbDeviceToken;
    @SerializedName("reset_password_token")
    private String resetPasswordToken;
    @SerializedName("reset_password_sent_at")
    private String resetPasswordSentAt;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("role")
    private Long role;
    @SerializedName("status")
    private String status;
    @SerializedName("address")
    private String address;
    @SerializedName("age")
    private String age;
    @SerializedName("artist_services")
    private ArrayList<ArtistService> artistServices;

    protected Artist(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        email = in.readString();
        mobileNumber = in.readString();
        passwordDigest = in.readString();
        registrationToken = in.readString();
        phoneNumber = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        gender = in.readString();
        dateOfBirth = in.readString();
        profileImage = in.readParcelable(ProfileImage.class.getClassLoader());
        fbDeviceToken = in.readString();
        resetPasswordToken = in.readString();
        resetPasswordSentAt = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        if (in.readByte() == 0) {
            role = null;
        } else {
            role = in.readLong();
        }
        status = in.readString();
        address = in.readString();
        age = in.readString();
        artistServices = in.createTypedArrayList(ArtistService.CREATOR);
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public String getFbDeviceToken() {
        return fbDeviceToken;
    }

    public void setFbDeviceToken(String fbDeviceToken) {
        this.fbDeviceToken = fbDeviceToken;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getResetPasswordSentAt() {
        return resetPasswordSentAt;
    }

    public void setResetPasswordSentAt(String resetPasswordSentAt) {
        this.resetPasswordSentAt = resetPasswordSentAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public ArrayList<ArtistService> getArtistServices() {
        return artistServices;
    }

    public void setArtistServices(ArrayList<ArtistService> artistServices) {
        this.artistServices = artistServices;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(email);
        parcel.writeString(mobileNumber);
        parcel.writeString(passwordDigest);
        parcel.writeString(registrationToken);
        parcel.writeString(phoneNumber);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(gender);
        parcel.writeString(dateOfBirth);
        parcel.writeParcelable(profileImage, i);
        parcel.writeString(fbDeviceToken);
        parcel.writeString(resetPasswordToken);
        parcel.writeString(resetPasswordSentAt);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        if (role == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(role);
        }
        parcel.writeString(status);
        parcel.writeString(address);
        parcel.writeString(age);
        parcel.writeTypedList(artistServices);
    }
}
