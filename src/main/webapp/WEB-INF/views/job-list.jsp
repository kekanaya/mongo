<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
    <form action="/filter">
        <div class="row">
            <div class="col-25">
                &emsp;<b>Job Name:</b>
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                <b>Success?</b>
                &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                <b>Run Date:</b>
            </div>
            <div class="col-75">
                &emsp;<input type="text" class="input-sm" name="jobName" value="${jobName}">
                &emsp;&emsp;&emsp;&emsp;&nbsp;
                <input type="text" class="input-sm" name="success" value="${success}">
                &emsp;&emsp;&emsp;&emsp;&nbsp;
                <input type="text" class="input-sm" name="runDate" value="${runDate}">
            </div>
        </div>
        <br/>
        <div class="row">
            <div class="col-75"><a>  </a></div>
            <div class="col-25">
                <div class="col-75"><a>  </a></div>
                &emsp;<input type="submit" class="btn btn-success" value="Filter Results">
            </div>
        </div>
    </form>
    <br/>
    <br/>
    <br/>
    <div>
        <a type="button" class="btn btn-primary" href="#"
           target="newJobWindow"
           onclick="openNewJobWindow(); return false;" >Run new Job</a>
        <a type="button" class="btn btn-danger" href="/jobs">Clear Filters</a>
    </div>
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
</div>
<%@ include file="common/footer.jspf" %>