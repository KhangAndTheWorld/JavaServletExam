<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Player Information</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        table { width: 80%; margin: 20px auto; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; }
        th { background-color: #f2f2f2; }
        .form-container { margin: 20px; }
        .form-container input, .form-container select { margin: 5px; padding: 5px; }
        .form-container button { padding: 5px 10px; background-color: #d9534f; color: white; border: none; }
    </style>
</head>
<body>
<h2>PLAYER INFORMATION</h2>
<div class="form-container">
    <form action="PlayerServlet" method="post">
        <input type="hidden" name="action" value="add">
        <label>PLAYER NAME</label>
        <input type="text" name="playerName" required>
        <label>PLAYER FULL NAME</label> <!-- Thêm trường này -->
        <input type="text" name="fullName" required>
        <label>PLAYER AGE</label>
        <input type="number" name="playerAge" required>
        <br>
        <label>INDEX NAME</label>
        <select name="indexName" required>
            <option value="speed">speed</option>
            <option value="strength">strength</option>
            <option value="accurate">accurate</option>
        </select>
        <label>VALUE</label>
        <input type="number" step="0.1" name="value" required>
        <br>
        <button type="submit">Add</button>
    </form>
</div>

<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Player name</th>
        <th>Player age</th>
        <th>Index name</th>
        <th>Value</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="playerIndex" items="${playerIndexList}">
        <tr>
            <td>${playerIndex.id}</td>
            <td>${playerIndex.playerName} ${playerIndex.fullName}</td>
            <td>${playerIndex.playerAge}</td>
            <td>${playerIndex.indexName}</td>
            <td>${playerIndex.value}</td>
            <td>
                <a href="PlayerServlet?action=edit&id=${playerIndex.id}">✏️</a>
                <a href="PlayerServlet?action=delete&id=${playerIndex.id}">🗑️</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>