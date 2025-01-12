import React, { useState, useEffect } from "react";
import axios from "axios";
import "./Books.css";

const ManageBooks = () => {
  const [books, setBooks] = useState([]);
  const [newBook, setNewBook] = useState({
    title: "",
    author: "",
    isbn: "",
    quantity: "",
  });
  const [error, setError] = useState("");

  // Fetch all books
  useEffect(() => {
    axios
      .get("http://localhost:8080/Books")
      .then((response) => setBooks(response.data))
      .catch(() => setError("Failed to fetch books."));
  }, []);

  // Handle input change
  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewBook((prev) => ({ ...prev, [name]: value }));
  };

  // Add a new book
  const addBook = (e) => {
    e.preventDefault();
    axios
      .post("http://localhost:8080/Books/addBook", newBook)
      .then((response) => {
        setBooks((prevBooks) => [...prevBooks, response.data]);
        setNewBook({ title: "", author: "", isbn: "", quantity: "" });
      })
      .catch(() => setError("Failed to add book."));
  };

  const deleteBook = (id) => {
    axios
      .delete(`http://localhost:8080/Books/${id}`)
      .then(() => {
        setBooks((prevBooks) => prevBooks.filter((book) => book.id !== id));
      })
      .catch(() => setError("Failed to delete book."));
  };

  return (
    <div className="manage-books">
      <h1>Manage Books</h1>

      <div className="book-form">
        <h2>Add a New Book</h2>
        <form onSubmit={addBook}>
          <input
            type="text"
            name="title"
            placeholder="Title"
            value={newBook.title}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="author"
            placeholder="Author"
            value={newBook.author}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="isbn"
            placeholder="ISBN"
            value={newBook.isbn}
            onChange={handleChange}
            required
          />
          <input
            type="number"
            name="quantity"
            placeholder="Quantity"
            value={newBook.quantity}
            onChange={handleChange}
            required
          />
          <button type="submit">Add Book</button>
        </form>
        {error && <p className="error">{error}</p>}
      </div>

      <div className="book-list">
        <h2>All Books</h2>
        <table>
          <thead>
            <tr>
              <th>Title</th>
              <th>Author</th>
              <th>ISBN</th>
              <th>Quantity</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {books.map((book) => (
              <tr key={book.id}>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.isbn}</td>
                <td>{book.quantity}</td>
                <td>
                  <button
                    className="delete-button"
                    onClick={() => deleteBook(book.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ManageBooks;
