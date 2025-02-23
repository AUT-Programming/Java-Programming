/**
 * Represents a book in the library.
 */
class Book {
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }
}

/**
 * Represents a member of the library.
 */
class Member {
    private String memberId;
    private String name;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }
}

/**
 * Represents a loan transaction in the library.
 */
class Loan {
    private Member member;
    private Book book;
    private String loanDate;
    private String dueDate;
    private boolean isReturned;

    public Loan(Member member, Book book, String loanDate, String dueDate) {
        this.member = member;
        this.book = book;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.isReturned = false;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void returnBook() {
        isReturned = true;
        book.setBorrowed(false);
    }
}

/**
 * Manages the library operations.
 */
class Library {
    private Book[] books;
    private Member[] members;
    private Loan[] loans;
    private int bookCount;
    private int memberCount;
    private int loanCount;
    public static final int LIBRARY_CAPACITY = 100;
    public static final int MEMBER_CAPACITY = 50;
    public static final int LOAN_CAPACITY = 100;

    public Library() {
        books = new Book[LIBRARY_CAPACITY];
        members = new Member[MEMBER_CAPACITY];
        loans = new Loan[LOAN_CAPACITY];
        bookCount = 0;
        memberCount = 0;
        loanCount = 0;
    }

    public void addBook(Book book) {
        if (bookCount < LIBRARY_CAPACITY) {
            books[bookCount++] = book;
            System.out.println("Added book: " + book.getTitle());
        } else {
            System.out.println("Library is at full capacity.");
        }
    }

    public void addMember(Member member) {
        if (memberCount < MEMBER_CAPACITY) {
            members[memberCount++] = member;
            System.out.println("Added member: " + member.getName());
        } else {
            System.out.println("Member capacity reached.");
        }
    }

    public void borrowBook(String memberId, String bookTitle, String loanDate, String dueDate) {
        Member member = findMemberById(memberId);
        Book book = findBookByTitle(bookTitle);
        if (member != null && book != null && !book.isBorrowed()) {
            if (loanCount < LOAN_CAPACITY) {
                book.setBorrowed(true);
                loans[loanCount++] = new Loan(member, book, loanDate, dueDate);
                System.out.println(member.getName() + " borrowed " + book.getTitle());
            } else {
                System.out.println("Loan capacity reached.");
            }
        } else {
            System.out.println("Cannot borrow book.");
        }
    }

    public void returnBook(String memberId, String bookTitle) {
        for (int i = 0; i < loanCount; i++) {
            Loan loan = loans[i];
            if (loan.getMember().getMemberId().equals(memberId) &&
                    loan.getBook().getTitle().equals(bookTitle) &&
                    !loan.isReturned()) {
                loan.returnBook();
                System.out.println(loan.getMember().getName() + " returned " + loan.getBook().getTitle());
                return;
            }
        }
        System.out.println("Loan record not found.");
    }

    private Book findBookByTitle(String title) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                return books[i];
            }
        }
        return null;
    }

    private Member findMemberById(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equalsIgnoreCase(memberId)) {
                return members[i];
            }
        }
        return null;
    }

    public void displayBooks() {
        if (bookCount == 0) {
            System.out.println("No books available in the library.");
        } else {
            System.out.println("Books available in the library:");
            for (int i = 0; i < bookCount; i++) {
                System.out.println((i + 1) + ". " + books[i].getTitle() + " by " + books[i].getAuthor() +
                        (books[i].isBorrowed() ? " (Borrowed)" : " (Available)"));
            }
        }
    }

    public void displayMembers() {
        if (memberCount == 0) {
            System.out.println("No members registered in the library.");
        } else {
            System.out.println("Members of the library:");
            for (int i = 0; i < memberCount; i++) {
                System.out.println((i + 1) + ". " + members[i].getName() + " (ID: " + members[i].getMemberId() + ")");
            }
        }
    }

    public static void main(String[] args) {
        Library library = new Library();

        library.addBook(new Book("Effective Java", "Joshua Bloch"));
        library.addBook(new Book("Clean Code", "Robert C. Martin"));
        library.addBook(new Book("Design Patterns", "Erich Gamma"));

        library.addMember(new Member("M001", "Alice"));
        library.addMember(new Member("M002", "Bob"));

        library.displayBooks();

        library.borrowBook("M001", "Effective Java", "2025-02-20", "2025-03-20");

        library.displayBooks();

        library.returnBook("M001", "Effective Java");

        library.displayBooks();
    }
}

