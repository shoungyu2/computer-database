<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="./css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="./css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="./css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="alert alert-danger">
                Error 400: Bad Request. Too bad bitch!
                <br/>
                <!-- stacktrace -->
            </div>
        </div>
        <div>
        	<form method="POST" id="goToDash" action="ListComputerController">
        		<button type="submit" class="btn btn-default">Return to dashboard</button>
        	</form>
        </div>
    </section>

    <script src="../js/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/dashboard.js"></script>

</body>
</html>