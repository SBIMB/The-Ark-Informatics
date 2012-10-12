package au.org.theark.core.model.report.entity;

public class QueryFilterGrouping {
	private Long id;
	private QueryGrouping 	parentGrouping;
	private QueryFilter 	leftFilter;
	private JoinType 		joinToNextFilter;
	private Long			precedence; //or order...but didn't want to confuse with order by

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public QueryGrouping getParentGrouping() {
		return parentGrouping;
	}
	public void setParentGrouping(QueryGrouping parentGrouping) {
		this.parentGrouping = parentGrouping;
	}
	public QueryFilter getLeftFilter() {
		return leftFilter;
	}
	public void setLeftFilter(QueryFilter leftFilter) {
		this.leftFilter = leftFilter;
	}
	public JoinType getJoinToNextFilter() {
		return joinToNextFilter;
	}
	public void setRelationshipToNextFilter(JoinType joinToNextFilter) {
		this.joinToNextFilter = joinToNextFilter;
	}
	public Long getPrecedence() {
		return precedence;
	}
	public void setPrecedence(Long precedence) {
		this.precedence = precedence;
	}

}