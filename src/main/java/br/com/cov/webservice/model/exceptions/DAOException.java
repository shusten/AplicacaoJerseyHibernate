package br.com.cov.webservice.model.exceptions;

public class DAOException extends RuntimeException {

    private int code;

    public DAOException(String messege, int code) {
        super(messege);
        this.code = code;
    }

    public int getCode() {
     return code;
    }

}
