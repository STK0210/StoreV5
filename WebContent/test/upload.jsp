<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	文件操作：
	<table frame="border" bordercolor="black" style="height: 57px; width: 600px">
		<tr valign="middle">
			<td style="border: 1px solid black;">
				<a href="/ServletTrain/ReadFile" target="workspace">读文件</a>
			</td>
			<td style="border: 1px solid black;">
				<a href="/ServletTrain/WriteFile" target="workspace">写文件</a>
			</td>
			<td style="border: 1px solid black;">
				<form action="/StoreV5/UpLoad" method="post" enctype="multipart/form-data" target="workspace">
					<input type="file" name="file">
					<input type="submit" name="upload" value="文件上传">
				</form>
			</td>
			<td style="border: 1px solid black;">
				<a href="/StoreV5/DownLoad" target="workspace">下载文件</a>
			</td>
		</tr>
	</table>
</body>
</html>