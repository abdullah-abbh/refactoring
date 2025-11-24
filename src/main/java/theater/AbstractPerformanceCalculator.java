package theater;

/**
 * Calculates amount and credits for a single performance.
 */
public abstract class AbstractPerformanceCalculator {

    private final Performance performance;
    private final Play play;

    protected AbstractPerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    /**
     * Factory method for creating the appropriate calculator subclass.
     *
     * @param performance performance
     * @param play        play
     * @return calculator instance
     */
    public static AbstractPerformanceCalculator createPerformanceCalculator(
            Performance performance, Play play) {

        switch (play.getType()) {
            case "tragedy":
                return new TragedyCalculator(performance, play);
            case "comedy":
                return new ComedyCalculator(performance, play);
            case "history":
                return new HistoryCalculator(performance, play);
            case "pastoral":
                return new PastoralCalculator(performance, play);
            default:
                throw new RuntimeException(
                        String.format("unknown type: %s", play.getType()));
        }
    }

    /**
     * Compute the base amount for this performance.
     *
     * @return amount in cents
     */
    public abstract int amountFor();

    /**
     * Compute volume credits for this performance.
     *
     * @return credits
     */
    public int volumeCredits() {
        return Math.max(performance.getAudience()
                - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
    }

    protected Performance getPerformance() {
        return performance;
    }

    protected Play getPlay() {
        return play;
    }
}
