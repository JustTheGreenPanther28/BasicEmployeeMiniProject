package com.employee.response;

public class ReportEmployee {

	private String reporter;
	private String reportTo;
	private String reportToId;

	public ReportEmployee() {

	}

	public ReportEmployee(String reporter, String reportTo, String reportToId) {
		super();
		this.reporter = reporter;
		this.reportTo = reportTo;
		this.reportToId = reportToId;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getReportTo() {
		return reportTo;
	}

	public void setReportTo(String reportTo) {
		this.reportTo = reportTo;
	}

	public String getReportToId() {
		return reportToId;
	}

	public void setReportToId(String reportToId) {
		this.reportToId = reportToId;
	}

}
