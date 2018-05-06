package com.zeral.web.rest.vm;

import com.zeral.domain.LineBody;
import com.zeral.domain.LineProcess;

import java.util.List;

public class LineBodyAndProcessVM {
    private LineBody lineBody;
    private List<LineProcess> lineProcesses;

    public LineBody getLineBody() {
        return lineBody;
    }

    public void setLineBody(LineBody lineBody) {
        this.lineBody = lineBody;
    }

    public List<LineProcess> getLineProcesses() {
        return lineProcesses;
    }

    public void setLineProcesses(List<LineProcess> lineProcesses) {
        this.lineProcesses = lineProcesses;
    }
}
