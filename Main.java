package org.example;

import java.util.*;

class Book {
    private int id;
    private String title;
    private String author;
    private int copies;
    private int available_copies;

    public Book(int id, String title, String author, int copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.copies = copies;
        this.available_copies = copies;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getAvailableCopies() {
        return available_copies;
    }

    public void decrease_copies() {
        if (available_copies > 0) {
            available_copies--;
        }
    }

    public void increase_copies() {
        if (available_copies < copies) {
            available_copies++;
        }
    }
}

class Member {
    private String name;
    private int age;
    private String phone;
    private List<Book> borrowed_books;
    private int memberId;
    private boolean loggedIn;
    private double amount;
    private Date due_time;
    private int number_borrowed;


    public Member(String name, int age, String phoneNumber, int memberId) {
        this.name = name;
        this.age = age;
        this.phone = phoneNumber;
        this.memberId = memberId;
        this.borrowed_books = new ArrayList<>();
        this.loggedIn = false;
        this.amount = 0;
        this.number_borrowed = 0;

    }


    public Date getDue_time() {
        return due_time;
    }

    public void set_due_time(Date due_time) {
        this.due_time = due_time;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public int getMemberId() {
        return memberId;
    }

    public List<Book> getBorrowed_books() {
        return borrowed_books;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void login() {
        loggedIn = true;
    }

    public void logout() {
        loggedIn = false;
    }

    public boolean can_borrow() {
        return amount == 0 && number_borrowed < 2;
    }

    public void clear_amount() {
        amount = 0;
    }

    public void borrowBook(Book book) {
        if (isLoggedIn() && can_borrow() && book.getAvailableCopies() > 0) {
            if (number_borrowed < 2) {
                borrowed_books.add(book);
                book.decrease_copies();
                number_borrowed++;
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, 10);
                due_time = calendar.getTime();
//                System.out.println("Book '" + book.getTitle() + "' successfully borrowed.");
            } else {
                System.out.println("You cannot borrow more than 2 books.");
            }
        }
    }

    public void returnBook(Book book, double fine) {
        if (isLoggedIn()) {
            if (due_time != null) {
                Date currentTime = new Date();
                long secondsLate = (currentTime.getTime() - due_time.getTime()) / 1000;

                if (secondsLate > 0) {
                    fine = secondsLate * 3;
                    amount += fine;
                    System.out.println("Fine of Rs. " + fine + " generated for returning the book late.");
                }
            }
            borrowed_books.remove(book);
            book.increase_copies();
            number_borrowed--;

            borrowed_books.remove(book);
            book.increase_copies();
        }
    }

    public void pay_dues(double amount) {
        if (amount <= this.amount) {
            this.amount -= amount;
        }
    }
}

class Library {
    private Map<Integer, Book> booksMap;
    private Map<Integer, Member> membersMap;
    private int book_id_counter;
    private int member_id_counter;

    public Library() {
        booksMap = new HashMap<>();
        membersMap = new HashMap<>();
        book_id_counter = 1;
        member_id_counter = 1;
    }

    public void add_book(String title, String author, int copies) {
        for (int i = 0; i < copies; i++) {
            int bookId = book_id_counter++;
            Book book = new Book(bookId, title, author, 1);
            booksMap.put(bookId, book);
        }
    }

    public void remove_book(int bookId) {
        booksMap.remove(bookId);
    }

    public void add_member(String name, int age, String phoneNumber) {
        int memberId = member_id_counter++;
        Member member = new Member(name, age, phoneNumber, memberId);
        membersMap.put(memberId, member);
    }

    public void remove_member(int memberId) {
        membersMap.remove(memberId);
    }

    public Member getMemberById(int memberId) {
        return membersMap.get(memberId);
    }

    public List<Member> getMembers() {
        return new ArrayList<>(membersMap.values());
    }

    public List<Book> getBooks() {
        return new ArrayList<>(booksMap.values());
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        List<Book> availableBooks = new ArrayList<>();

        System.out.println("Library Portal Initialized….");
        while (true) {
            System.out.println("---------------------------------");
            System.out.println("1. Enter as a librarian");
            System.out.println("2. Enter as a member");
            System.out.println("3. Exit");
            System.out.println("---------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                librarianMenu:
                while (true) {
                    System.out.println("---------------------------------");
                    System.out.println("1. Register a member\n2. Remove a member\n3. Add a book\n4. Remove a book" +
                            "\n5. View all members along with their books and fines to be paid\n6. View all books details\n7. Back");
                    System.out.println("---------------------------------");

                    System.out.print("Enter your choice: ");
                    int librarian_choice = scanner.nextInt();

                    if (librarian_choice == 1) {
                        // Register a member
                        System.out.print("Enter name: ");
                        String memberName = scanner.next();
                        System.out.print("Enter age: ");
                        int memberAge = scanner.nextInt();

                        System.out.print("Enter phone number: ");
                        String memberPhoneNumber = scanner.next();

                        library.add_member(memberName, memberAge, memberPhoneNumber);
                        System.out.println("Member Successfully Registered with Member ID: " + (library.getMembers().size()));
                    } else if (librarian_choice == 2) {
                        // Remove a member
                        System.out.print("Enter the Member ID to remove: ");
                        int memberIdToRemove = scanner.nextInt();

                        Member memberToRemove = library.getMemberById(memberIdToRemove);
                        if (memberToRemove != null) {
                            library.remove_member(memberIdToRemove);
                            System.out.println("Member with Member ID: " + memberIdToRemove + " has been successfully removed.");
                        } else {
                            System.out.println("Member with Member ID: " + memberIdToRemove + " not found.");
                        }
                    } else if (librarian_choice == 3) {
                        // Add a book
                        System.out.print("Enter the book title: ");
                        scanner.nextLine();
                        String title = scanner.nextLine();

                        System.out.print("Enter the book author: ");
                        String author = scanner.nextLine();

                        System.out.print("Enter no. of copies: ");
                        int copies = scanner.nextInt();

                        library.add_book(title, author, copies);

                        System.out.println("Book added successfully!");
//                        break;
                    } else if (librarian_choice == 4) {
                        // Remove a book
                        System.out.print("Enter the book ID to remove: ");
                        int bookIdToRemove = scanner.nextInt();
                        library.remove_book(bookIdToRemove);

                        System.out.println("Book removed successfully!");
                    } else if (librarian_choice == 5) {
//                        System.out.println("List of all members along with their details, fines, and borrowed books:");
                        for (Member member : library.getMembers()) {
                            System.out.println("Member ID: " + member.getMemberId() + ", Name: " + member.getName() + ", Age: " + member.getAge() +
                                    ", Phone number: " + member.getPhone());

                            List<Book> borrowedBooks = member.getBorrowed_books();
                            if (!borrowedBooks.isEmpty()) {
                                System.out.println("Borrowed Books:");
                                for (Book book : borrowedBooks) {
                                    System.out.println("  - " + book.getTitle());
                                }
                            }

                            double penaltyAmount = member.getAmount();
                            System.out.println("Fines to be Paid: Rs. " + penaltyAmount);

                            System.out.println("------------------------------");
                        }
                    }
                    else if (librarian_choice == 6) {
                        // View all books along with their details
//                        System.out.println("List of all books along with their details:");
                        for (Book book : library.getBooks()) {
                            System.out.println("Book ID: " + book.getId()+ ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
                            System.out.println("------------------------------");
                        }
                    } else if (librarian_choice == 7) {
                        break librarianMenu;
                    }
//                    else {
//                        System.out.println("Invalid choice.");
//                    }
                }
            } else if (choice == 2) {
                Member current_member = null;

                System.out.print("Enter your name: ");
                String memberNameInput = scanner.next();
                System.out.print("Enter your phone number: ");
                String memberPhoneNumberInput = scanner.next();


                for (Member member : library.getMembers()) {
                    if (member.getName().equalsIgnoreCase(memberNameInput) && member.getPhone().equals(memberPhoneNumberInput)) {
                        current_member = member;
                        current_member.login();
                        System.out.println("Login successful. Welcome, " + current_member.getName() + "! Member ID: " + current_member.getMemberId());
                        break;
                    }
                }

                if (current_member == null) {
                    System.out.println("Member not found.");
                    break;
                }

                memberMenu:
                while (true) {
                    System.out.println("\nMember Menu:");
                    System.out.println("1. List Available Books\n2. List My Books\n3. Issue Book\n4. Return Book" +
                            "\n5. Pay Fine\n6. Back");
                    System.out.print("Enter your choice: ");
                    int member_choice = scanner.nextInt();

                    if (member_choice == 1) {
                        // List available books
                        System.out.println("\nAvailable Books:");
                        availableBooks = library.getBooks();
                        for (Book book : availableBooks) {
                            if (book.getAvailableCopies() > 0) {
                                System.out.println("Book ID: " + book.getId() + ", Title: " + book.getTitle()+ ", Author: " + book.getAuthor()+
                                        ", Available Copies: " + book.getAvailableCopies());
                                System.out.println("------------------------------");
                            }
                        }
                    } else if (member_choice == 2) {
                        // List member's borrowed books
                        if (current_member != null) {
                            System.out.println("\nBooks Borrowed by " + current_member.getName() + ":");
                            List<Book> borrowedBooks = current_member.getBorrowed_books();
                            if (borrowedBooks.isEmpty()) {
                                System.out.println("No books borrowed.");
                            } else {
                                for (Book book : borrowedBooks) {
                                    System.out.println("Book ID: " + book.getId()+ ", Title: " + book.getTitle()+ ", Author: " + book.getAuthor());
                                    System.out.println("---------------------------");
                                }
                            }
                        } else {
                            System.out.println("No member logged in.");
                        }
                    } else if (member_choice == 3) {
                        // Issue a book
                        if (current_member != null) {
                            System.out.println("\nList of Available Books:");
                            List<Book> allBooks = library.getBooks();
                            if (allBooks.isEmpty()) {
                                System.out.println("No books available.");
                            } else {
                                for (Book book : allBooks) {
                                    if (book.getAvailableCopies() > 0) {
                                        System.out.println("Book ID: " + book.getId() + ", Title: " + book.getTitle()+ ", Author: " + book.getAuthor()+
                                                ", Available Copies: " + book.getAvailableCopies());
                                        System.out.println("-----------------------------");
                                    }
                                }
                                System.out.print("Enter Book ID to borrow: ");
                                int bookIdToBorrow = scanner.nextInt();

                                Book bookToBorrow = null;
                                for (Book book : allBooks) {
                                    if (book.getId() == bookIdToBorrow) {
                                        bookToBorrow = book;
                                        break;
                                    }
                                }

                                if (bookToBorrow == null) {
                                    System.out.println("Book not found.");
                                } else {

                                    if (current_member.can_borrow()) {
                                        current_member.borrowBook(bookToBorrow);
                                        System.out.println("Book '" + bookToBorrow.getTitle() + "' successfully borrowed.");
                                    } else {
//                                        System.out.println("You have to clear your fine.");
                                        System.out.println("You've reached ur maximum limit for issuing books (2).");
                                    }
                                }
                            }
                        } else {
                            System.out.println("No member logged in.");
                        }
                    } else if (member_choice == 4) {
                        // Return a book
                        if (current_member == null) {
                            System.out.println("No member logged in.");
                        } else {
                            List<Book> borrowedBooks = current_member.getBorrowed_books();
                            if (borrowedBooks.isEmpty()) {
                                System.out.println("You haven't borrowed any books.");
                            } else {
                                System.out.println("\nList of Borrowed Books:");
                                for (Book book : borrowedBooks) {
                                    System.out.println("Book ID: " + book.getId()+", Title: " + book.getTitle()+
                                            ", Author: " + book.getAuthor());
                                    System.out.println("---------------");
                                }

                                System.out.print("Enter Book ID to return: ");
                                int bookIdToReturn = scanner.nextInt();

                                Book book_to_return = null;
                                for (Book book : borrowedBooks) {
                                    if (book.getId() == bookIdToReturn) {
                                        book_to_return = book;
                                        break;
                                    }
                                }

                                if (book_to_return == null) {
                                    System.out.println("Book not found in your borrowed books.");
                                } else {
                                    long current_time_ms = System.currentTimeMillis();
                                    long due_time_ms = current_member.getDue_time().getTime();
                                    long secondsLate = (current_time_ms - due_time_ms) / 1000;
                                    boolean fineGenerated=false;
                                    if (secondsLate > 0 && !fineGenerated) {
                                        double fine = secondsLate * 3;
                                        current_member.returnBook(book_to_return, fine);
                                        System.out.println("Fine of Rs. " + fine + " generated for returning the book late.");
//                                        break;
                                    } else {
                                        current_member.returnBook(book_to_return, 0);
                                    }

                                    System.out.println("Book '" + book_to_return.getTitle() + "' successfully returned.");
                                }
                            }
                        }
                    }

                    if (member_choice == 5) {
                        // Pay Fine
                        if (current_member != null) {
                            double penaltyAmount = current_member.getAmount();
                            if (penaltyAmount > 0) {
                                System.out.println("Your current fine amount is: Rs. " + penaltyAmount);
                                System.out.print("Enter the amount you want to pay (0 to cancel): ");
                                double amountToPay = scanner.nextDouble();

                                if (amountToPay < 0) {
                                    System.out.println("Invalid amount entered.");
                                } else if (amountToPay == 0) {
                                    System.out.println("Payment canceled.");
                                } else if (amountToPay <= penaltyAmount) {
                                    current_member.pay_dues(amountToPay);
                                    double remainingFine = penaltyAmount - amountToPay;
                                    System.out.println("Payment successful. Remaining fine: Rs. " + remainingFine);

                                } else {
                                    System.out.println("Payment exceeds the outstanding fine amount.");
                                }
                            } else {
                                System.out.println("You don't have any fines to pay.");
                            }
                        } else {
                            System.out.println("No member logged in.");
                        }
                    } else if (member_choice == 6) {
                        break memberMenu;
                    } else {
//                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else if (choice == 3) {
                System.out.println("Thanks for visiting!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}