<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>


<html>
<head>
<meta charset="UTF-8">
<title>Livros de java, Android, Iphone, PHP, Ruby e muito mais -
	Casa do código</title>
</head>
<body>
	<form:form action="${s:mvcUrl('PC#grava').build()}" method="post"
		commandName="produto">
		<div>
			<label>Título</label> 
			<form:input path="titulo" />
			<form:errors path="titulo" />
		</div>
		<div>
			<label>Descrição</label>
			<form:textarea path="descricao" rows="10" cols="20" />
			</textarea>
			<form:errors path="descricao" />
		</div>

		<div>
			<label>Páginas</label> 
			<form:input path="paginas"/>
			<form:errors path="paginas" />
		</div>

		<div>
			<label>Data Lançamento</label>
			 <form:input path="dataLancamento"/>
			<form:errors path="dataLancamento" />
		</div>
		<c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
			<div>
				<label>${tipoPreco}</label> 
					<form:input path="precos[${status.index}].valor"/> 
					<form:hidden path="precos[${status.index}].tipo" value="${tipoPreco}"/>
			</div>
		</c:forEach>
		<button type="submit">Cadastrar</button>
	</form:form>
</body>
</html>