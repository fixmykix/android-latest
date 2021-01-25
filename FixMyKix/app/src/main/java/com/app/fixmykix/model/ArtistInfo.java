package com.app.fixmykix.model;

import com.google.gson.annotations.SerializedName;

public class ArtistInfo{

	@SerializedName("role")
	private int role;

	@SerializedName("address")
	private String address;

	@SerializedName("gender")
	private String gender;

	@SerializedName("date_of_birth")
	private String dateOfBirth;

	@SerializedName("reset_password_sent_at")
	private String resetPasswordSentAt;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("fb_device_token")
	private String fbDeviceToken;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("reset_password_token")
	private String resetPasswordToken;

	@SerializedName("profile_image")
	private ProfileImage profileImage;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("registration_token")
	private String registrationToken;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("password_digest")
	private String passwordDigest;

	@SerializedName("mobile_number")
	private String mobileNumber;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("age")
	private String age;

	@SerializedName("status")
	private String status;

	public int getRole(){
		return role;
	}

	public String getAddress(){
		return address;
	}

	public String getGender(){
		return gender;
	}

	public String getDateOfBirth(){
		return dateOfBirth;
	}

	public String getResetPasswordSentAt(){
		return resetPasswordSentAt;
	}

	public String getLastName(){
		return lastName;
	}

	public String getFbDeviceToken(){
		return fbDeviceToken;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getResetPasswordToken(){
		return resetPasswordToken;
	}

	public ProfileImage getProfileImage(){
		return profileImage;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getRegistrationToken(){
		return registrationToken;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public int getId(){
		return id;
	}

	public String getPasswordDigest(){
		return passwordDigest;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}

	public String getAge(){
		return age;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ArtistInfo{" + 
			"role = '" + role + '\'' + 
			",address = '" + address + '\'' + 
			",gender = '" + gender + '\'' + 
			",date_of_birth = '" + dateOfBirth + '\'' + 
			",reset_password_sent_at = '" + resetPasswordSentAt + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",fb_device_token = '" + fbDeviceToken + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",reset_password_token = '" + resetPasswordToken + '\'' + 
			",profile_image = '" + profileImage + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",registration_token = '" + registrationToken + '\'' + 
			",phone_number = '" + phoneNumber + '\'' + 
			",id = '" + id + '\'' + 
			",password_digest = '" + passwordDigest + '\'' + 
			",mobile_number = '" + mobileNumber + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",email = '" + email + '\'' + 
			",age = '" + age + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}