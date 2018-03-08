<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard.html"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form id="addForm" action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="name">Computer name</label> <input type="text"
									data-validation="alphanumeric" data-validation-allowing="-_"
									class="form-control" name="name" id="name"
									placeholder="Computer name" maxlength="35" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date (YYYY-MM-DD)</label> <input
									data-validation="date" data-validation-format="yyyy-mm-dd"
									type="date" class="form-control" name="introduced"
									id="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date (YYYY-MM-DD)</label>
								<input data-validation="date"
									data-validation-format="yyyy-mm-dd" type="date"
									class="form-control" name="discontinued" id="discontinued"
									placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" name="companyId" id="companyId">
									<c:forEach items="${ listCompanies }" var="company">

										<option value="${ company.id }" selected="${ company.id }">-${ company.name }-</option>

									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
					<script>
						$("#addForm").validate();
					</script>
				</div>
			</div>
		</div>
	</section>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
	<script>
		$.validate({
			lang : 'fr',
			modules : 'date , security'
		});
	</script>
</body>
</html>