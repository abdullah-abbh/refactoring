package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates statements for a given invoice of performances.
 */
public class StatementPrinter {

    private final Invoice invoice;
    private final Map<String, Play> plays;
    protected final StatementData statementData;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
        this.statementData = new StatementData(invoice, plays);
    }

    /**
     * Returns the plain-text statement for this invoice.
     *
     * @return plain-text statement
     */
    public String statement() {
        return renderPlainText(statementData);
    }

    /**
     * Render the statement as plain text.
     *
     * @param data statement data
     * @return plain-text representation
     */
    protected String renderPlainText(StatementData data) {
        StringBuilder result = new StringBuilder(
                "Statement for " + data.getCustomer() + System.lineSeparator());

        for (PerformanceData perfData : data.getPerformances()) {
            result.append(String.format("  %s: %s (%s seats)%n",
                    perfData.getName(),
                    usd(perfData.getAmount()),
                    perfData.getAudience()));
        }

        result.append(String.format("Amount owed is %s%n",
                usd(data.totalAmount())));
        result.append(String.format("You earned %s credits%n",
                data.volumeCredits()));

        return result.toString();
    }

    /**
     * Formats an amount as US dollars.
     *
     * @param amount amount in cents
     * @return formatted amount string
     */
    protected String usd(int amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(amount / Constants.PERCENT_FACTOR);
    }
}
