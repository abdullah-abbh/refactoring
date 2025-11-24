package theater;

import java.util.Map;

/**
 * Statement printer that renders the invoice as HTML.
 */
public class HTMLStatementPrinter extends StatementPrinter {

    /**
     * Construct an HTML statement printer.
     *
     * @param invoice invoice data
     * @param plays   map of plays
     */
    public HTMLStatementPrinter(Invoice invoice, Map<String, Play> plays) {
        super(invoice, plays);
    }

    /**
     * Override statement() to generate HTML.
     *
     * @return HTML formatted invoice
     */
    @Override
    public String statement() {

        final StringBuilder result = new StringBuilder(
                String.format("<h1>Statement for %s</h1>%n", getStatementData().getCustomer())
        );

        result.append("<table>").append(System.lineSeparator());
        result.append(String.format(" <caption>Statement for %s</caption>%n",
                getStatementData().getCustomer()));
        result.append(" <tr><th>play</th><th>seats</th><th>cost</th></tr>")
                .append(System.lineSeparator());

        for (final PerformanceData perfData : getStatementData().getPerformances()) {
            result.append(String.format(
                    " <tr><td>%s</td><td>%d</td><td>%s</td></tr>%n",
                    perfData.getName(),
                    perfData.getAudience(),
                    usd(perfData.getAmount())
            ));
        }

        result.append("</table>").append(System.lineSeparator());
        result.append(String.format(
                "<p>Amount owed is <em>%s</em></p>%n",
                usd(getStatementData().totalAmount())
        ));
        result.append(String.format(
                "<p>You earned <em>%s</em> credits</p>%n",
                getStatementData().volumeCredits()
        ));

        return result.toString();
    }

    /**
     * Getter needed so HTML class can access the data.
     *
     * @return the statement data
     */
    private StatementData getStatementData() {
        return super.statementData;
    }
}
