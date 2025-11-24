package theater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Holds computed statement data for an invoice.
 */
public class StatementData {

    private final String customer;
    private final List<PerformanceData> performances = new ArrayList<>();

    /**
     * Create a StatementData object.
     *
     * @param invoice the invoice
     * @param plays   map of plays
     */
    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.customer = invoice.getCustomer();

        for (final Performance performance : invoice.getPerformances()) {
            final Play play = plays.get(performance.getPlayID());
            final AbstractPerformanceCalculator calculator =
                    AbstractPerformanceCalculator.createPerformanceCalculator(performance, play);

            final PerformanceData perfData = new PerformanceData(
                    play.getName(),
                    play.getType(),
                    performance.getAudience(),
                    calculator.amountFor(),
                    calculator.volumeCredits()
            );

            performances.add(perfData);
        }
    }

    /**
     * Return the customer name.
     *
     * @return customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Return the list of performance data.
     *
     * @return list of data
     */
    public List<PerformanceData> getPerformances() {
        return performances;
    }

    /**
     * Compute the total amount owed.
     *
     * @return total amount
     */
    public int totalAmount() {
        int result = 0;
        for (final PerformanceData data : performances) {
            result += data.getAmount();
        }
        return result;
    }

    /**
     * Compute total volume credits.
     *
     * @return total credits
     */
    public int volumeCredits() {
        int result = 0;
        for (final PerformanceData data : performances) {
            result += data.getVolumeCredits();
        }
        return result;
    }
}
