package com.nordicpeak.flowengine.beans;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import se.unlogic.standardutils.annotations.WebPopulate;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.Key;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.OrderBy;
import se.unlogic.standardutils.dao.annotations.SimplifiedRelation;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.reflection.ReflectionUtils;
import se.unlogic.standardutils.xml.GeneratedElementable;
import se.unlogic.standardutils.xml.XMLElement;

import com.nordicpeak.flowengine.interfaces.ImmutableFlowType;

@Table(name = "flowengine_flow_types")
@XMLElement
public class FlowType extends GeneratedElementable implements Serializable, ImmutableFlowType{

	private static final long serialVersionUID = 7195827633619267170L;

	public static final Field CATEGORIES_RELATION = ReflectionUtils.getField(FlowType.class,"categories");
	public static final Field FLOWS_RELATION = ReflectionUtils.getField(FlowType.class,"flows");
	public static final Field ALLOWED_GROUPS_RELATION = ReflectionUtils.getField(FlowType.class,"allowedGroupIDs");
	public static final Field ALLOWED_USERS_RELATION = ReflectionUtils.getField(FlowType.class,"allowedUserIDs");
	public static final Field ALLOWED_QUERIES_RELATION = ReflectionUtils.getField(FlowType.class,"allowedQueryTypes");

	@DAOManaged(autoGenerated = true)
	@Key
	@XMLElement
	private Integer flowTypeID;

	@DAOManaged
	@OrderBy
	@WebPopulate(required = true,maxLength=255)
	@XMLElement
	private String name;

	@DAOManaged
	@OneToMany
	@XMLElement(fixCase=true)
	private List<Flow> flows;

	@DAOManaged
	@OneToMany
	@XMLElement(fixCase=true)
	private List<Category> categories;

	@DAOManaged
	@OneToMany
	@SimplifiedRelation(table = "flowengine_flow_type_admin_groups", remoteValueColumnName = "groupID")
	@WebPopulate(paramName = "group")
	@XMLElement(childName = "groupID")
	private List<Integer> allowedGroupIDs;

	@DAOManaged
	@OneToMany
	@SimplifiedRelation(table = "flowengine_flow_type_admin_users", remoteValueColumnName = "userID")
	@WebPopulate(paramName="user")
	@XMLElement(childName = "userID")
	private List<Integer> allowedUserIDs;

	@DAOManaged
	@OneToMany
	@SimplifiedRelation(table = "flowengine_flow_type_allowed_queries", remoteValueColumnName = "queryTypeID")
	@WebPopulate(paramName="queryType")
	@XMLElement(childName = "queryTypeID")
	private List<String> allowedQueryTypes;

	@Override
	public Integer getFlowTypeID() {

		return flowTypeID;
	}

	public void setFlowTypeID(Integer flowTypeID) {

		this.flowTypeID = flowTypeID;
	}

	@Override
	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	public List<Category> getCategories() {

		return categories;
	}

	public void setCategories(List<Category> flowCategories) {

		this.categories = flowCategories;
	}

	@Override
	public boolean allowsAdminAccess() {

		return false;
	}

	@Override
	public boolean allowsUserAccess() {

		return false;
	}

	@Override
	public boolean allowsAnonymousAccess() {

		return false;
	}

	@Override
	public List<Integer> getAllowedGroupIDs() {

		return this.allowedGroupIDs;
	}

	@Override
	public List<Integer> getAllowedUserIDs() {

		return this.allowedUserIDs;
	}

	@Override
	public List<Flow> getFlows() {

		return flows;
	}

	public void setFlows(List<Flow> flows) {

		this.flows = flows;
	}

	@Override
	public List<String> getAllowedQueryTypes() {

		return allowedQueryTypes;
	}

	public void setAllowedQueryTypes(List<String> allowedQueryTypes) {

		this.allowedQueryTypes = allowedQueryTypes;
	}

	@Override
	public String toString() {

		return name + " (ID: " + flowTypeID + ")";
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((flowTypeID == null) ? 0 : flowTypeID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		FlowType other = (FlowType) obj;
		if (flowTypeID == null) {
			if (other.flowTypeID != null) {
				return false;
			}
		} else if (!flowTypeID.equals(other.flowTypeID)) {
			return false;
		}
		return true;
	}
}