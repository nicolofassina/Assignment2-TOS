////////////////////////////////////////////////////////////////////
// [Nicolo] [Fassina] [1166190]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business.exception;

public class TakeAwayBillException extends Throwable {
    private String error;

    public TakeAwayBillException(String description) {
        error = description;
    }

    public String getError() {
        return error;
    }
}
