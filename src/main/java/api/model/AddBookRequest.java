package api.model;

public class AddBookRequest {

    private String userId;
    private CollectionIsbn[] collectionOfIsbns;

    public AddBookRequest(String userId, String isbn) {
        this.userId = userId;
        this.collectionOfIsbns = new CollectionIsbn[]{
                new CollectionIsbn(isbn)
        };
    }

    public String getUserId() {
        return userId;
    }

    public CollectionIsbn[] getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public static class CollectionIsbn {

        private String isbn;

        public CollectionIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getIsbn() {
            return isbn;
        }
    }

    public static class Isbn {

        private String isbn;

        public Isbn(String isbn) {
            this.isbn = isbn;
        }

        public String getIsbn() {
            return isbn;
        }
    }
}