<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Player</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        .form-container { margin: 20px; }
        .form-container input, .form-container select { margin: 5px; padding: 5px; }
        .form-container button { padding: 5px 10px; background-color: #d9534f; color: white; border: none; }
    </style>
</head>
<body>
<h2>EDIT PLAYER INFORMATION</h2>
<div class="form-container">
    <form action="PlayerServlet" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${playerIndex.id}">
        <label>PLAYER NAME</label>
        <input type="text" name="playerName" value="${playerIndex.playerName}" required>
        <label>PLAYER AGE</label>
        <input type="number" name="playerAge" value="${playerIndex.playerAge}" required>
        <br>
        <label>INDEX NAME</label>
        <select name="indexName" required>
            <option value="speed" ${playerIndex.indexName == 'speed' ? 'selected' : ''}>speed</option>
            <option value="strength" ${playerIndex.indexName == 'strength' ? 'selected' : ''}>strength</option>
            <option value="accurate" ${playerIndex.indexName == 'accurate' ? 'selected' : ''}>accurate</option>
        </select>
        <label>VALUE</label>
        <input type="number" step="0.1" name="value" value="${playerIndex.value}" required>
        <br>
        <button type="submit">Update</button>
    </form>
    <a href="PlayerServlet">Back to List</a>
</div>
</body>
</html>