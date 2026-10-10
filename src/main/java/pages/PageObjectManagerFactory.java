
package pages;

public final class PageObjectManagerFactory {

    private static final ThreadLocal<PageObjectManager>
            PAGE_OBJECT_MANAGER = new ThreadLocal<>();

    private PageObjectManagerFactory() {
        // Utility class
    }

    public static void initialize() {
        if (PAGE_OBJECT_MANAGER.get() != null) {
            throw new IllegalStateException(
                    "PageObjectManager is already initialized for the current thread.");
        }

        PAGE_OBJECT_MANAGER.set(new PageObjectManager());
    }

    public static PageObjectManager get() {
        PageObjectManager manager = PAGE_OBJECT_MANAGER.get();

        if (manager == null) {
            throw new IllegalStateException(
                    "PageObjectManager is not initialized for the current thread.");
        }

        return manager;
    }

    public static void remove() {
        PAGE_OBJECT_MANAGER.remove();
    }
}
