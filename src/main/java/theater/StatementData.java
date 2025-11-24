package theater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Holds calculated data needed to render a statement.
 */
public class StatementData {

    private final String customer;
    private final List<PerformanceData> performances;

    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.customer = invoice.getCustomer();
        this.performances = new ArrayList<>();

        for (Performance performance : invoice.getPerformances()) {
            Play play = plays.get(performance.getPlayID());
            AbstractPerformanceCalculator calculator =
                    AbstractPerformanceCalculator.createPerformanceCalculator(performance, play);

            PerformanceData perfData = new PerformanceData(
                    play.getName(),
                    performance.getAudience(),
                    play.getType(),
                    calculator.amountFor(),
                    calculator.volumeCredits());

            performances.add(perfData);
        }
    }

    public String getCustomer() {
        return customer;
    }

    public List<PerformanceData> getPerformances() {
        return performances;
    }

    public int totalAmount() {
        int result = 0;
        for (PerformanceData perfData : performances) {
            result += perfData.getAmount();
        }
        return result;
    }

    public int volumeCredits() {
        int result = 0;
        for (PerformanceData perfData : performances) {
            result += perfData.getVolumeCredits();
        }
        return result;
    }
}
