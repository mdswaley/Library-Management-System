import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Issue.css";

const IssueBooks = () => {
  const [books, setBooks] = useState([]);
  const [selectedBook, setSelectedBook] = useState("");
  const [userId, setUserId] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [transactionData, setTransactionData] = useState(null);

  // Fetch all books
  useEffect(() => {
    axios
      .get("http://localhost:8080/Books")
      .then((response) => setBooks(response.data))
      .catch(() => setError("Failed to fetch books."));
  }, []);

  // Handle Book Issue
  const handleIssueBook = () => {
    if (!selectedBook || !userId) {
      setError("Please select a book and enter a user ID.");
      return;
    }

    axios
      .post(`http://localhost:8080/transaction/issue/${selectedBook}/${userId}`)
      .then((response) => {
        setSuccess(`Book issued successfully! Title: ${response.data.book.title}`);
        setError("");
        setTransactionData(response.data);
      })
      .catch(() => {
        setError("Failed to issue book.");
        setSuccess("");
        setTransactionData(null);
      });
  };

  // Handle Book Return
  const handleReturnBook = () => {
    if (!selectedBook || !userId) {
      setError("Please select a book and enter a user ID.");
      return;
    }

    axios
      .post(`http://localhost:8080/transaction/return/${selectedBook}/${userId}`)
      .then((response) => {
        setSuccess(`Book returned successfully! Title: ${response.data.book.title}`);
        setError("");
        setTransactionData(response.data);
      })
      .catch(() => {
        setError("Failed to return book.");
        setSuccess("");
        setTransactionData(null);
      });
  };

  return (
    <div className="issue-books">
      <h1>Issue or Return a Book</h1>
      {error && <p className="error">{error}</p>}
      {success && <p className="success">{success}</p>}
      <div className="form-container">
        <div className="form-group">
          <label htmlFor="book">Select a Book:</label>
          <select
            id="book"
            value={selectedBook}
            onChange={(e) => setSelectedBook(e.target.value)}
          >
            <option value="">--Select a Book--</option>
            {books.map((book) => (
              <option key={book.id} value={book.id}>
                {book.title} - {book.author}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="userId">Enter User ID:</label>
          <input
            type="text"
            id="userId"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            placeholder="Enter User ID"
          />
        </div>
        <div className="button-group">
          <button onClick={handleIssueBook}>Issue Book</button>
          <button onClick={handleReturnBook}>Return Book</button>
        </div>
      </div>
      
      <div className="transaction-details">
        <h2>Transaction Details</h2>
        {transactionData ? (
          <div>
            <p><strong>Transaction Type:</strong> {transactionData.transactionType}</p>
            <p><strong>Book Title:</strong> {transactionData.book.title}</p>
            <p><strong>Book Author:</strong> {transactionData.book.author}</p>
            <p><strong>User ID:</strong> {transactionData.user.id}</p>
            <p><strong>Issue Date:</strong> {new Date(transactionData.issueDate).toLocaleString()}</p>
            <p><strong>Return Date:</strong> {new Date(transactionData.returnDate).toLocaleString()}</p>
            <p><strong>Fine :</strong> {transactionData.fine}</p>
          </div>
        ) : (
          <p>No transaction data available.</p>
        )}
      </div>
    </div>
  );
};

export default IssueBooks;
