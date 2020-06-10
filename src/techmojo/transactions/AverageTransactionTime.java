package techmojo.transactions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
    Transaction Id T1236. I could see in input data we don't have end transaction entry for this id.
    So I assumed we could have faulty transaction data. Therefore I've discarded those entries.

    Assumption:
    One another case which I could think of regarding the fault transaction.
    We could have multiple entries of start transaction before it ends.
    But for now this case is not handel.
 **/

public class AverageTransactionTime {
    LogTransaction logTransaction = LogTransaction.getInstance();
    /*
        Sample Input:
         inputs.add("T1234, 2020 – 03 – 01 , 3:15 pm, start");
         inputs.add("T1234, 2020 – 03 – 01 , 3:18 pm, End");
     */

    /**
     *
     * @param transactionList
     * @throws ParseException
     */
    public void averageTransactionTime(List<String> transactionList) throws ParseException{
        HashMap<String, String> startTimeMap = new HashMap<>();
        HashMap<String, String> endTimeMap = new HashMap<>();
        List<String> faultTransactions = new ArrayList<>();
        double diffBtwTransactions = 0.0;

        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_PATTERN);
        for (String val : transactionList){
            String[] transaction = val.split(Constants.SPLIT_REGEX);
            if (transaction[3].equalsIgnoreCase(Constants.START_LABEL)){
                startTimeMap.put(transaction[0], transaction[1]+transaction[2]);
            }
            else {
                endTimeMap.put(transaction[0], transaction[1]+transaction[2]);
            }
        }
        for (String key:startTimeMap.keySet()) {
            //discarding faulty transaction (only one entry available either start or end)
            if (endTimeMap.containsKey(key)) {
                Date start = formatter.parse(startTimeMap.get(key));
                Date end = formatter.parse(endTimeMap.get(key));
                long diffInMillis = Math.abs(start.getTime() - end.getTime());
                diffBtwTransactions = diffBtwTransactions +
                        TimeUnit.SECONDS.convert(diffInMillis, TimeUnit.MILLISECONDS);
            }else{
                faultTransactions.add(key);
            }
        }
        double avg = diffBtwTransactions/((double)startTimeMap.size()) ;
        logTransaction.logFaultTransaction(faultTransactions);
        System.out.println("Average Transaction Time In Seconds : "+avg);

    }


    public static void main(String[] args) {

        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("T1234, 2020 – 03 – 01 , 3:15 pm, start");
        inputs.add("T1235, 2020 – 03 – 01 , 3:16 pm, start");
        inputs.add("T1236, 2020 – 03 – 01 , 3:17 pm, start");
        inputs.add("T1234, 2020 – 03 – 01 , 3:18 pm, End");
        inputs.add("T1235, 2020 – 03 – 01 , 3:18 pm, End");

        try {
            new AverageTransactionTime().averageTransactionTime(inputs);
        }catch(ParseException e){
            System.out.println(e.getMessage());
        }

    }
}
