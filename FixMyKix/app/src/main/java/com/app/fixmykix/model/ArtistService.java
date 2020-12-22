package com.app.fixmykix.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ArtistService implements Parcelable {
    @SerializedName("id")
    private Long id;
    @SerializedName("service_name")
    private String serviceName;
    @SerializedName("service_id")
    private Long serviceId;
    @SerializedName("service_is_popular")
    private Boolean serviceIsPopular;
    @SerializedName("service_image")
    private ServiceImage serviceImage;
    @SerializedName("artist_id")
    private Long artistId;
    @SerializedName("description")
    private String description;
    @SerializedName("price")
    private Double price;
    @SerializedName("proof_of_work")
    private ProofOfWork proofOfWork;
    @SerializedName("number_of_days")
    private Long numberOfDays;
    public final static Parcelable.Creator<ArtistService> CREATOR = new Parcelable.Creator<ArtistService>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ArtistService createFromParcel(Parcel in) {
            return new ArtistService(in);
        }

        public ArtistService[] newArray(int size) {
            return (new ArtistService[size]);
        }

    };

    protected ArtistService(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.serviceName = ((String) in.readValue((String.class.getClassLoader())));
        this.serviceId = ((Long) in.readValue((Long.class.getClassLoader())));
        this.serviceIsPopular = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.serviceImage = ((ServiceImage) in.readValue((ServiceImage.class.getClassLoader())));
        this.artistId = ((Long) in.readValue((Long.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((Double) in.readValue((Double.class.getClassLoader())));
        this.proofOfWork = ((ProofOfWork) in.readValue((ProofOfWork.class.getClassLoader())));
        this.numberOfDays = ((Long) in.readValue((Long.class.getClassLoader())));
    }

    public ArtistService() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArtistService withId(Long id) {
        this.id = id;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ArtistService withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public ArtistService withServiceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Boolean getServiceIsPopular() {
        return serviceIsPopular;
    }

    public void setServiceIsPopular(Boolean serviceIsPopular) {
        this.serviceIsPopular = serviceIsPopular;
    }

    public ArtistService withServiceIsPopular(Boolean serviceIsPopular) {
        this.serviceIsPopular = serviceIsPopular;
        return this;
    }

    public ServiceImage getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(ServiceImage serviceImage) {
        this.serviceImage = serviceImage;
    }

    public ArtistService withServiceImage(ServiceImage serviceImage) {
        this.serviceImage = serviceImage;
        return this;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public ArtistService withArtistId(Long artistId) {
        this.artistId = artistId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArtistService withDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArtistService withPrice(Double price) {
        this.price = price;
        return this;
    }

    public ProofOfWork getProofOfWork() {
        return proofOfWork;
    }

    public void setProofOfWork(ProofOfWork proofOfWork) {
        this.proofOfWork = proofOfWork;
    }

    public ArtistService withProofOfWork(ProofOfWork proofOfWork) {
        this.proofOfWork = proofOfWork;
        return this;
    }

    public Long getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public ArtistService withNumberOfDays(Long numberOfDays) {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(serviceName);
        dest.writeValue(serviceId);
        dest.writeValue(serviceIsPopular);
        dest.writeValue(serviceImage);
        dest.writeValue(artistId);
        dest.writeValue(description);
        dest.writeValue(price);
        dest.writeValue(proofOfWork);
        dest.writeValue(numberOfDays);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof ArtistService) {
            ArtistService ptr = (ArtistService) v;
            retVal = ptr.id.longValue() == this.id;
        }

        return retVal;
    }
}
