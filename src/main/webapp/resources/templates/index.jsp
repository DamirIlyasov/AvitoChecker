<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>
        index.jsp
    </title>
    <script type="text/javascript">
        function checkRows() {
            var request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (request.readyState === 4) {
                    var responseText = request.responseText;
                    var beforeRows = "";
                    var afterRows = "";
                    var isBeforeVariableFull = false;
                    var c;
                    for (c = 0; c < responseText.length; c++) {
                        if (!isBeforeVariableFull) {
                            if (responseText.charAt(c) == ',') {
                                isBeforeVariableFull = true;
                            } else {
                                beforeRows += responseText.charAt(c);
                            }
                        } else {
                            afterRows += responseText.charAt(c);
                        }
                    }
                    if (beforeRows != afterRows) {
                        alert("Reload page, there are some changes");
                    }
                }
            };
            request.open('GET', 'rows', true);
            request.send()
        }
        setInterval(checkRows, 10000)
    </script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load('current', {'packages': ['table']});
        google.charts.setOnLoadCallback(drawTable);

        function drawTable() {
            var data = new google.visualization.DataTable();
            data.addColumn('number', 'Price');
            data.addColumn('number', 'Year of manufacturing');
            data.addColumn('string', 'Description');
            data.addRows([
                <c:forEach items="${list}" var="item">
                [{v:${item.getPrice()}, f: '${item.getPrice()} RUB'}, {
                    v:${item.getYear()},
                    f: '${item.getYear()}'
                }, '${item.getDescription()}'],
                </c:forEach>
            ]);
            var table = new google.visualization.Table(document.getElementById('table_div'));
            table.draw(data, {showRowNumber: true, width: '60%', height: '70%'});
        }
    </script>
</head>
<body>
<p style="text-align: center">Subaru Baja advertisements</p>
<table border="1" width="100%" cellpadding="5">
    <tr>
        <th>Price</th>
        <th>Year of car manufacture</th>
        <th>City</th>
        <th>Description</th>
        <th>Created At</th>
    </tr>
    <c:forEach items="${list}" var="item">
        <tr>
            <th>${item.getPrice()}</th>
            <th>${item.getYear()}</th>
            <th>${item.getCity()}</th>
            <th>${item.getDescription()}</th>
            <th>${item.getCreatedAtFormatted()}</th>
        </tr>
    </c:forEach>
</table>
<div id="table_div" style="text-align: center"></div>
</body>
</html>