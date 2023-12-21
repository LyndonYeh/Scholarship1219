<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Password Information</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .password-container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 20px;
            text-align: center;
        }

        .password {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="password-container">
    <div class="password">
        <strong>帳號:</strong> dave.wenyu@gmail.com <br>
        <strong>明碼:</strong> password1 <br>
        <strong>暗碼:</strong> <span style="color: #4CAF50;">$2a$05$FDHt7rx0kB74Vs0mon5GWugT4cGOFvDSYra/qdZr8y5DS2fCAs0ta</span>
    </div>

    <div class="password">
        <strong>帳號:</strong> lyndonyeh@gmail.com <br>
        <strong>明碼:</strong> password2 <br>
        <strong>暗碼:</strong> <span style="color: #4CAF50;">$2a$05$0ppuj4QdEyWAnlNf7IbWFObR9.NH1rSIgTaTJ1WyNvAyd9iWtR7uW</span>
    </div>

    <div class="password">
        <strong>帳號:</strong> alicelu@gmail.com <br>
        <strong>明碼:</strong> password3 <br>
        <strong>暗碼:</strong> <span style="color: #4CAF50;">$2a$05$wbUIDjEtLQ5J8mzwIiDE1Op5nz4N6XNclJxFEtehQg2Bodhj22G2K</span>
    </div>
</div>

</body>
</html>
