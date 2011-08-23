/*******************************************************************************
 * Copyright (c) 2011  University of Western Australia. All rights reserved.
 * 
 * This file is part of The Ark.
 * 
 * The Ark is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * The Ark is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package au.org.theark.report.web.component.viewReport;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import au.org.theark.report.web.component.viewReport.studySummary.filterForm.StudySummaryFilterForm;

public class ReportContainerVO implements Serializable {

	/**
	 * 
	 */
	private static final long						serialVersionUID	= 1L;

	private FeedbackPanel							feedbackPanel;
	private ReportSelectPanel						reportSelectPanel;
	private WebMarkupContainer						selectedReportContainerWMC;
	private AbstractSelectedReportContainer	selectedReportPanel;

	public ReportContainerVO() {

		selectedReportContainerWMC = new WebMarkupContainer("selectedReportContainerWMC");
		selectedReportContainerWMC.setOutputMarkupPlaceholderTag(true);
		selectedReportContainerWMC.setVisible(false);

	}

	public FeedbackPanel getFeedbackPanel() {
		return feedbackPanel;
	}

	public void setFeedbackPanel(FeedbackPanel feedBackPanel) {
		this.feedbackPanel = feedBackPanel;
	}

	public ReportSelectPanel getReportSelectPanel() {
		return reportSelectPanel;
	}

	public void setReportSelectPanel(ReportSelectPanel reportSelectPanel) {
		this.reportSelectPanel = reportSelectPanel;
	}

	public WebMarkupContainer getSelectedReportContainerWMC() {
		return selectedReportContainerWMC;
	}

	public void setSelectedReportContainerWMC(WebMarkupContainer selectedReportContainerWMC) {
		this.selectedReportContainerWMC = selectedReportContainerWMC;
	}

	public AbstractSelectedReportContainer getSelectedReportPanel() {
		return selectedReportPanel;
	}

	public void setSelectedReportPanel(AbstractSelectedReportContainer selectedReportPanel) {
		this.selectedReportPanel = selectedReportPanel;
	}

}
