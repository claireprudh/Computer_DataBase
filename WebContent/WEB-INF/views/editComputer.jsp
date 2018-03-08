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
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id: ${ computer.id }
					</div>
					<h1>Edit Computer</h1>

					<form id="editForm" action="editComputer" method="POST">
						<input type="hidden" value="${ computer.id }" name="id" id="id" />
						<fieldset>
							<div class="form-group">
								<label for="name">Computer name</label> <input type="text"
									class="form-control" name="name" id="name"
									placeholder="${ computer.name }" value="${ computer.name }">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									data-validation="date" data-validation-format="yyyy-mm-dd"
									type="date" class="form-control" name="introduced"
									id="introduced" placeholder="${ computer.introduced }"
									value="${ computer.introduced }">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									data-validation="date" data-validation-format="yyyy-mm-dd"
									type="date" class="form-control" name="discontinued"
									id="discontinued" placeholder="${ computer.discontinued }"
									value="${ computer.discontinued }">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" name="companyId" id="companyId">
									<c:forEach items="${ listCompanies }" var="company">
										<c:choose>
											<c:when test="${ company.id == computer.companyId }">
												<option value="${ company.id }" selected="selected">-${ company.name }-</option>
											</c:when>
											<c:otherwise>
												<option value="${ company.id }">-${ company.name }-</option>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
					<script>
						$("#editForm").validate();
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
			lang : 'fr'
			modules : 'date'
		});
	</script>
</body>
</html>