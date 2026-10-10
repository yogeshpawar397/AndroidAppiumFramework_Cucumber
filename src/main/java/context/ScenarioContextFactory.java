package context;

public final class ScenarioContextFactory {

    private static final ThreadLocal<ScenarioContext>
            SCENARIO_CONTEXT = new ThreadLocal<>();

    private ScenarioContextFactory() {
        // Utility class
    }

    public static void initialize() {

        SCENARIO_CONTEXT.set(
                new ScenarioContext()
        );
    }

    public static ScenarioContext get() {

        ScenarioContext context =
                SCENARIO_CONTEXT.get();

        if (context == null) {
            throw new IllegalStateException(
                    "ScenarioContext is not initialized "
                            + "for the current thread."
            );
        }

        return context;
    }

    public static void remove() {

        ScenarioContext context =
                SCENARIO_CONTEXT.get();

        if (context != null) {
            context.clear();
        }

        SCENARIO_CONTEXT.remove();
    }

    public static boolean isInitialized() {
        return SCENARIO_CONTEXT.get() != null;
    }
}