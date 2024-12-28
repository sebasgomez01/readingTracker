package com.csgp.backend.dto;

public class BearerToken {

    private String accessToken;
    private String tokenType;

    public BearerToken(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    // Getters
    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    // Setters
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "BearerToken{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }

    // Método equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BearerToken that = (BearerToken) o;

        if (!accessToken.equals(that.accessToken)) return false;
        return tokenType.equals(that.tokenType);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        int result = accessToken.hashCode();
        result = 31 * result + tokenType.hashCode();
        return result;
    }
}

