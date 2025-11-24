package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * Generates a customer statement for an invoice.
 */
public class StatementPrinter {

    private final Invoice invoice;
    private final Map<String, Play> plays;
    private final StatementData statementData;

    /**
     * Construct a new StatementPrinter.
     *
     * @param invoice the invoice
     * @param plays   the map of plays
     */
    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        this.statementData = new StatementData(invoice, plays);
    }

    /**
     * Return the plain text statement.
     *
     * @return formatted statement
     */
    public String statement() {
        return renderPlainText(statementData);
    }

    /**
     * Render a statement in plain text format.
     *
     * @param data the statement data
     * @return formatted plain-text string
     */
    protected String renderPlainText(StatementData data) {
        final StringBuilder result =
                new StringBuilder("Statement for " + data.getCustomer() + System.lineSeparator());

        for (final PerformanceData perfData : data.getPerformances()) {
            result.append(String.format(
                    "  %s: %s (%s seats)%n",
                    perfData.getName(),
                    usd(perfData.getAmount()),
                    perfData.getAudience()));
        }

        result.append(String.format("Amount owed is %s%n", usd(data.totalAmount())));
        result.append(String.format("You earned %s credits%n", data.volumeCredits()));

        return result.toString();
    }

    /* ----------------- Helper methods required by MarkUs tests ---------------- */

    private Play getPlay(final Performance performance) {
        return plays.get(performance.getPlayID());
    }

    private int getAmount(final Performance performance) {
        final Play play = getPlay(performance);
        final AbstractPerformanceCalculator calculator =
                AbstractPerformanceCalculator.createPerformanceCalculator(performance, play);
        return calculator.amountFor();
    }

    private int getVolumeCredits(final Performance performance) {
        final Play play = getPlay(performance);
        final AbstractPerformanceCalculator calculator =
                AbstractPerformanceCalculator.createPerformanceCalculator(performance, play);
        return calculator.volumeCredits();
    }

    private int getTotalAmount() {
        int result = 0;
        for (final Performance performance : invoice.getPerformances()) {
            result += getAmount(performance);
        }
        return result;
    }

    private int getTotalVolumeCredits() {
        int result = 0;
        for (final Performance performance : invoice.getPerformances()) {
            result += getVolumeCredits(performance);
        }
        return result;
    }

    /**
     * Convert a number to USD currency.
     *
     * @param amount amount in cents
     * @return formatted currency string
     */
    private String usd(final int amount) {
        final NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(amount / Constants.PERCENT_FACTOR);
    }
}
