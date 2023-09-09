<h1><u>Library Management System</u></h1>
<hr>

<h3><u>Project Description:</u></h3>

The Library Management System is a Java-based console application which uses OOP principles that allows librarians and library members to manage books, members, borrow and return books, and handle fines.<br> The system provides two user roles: librarians and members. <br>A librarian can view, register or remove members. <br>They can also view books, add a book, remove a book, issue a book and collect dues/fines. <br>The fine is applied as 3 rupees per day post the return due date of a book. <br>A member can view all available books, borrow a book, return a book, view their own books, and pay dues.
<br />
<hr />

<h3>Usage:</h3>

<h4><u>Book Class:</u></h4>
The Book class represents a book in a library, storing information such as the book ID, title, author, and available copies.<br>
It provides methods to decrease and increase the available copies based on borrowing and returning operations.<br/>
It helps to keep a track of the books.
<hr>

<h4><u>Librarian Class:</u></h4>
The Library class represents a library and manages its books and members. 
<br>It uses maps to store books and members, with unique IDs for each.<br>
(Use of integers as input for the menu before selecting the appropriate option. Use of strings for entering member name and book details.)<br><br>
Register and remove members.<br>
Add and remove books.<br>
View member details, including borrowed books and fines.<br>
View book details.<br>
Exit (to return back to the main menu).

<hr>
<h4><u>Member Class:</u></h4>
The Member class represents a library member and stores information such as name, age, phone number, member ID, borrowed books, login status, fine amount, due time, and number of books borrowed and necessary methods for the code.<br><br>
(Use of integers as input for the menu before selecting the appropriate option. Use of strings for entering member name and book details.)<br><br>
Log in using name and phone number.<br>
List available books.<br>
List borrowed books.<br>
Borrow books (up to 2 at a time).<br>
Return books (with fines for late returns).<br>
Pay fines.<br>
Exit (to return to the main menu).
<hr>

