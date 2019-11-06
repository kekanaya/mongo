<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
    <table class="table table-striped">
        <caption>Job Summaries</caption>
        <thead>
        <tr>
            <th>User</th>
            <th>Job Name</th>
            <th>Run Date</th>
            <th>Success?</th>
            <th>Description</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${jobs}" var="job">
            <tr>
                <td>${job.name}</td>
                <td>${job.jobName}</td>
                <td><fmt:formatDate value="${job.runDate}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                <td>${job.success}</td>
                <td>${job.description}</td>
                <td><a type="button" class="btn btn-success"
                       href="/rerun?id=${job.id}">Re-run</a></td>
                <td><a type="button" class="btn btn-warning"
                       href="/delete?id=${job.id}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <script type="text/javascript">
        var windowRef = null; // global variable

        function openNewJobWindow() {
            if(windowRef == null || windowRef.closed)
            {
                windowRef = window.open("/newJob",
                    "newJobWindow", "width=820,height=530,resizable,scrollbars,status");
            }
            else
            {
                windowRef.focus();
            };
        }
    </script>


    <div>
        <a type="button" class="btn btn-primary" href="#"
           target="newJobWindow"
           onclick="openNewJobWindow(); return false;" >Run new Job</a>
    </div>
</div>
<%@ include file="common/footer.jspf" %>