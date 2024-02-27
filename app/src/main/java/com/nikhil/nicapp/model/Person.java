package com.nikhil.nicapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "persons")
public class Person implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private boolean isMarried;
    private String gender;
    private String address;
    private String imageUri;
    private double latitude;
    private double longitude;
    public Person() {
    }

    public Person(Parcel in) {
        id = in.readInt();
        name = in.readString();
        isMarried = in.readByte() != 0;
        gender = in.readString();
        address = in.readString();
        imageUri = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (isMarried ? 1 : 0));
        dest.writeString(gender);
        dest.writeString(address);
        dest.writeString(imageUri);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public boolean isMarried() {
        return isMarried;
    }

    public String getGender() {
        return gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}