import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
class Book {
    private int bookId;
    private String bookName;
    private String author;
    private String category;
    private int availableCopies;
    public Book(int bookId, String bookName, String author, String category, int availableCopies) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.category = category;
        setAvailableCopies(availableCopies);
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public int getAvailableCopies() {
        return availableCopies;
    }
    public void setAvailableCopies(int availableCopies) {
        if (availableCopies >= 0) {
            this.availableCopies = availableCopies;
        } else {
            System.out.println("Available copies cannot be negative!");
        }
    }
}
class LibraryMember {
    private int memberId;
    private String memberName;
    private String contactNumber;
    public LibraryMember(int memberId, String memberName, String contactNumber) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.contactNumber = contactNumber;
    }
    public int getMemberId() {
        return memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public void displayMemberDetails() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Member Name: " + memberName);
        System.out.println("Contact Number: " + contactNumber);
    }
}
class Student extends LibraryMember {
    private String department;
    private int year;
    public Student(int memberId, String memberName, String contactNumber, String department, int year) {
        super(memberId, memberName, contactNumber);
        this.department = department;
        this.year = year;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    @Override
    public void displayMemberDetails() {
        System.out.println("Role: Student");
        super.displayMemberDetails();
        System.out.println("Department: " + department);
        System.out.println("Year: " + year);
    }
}
class Faculty extends LibraryMember {
    private String department;
    private String designation;
    public Faculty(int memberId, String memberName, String contactNumber, String department, String designation) {
        super(memberId, memberName, contactNumber);
        this.department = department;
        this.designation = designation;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    @Override
    public void displayMemberDetails() {
        System.out.println("Role: Faculty");
        super.displayMemberDetails();
        System.out.println("Department: " + department);
        System.out.println("Designation: " + designation);
    }
}
class IssueRecord {
    private int issueId;
    private int bookId;
    private int memberId;
    private String bookName;
    private String status;
    public IssueRecord(int issueId, int bookId, int memberId, String bookName, String status) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.bookName = bookName;
        this.status = status;
    }
    public int getIssueId() {
        return issueId;
    }
    public int getBookId() {
        return bookId;
    }
    public int getMemberId() {
        return memberId;
    }
    public String getBookName() {
        return bookName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
abstract class LibraryOperations {
    abstract void addBook(Book book);
    abstract void displayBooks();
    abstract void issueBook(int bookId, int memberId);
    abstract void returnBook(int bookId, int memberId);
}
class Library extends LibraryOperations {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<LibraryMember> members = new ArrayList<>();
    private HashMap<Integer, Book> bookMap = new HashMap<>();
    private LinkedHashMap<Integer, IssueRecord> issueRecords = new LinkedHashMap<>();
    private HashSet<Integer> issuedBookIds = new HashSet<>();
    private int nextMemberId = 1001;
    private int nextIssueId = 1;
    public boolean bookExists(int bookId) {
        return bookMap.containsKey(bookId);
    }
    @Override
    public void addBook(Book book) {
        if (bookMap.containsKey(book.getBookId())) {
            System.out.println("\nBook ID already exists! Duplicate Book IDs are not allowed.");
            return;
        }
        books.add(book);
        bookMap.put(book.getBookId(), book);
        System.out.println("\nBook Added Successfully!");
    }
    @Override
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("\nNo books available in the library.");
            return;
        }
        System.out.println("\n------------------ LIBRARY BOOKS ------------------\n");
        System.out.printf("%-8s %-22s %-18s %-15s %s%n", "ID", "Book Name", "Author", "Category", "Copies");
        for (Book b : books) {
            System.out.printf("%-8d %-22s %-18s %-15s %d%n", b.getBookId(), b.getBookName(), b.getAuthor(), b.getCategory(), b.getAvailableCopies());
        }
        System.out.println("\nTotal Books: " + books.size());
        System.out.println("Books with Zero Available Copies:");
        boolean zeroFound = false;
        for (Book b : books) {
            if (b.getAvailableCopies() == 0) {
                System.out.println("- " + b.getBookName() + " (ID: " + b.getBookId() + ")");
                zeroFound = true;
            }
        }
        if (!zeroFound) {
            System.out.println("None");
        }
    }
    public void searchBook(int bookId) {
        Book b = bookMap.get(bookId);
        if (b != null) {
            System.out.println("\nBook Found!");
            System.out.println("Book ID: " + b.getBookId());
            System.out.println("Book Name: " + b.getBookName());
            System.out.println("Author: " + b.getAuthor());
            System.out.println("Category: " + b.getCategory());
            System.out.println("Available Copies: " + b.getAvailableCopies());
        } else {
            System.out.println("\nBook with ID " + bookId + " does not exist!");
        }
    }
    public void registerStudent(String name, String contact, String department, int year) {
        int id = nextMemberId++;
        LibraryMember student = new Student(id, name, contact, department, year);
        members.add(student);
        System.out.println("\nStudent Registered Successfully! Assigned Member ID: " + id);
    }
    public void registerFaculty(String name, String contact, String department, String designation) {
        int id = nextMemberId++;
        LibraryMember faculty = new Faculty(id, name, contact, department, designation);
        members.add(faculty);
        System.out.println("\nFaculty Registered Successfully! Assigned Member ID: " + id);
    }
    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("\nNo registered library members.");
            return;
        }
        System.out.println("\n----------- REGISTERED MEMBERS -----------");
        for (LibraryMember member : members) {
            System.out.println();
            member.displayMemberDetails();
            System.out.println("------------------------------------------");
        }
        System.out.println("Total Members: " + members.size());
    }
    public LibraryMember findMemberById(int memberId) {
        for (LibraryMember m : members) {
            if (m.getMemberId() == memberId) {
                return m;
            }
        }
        return null;
    }
    public void searchMember(int memberId) {
        LibraryMember m = findMemberById(memberId);
        if (m != null) {
            System.out.println("\nMember Found!\n");
            m.displayMemberDetails();
        } else {
            System.out.println("\nMember with ID " + memberId + " not found!");
        }
    }
    @Override
    public void issueBook(int bookId, int memberId) {
        LibraryMember member = findMemberById(memberId);
        if (member == null) {
            System.out.println("\nInvalid Member ID! Member does not exist.");
            return;
        }
        Book book = bookMap.get(bookId);
        if (book == null) {
            System.out.println("\nInvalid Book ID! Book does not exist.");
            return;
        }
        if (book.getAvailableCopies() <= 0) {
            System.out.println("\nNo copies available for '" + book.getBookName() + "'!");
            return;
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        int issueId = nextIssueId++;
        IssueRecord record = new IssueRecord(issueId, bookId, memberId, book.getBookName(), "Issued");
        issueRecords.put(issueId, record);
        issuedBookIds.add(bookId);
        System.out.println("\nBook Issued Successfully!\n");
        System.out.println("Member: " + member.getMemberName());
        System.out.println("Book: " + book.getBookName());
        System.out.println("Available Copies: " + book.getAvailableCopies());
    }
    @Override
    public void returnBook(int bookId, int memberId) {
        Book book = bookMap.get(bookId);
        if (book == null) {
            System.out.println("\nInvalid Book ID!");
            return;
        }
        IssueRecord activeRecord = null;
        for (IssueRecord record : issueRecords.values()) {
            if (record.getMemberId() == memberId && record.getBookId() == bookId && record.getStatus().equals("Issued")) {
                activeRecord = record;
                break;
            }
        }
        if (activeRecord == null) {
            System.out.println("\nNo active issue record found for Member ID " + memberId + " and Book ID " + bookId + "!");
            return;
        }
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        activeRecord.setStatus("Returned");
        boolean stillIssued = false;
        for (IssueRecord record : issueRecords.values()) {
            if (record.getBookId() == bookId && record.getStatus().equals("Issued")) {
                stillIssued = true;
                break;
            }
        }
        if (!stillIssued) {
            issuedBookIds.remove(bookId);
        }
        System.out.println("\nBook Returned Successfully!");
        System.out.println("Book: " + book.getBookName());
        System.out.println("Available Copies: " + book.getAvailableCopies());
    }
    public void displayIssuedBooks(boolean activeOnly) {
        if (issueRecords.isEmpty()) {
            System.out.println("\nNo issue records available.");
            return;
        }
        System.out.println("\n--------------- ISSUE RECORDS ---------------\n");
        System.out.printf("%-10s %-12s %-10s %-22s %s%n", "Issue ID", "Member ID", "Book ID", "Book Name", "Status");
        int count = 0;
        for (Map.Entry<Integer, IssueRecord> entry : issueRecords.entrySet()) {
            IssueRecord r = entry.getValue();
            if (!activeOnly || r.getStatus().equals("Issued")) {
                System.out.printf("%-10d %-12d %-10d %-22s %s%n", r.getIssueId(), r.getMemberId(), r.getBookId(), r.getBookName(), r.getStatus());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No active issue records found.");
        }
    }
    public void generateLibraryReport() {
        int activeIssues = 0;
        for (IssueRecord r : issueRecords.values()) {
            if (r.getStatus().equals("Issued")) {
                activeIssues++;
            }
        }
        int totalAvailableCopies = 0;
        for (Book b : books) {
            totalAvailableCopies += b.getAvailableCopies();
        }
        System.out.println("\n============= LIBRARY REPORT =============\n");
        System.out.println("Total Books Registered: " + books.size());
        System.out.println("Total Registered Members: " + members.size());
        System.out.println("Total Active Issue Records: " + activeIssues);
        System.out.println("Unique Issued Book IDs: " + issuedBookIds);
        System.out.println("Total Available Book Copies: " + totalAvailableCopies);
        System.out.println("Currently Unavailable Books (0 Copies):");
        boolean unavailableFound = false;
        for (Book b : books) {
            if (b.getAvailableCopies() == 0) {
                System.out.println("- " + b.getBookName() + " (ID: " + b.getBookId() + ")");
                unavailableFound = true;
            }
        }
        if (!unavailableFound) {
            System.out.println("None");
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        while (true) {
            System.out.println("\n========================================");
            System.out.println("          LIBRARY MANAGEMENT SYSTEM");
            System.out.println("========================================\n");
            System.out.println("1. Add Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Register Student");
            System.out.println("5. Register Faculty");
            System.out.println("6. Display All Members");
            System.out.println("7. Search Member");
            System.out.println("8. Issue Book");
            System.out.println("9. Return Book");
            System.out.println("10. Display Issued Books");
            System.out.println("11. Generate Library Report");
            System.out.println("12. Exit\n");
            System.out.print("Enter Your Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("\nEnter Book ID: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();
                    if (library.bookExists(bookId)) {
                        System.out.println("\nBook ID already exists! Use a unique Book ID.");
                        break;
                    }
                    System.out.print("Enter Book Name: ");
                    String bookName = sc.nextLine();
                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();
                    System.out.print("Enter Category: ");
                    String category = sc.nextLine();
                    System.out.print("Enter Available Copies: ");
                    int copies = sc.nextInt();
                    sc.nextLine();
                    if (copies < 0) {
                        System.out.println("\nAvailable copies cannot be negative!");
                    } else {
                        Book book = new Book(bookId, bookName, author, category, copies);
                        library.addBook(book);
                    }
                    break;
                case 2:
                    library.displayBooks();
                    break;
                case 3:
                    System.out.print("\nEnter Book ID: ");
                    int searchBookId = sc.nextInt();
                    sc.nextLine();
                    library.searchBook(searchBookId);
                    break;
                case 4:
                    System.out.print("\nEnter Student Name: ");
                    String sName = sc.nextLine();
                    System.out.print("Enter Contact Number: ");
                    String sContact = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String sDept = sc.nextLine();
                    System.out.print("Enter Year: ");
                    int year = sc.nextInt();
                    sc.nextLine();
                    library.registerStudent(sName, sContact, sDept, year);
                    break;
                case 5:
                    System.out.print("\nEnter Faculty Name: ");
                    String fName = sc.nextLine();
                    System.out.print("Enter Contact Number: ");
                    String fContact = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String fDept = sc.nextLine();
                    System.out.print("Enter Designation: ");
                    String designation = sc.nextLine();
                    library.registerFaculty(fName, fContact, fDept, designation);
                    break;
                case 6:
                    library.displayAllMembers();
                    break;
                case 7:
                    System.out.print("\nEnter Member ID: ");
                    int searchMemberId = sc.nextInt();
                    sc.nextLine();
                    library.searchMember(searchMemberId);
                    break;
                case 8:
                    System.out.print("\nEnter Member ID: ");
                    int issueMemberId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int issueBookId = sc.nextInt();
                    sc.nextLine();
                    library.issueBook(issueBookId, issueMemberId);
                    break;
                case 9:
                    System.out.print("\nEnter Member ID: ");
                    int returnMemberId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int returnBookId = sc.nextInt();
                    sc.nextLine();
                    library.returnBook(returnBookId, returnMemberId);
                    break;
                case 10:
                    System.out.println("\n1. Display All Issue Records");
                    System.out.println("2. Display Only Active Issue Records");
                    System.out.print("Choose Option: ");
                    int viewOpt = sc.nextInt();
                    sc.nextLine();
                    library.displayIssuedBooks(viewOpt == 2);
                    break;
                case 11:
                    library.generateLibraryReport();
                    break;
                case 12:
                    System.out.println("\nExiting Library Management System...");
                    sc.close();
                    return;
                default:
                    System.out.println("\nInvalid Choice! Please enter a number between 1 and 12.");
            }
            System.out.println("\n----------------------------------------");
        }
    }
}