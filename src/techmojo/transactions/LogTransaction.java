package techmojo.transactions;

import java.util.List;

public class LogTransaction {
    /** eager loading
     * Its not fully protected Singleton class. Because we are not consider following cases as of now
     *      Thread
     *      Serialization
     *      Cloning
     *      Reflection
     */
    private static LogTransaction logTransaction = new LogTransaction();
    private LogTransaction(){};
    public static LogTransaction getInstance(){
        return logTransaction;
    }

    /**
     *
     * @param faultTransactions
     */
    public void logFaultTransaction(List<String> faultTransactions){
        System.out.println("Faulty Transactions");
        for(String trans : faultTransactions){
            System.out.println(trans);
        }
    }
}
