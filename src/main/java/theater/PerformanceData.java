package theater;

/**
 * Calculated data for a single performance.
 */
public class PerformanceData {

    private final String name;
    private final int audience;
    private final String type;
    private final int amount;
    private final int volumeCredits;

    public PerformanceData(String name, int audience, String type,
                           int amount, int volumeCredits) {
        this.name = name;
        this.audience = audience;
        this.type = type;
        this.amount = amount;
        this.volumeCredits = volumeCredits;
    }

    public String getName() {
        return name;
    }

    public int getAudience() {
        return audience;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getVolumeCredits() {
        return volumeCredits;
    }
}

