package com.radhaa.QUIZAPPLICATIONBackend.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="OtpStorage")
public class otpVerification
{
    public int getOtpid() {
        return Otpid;
    }

    public void setOtpid(int otpid) {
        Otpid = otpid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Otpid;
    private int otp;
    private String mail;
    private LocalDateTime expiryTime;

    public otpVerification() {
    }

    public otpVerification(int otp, String mail, LocalDateTime expiryTime) {
        this.otp = otp;
        this.mail = mail;
        this.expiryTime = expiryTime;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
}
