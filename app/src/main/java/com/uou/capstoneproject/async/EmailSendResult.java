package com.uou.capstoneproject.async;

public class EmailSendResult {
    private String authEmailCheck;

    public EmailSendResult(String authEmailCheck) {
        this.authEmailCheck = authEmailCheck;
    }

    public String getAuthEmailCheck() {
        return authEmailCheck;
    }

    public void setAuthEmailCheck(String authEmailCheck) {
        this.authEmailCheck = authEmailCheck;
    }

    @Override
    public String toString() {
        return "EmailSendResult{" +
                "authEmailCheck='" + authEmailCheck + '\'' +
                '}';
    }
}
