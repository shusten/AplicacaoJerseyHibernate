package br.com.cov.webservice.model.exceptions;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 3965087475900464946L;
    private int code;

    public DAOException(String messege, int code) {
        super( messege );
        this.code = code;
    }

    public int getCode() {
     return code;
    }

}
