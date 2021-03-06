package com.nordicpeak.flowengine.queries.multigeometrymapquery;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import se.unlogic.standardutils.annotations.WebPopulate;
import se.unlogic.standardutils.dao.annotations.DAOManaged;
import se.unlogic.standardutils.dao.annotations.OneToMany;
import se.unlogic.standardutils.dao.annotations.Table;
import se.unlogic.standardutils.populators.IntegerPopulator;
import se.unlogic.standardutils.populators.PositiveStringIntegerPopulator;
import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationException;
import se.unlogic.standardutils.xml.XMLElement;
import se.unlogic.standardutils.xml.XMLParser;
import se.unlogic.standardutils.xml.XMLValidationUtils;

import com.nordicpeak.flowengine.queries.basemapquery.BaseMapQuery;

@Table(name = "multi_geometry_map_queries")
@XMLElement
public class MultiGeometryMapQuery extends BaseMapQuery {

	private static final long serialVersionUID = 4441486010619994034L;

	@DAOManaged
	@WebPopulate(maxLength = 255, populator=IntegerPopulator.class)
	@XMLElement
	private Integer minimumScale;

	@DAOManaged
	@OneToMany
	@XMLElement
	private List<MultiGeometryMapQueryInstance> instances;

	public List<MultiGeometryMapQueryInstance> getInstances() {
		return instances;
	}

	public void setInstances(List<MultiGeometryMapQueryInstance> instances) {
		this.instances = instances;
	}

	public Integer getMinimumScale() {
		return minimumScale;
	}

	public void setMinimumScale(Integer minimumScale) {
		this.minimumScale = minimumScale;
	}

	@Override
	public String getXSDTypeName() {

		return "MultiGeometryMapQuery" + getQueryID();
	}

	@Override
	public void toXSD(Document doc) {

		Element complexTypeElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:complexType");
		complexTypeElement.setAttribute("name", getXSDTypeName());

		Element complexContentElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:complexContent");
		complexTypeElement.appendChild(complexContentElement);

		Element extensionElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:extension");
		extensionElement.setAttribute("base", "Query");
		complexContentElement.appendChild(extensionElement);

		Element sequenceElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:sequence");
		extensionElement.appendChild(sequenceElement);

		Element nameElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:element");
		nameElement.setAttribute("name", "Name");
		nameElement.setAttribute("type", "xs:string");
		nameElement.setAttribute("minOccurs", "1");
		nameElement.setAttribute("maxOccurs", "1");
		nameElement.setAttribute("fixed", queryDescriptor.getName());
		sequenceElement.appendChild(nameElement);

		appendBaseFieldDefenitions(doc, sequenceElement);

		Element fieldElement = doc.createElementNS("http://www.w3.org/2001/XMLSchema","xs:element");
		fieldElement.setAttribute("name", "Geometry");
		fieldElement.setAttribute("type", "xs:string");
		fieldElement.setAttribute("minOccurs", "0");
		fieldElement.setAttribute("maxOccurs", "unbounded");

		sequenceElement.appendChild(fieldElement);

		doc.getDocumentElement().appendChild(complexTypeElement);
	}

	@Override
	public void populate(XMLParser xmlParser) throws ValidationException {

		super.populate(xmlParser);

		List<ValidationError> errors = new ArrayList<ValidationError>();

		minimumScale = XMLValidationUtils.validateParameter("minimumScale", xmlParser, false, PositiveStringIntegerPopulator.getPopulator(), errors);

		if(!errors.isEmpty()){

			throw new ValidationException(errors);
		}

	}

}
