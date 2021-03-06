package org.openmrs.module.jsslab.rest.v1_0.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabTestingService;
import org.openmrs.module.jsslab.db.LabTestSpecimen;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource("labtestspecimen")
@Handler(supports = LabTestSpecimen.class, order = 0)
public class LabTestSpecimenResource extends MetadataDelegatingCrudResource<LabTestSpecimen> {
	
	@Override
	public LabTestSpecimen getByUniqueId(String uniqueId) {
		LabTestSpecimen labTestSpecimen = Context.getService(LabTestingService.class).getLabTestSpecimenByUuid(uniqueId);
		return labTestSpecimen;
	}
	
	@Override
	public LabTestSpecimen newDelegate() {
		return new LabTestSpecimen();
	}
	
	@Override
	public LabTestSpecimen save(LabTestSpecimen delegate) {
		LabTestSpecimen labTestSpecimen = Context.getService(LabTestingService.class).saveLabTestSpecimen(delegate);
		return labTestSpecimen;
	}
	
	@Override
	public void delete(LabTestSpecimen labTestSpecimen, String reason, RequestContext context) throws ResponseException {
		if (labTestSpecimen != null) {
			//
			Context.getService(LabTestingService.class).retireLabTestSpecimen(labTestSpecimen, reason);
		}
		
	}
	
	@Override
	public void purge(LabTestSpecimen labTestSpecimen, RequestContext context) throws ResponseException {
		if (labTestSpecimen != null) {
			//
			Context.getService(LabTestingService.class).purgeLabTestSpecimen(labTestSpecimen);
		}
		
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription Descri = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation) {
			//			
			Descri.addProperty("uuid");
			Descri.addProperty("specimen", Representation.REF);
			Descri.addProperty("specimenSubId");
			Descri.addProperty("retired");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return Descri;
		} else if (rep instanceof FullRepresentation) {
			//
			Descri.addProperty("uuid");
			Descri.addProperty("specimen", Representation.REF);
			Descri.addProperty("parentSubId");
			Descri.addProperty("specimenSubId");
			Descri.addProperty("parentRelationConcept", Representation.REF);
			Descri.addProperty("analysisSpecimenTypeConcept", Representation.REF);
			Descri.addProperty("testPanel", Representation.REF);
			Descri.addProperty("testRoleConcept", Representation.REF);
			Descri.addProperty("supplyItem", Representation.REF);
			Descri.addProperty("prepTech", Representation.REF);
			Descri.addProperty("prepTime");
			Descri.addProperty("rackPosition");
			Descri.addProperty("testTech", Representation.REF);
			Descri.addProperty("testTime");
			//			Descri.addProperty("testRun",Representation.REF);
			Descri.addProperty("wellPosition", Representation.REF);
			Descri.addProperty("retired");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo", findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription d = new DelegatingResourceDescription();
		d.addRequiredProperty("specimen");
		d.addRequiredProperty("specimenSubId");
		d.addRequiredProperty("parentSubId");
		d.addRequiredProperty("parentRelationConcept");
		d.addProperty("analysisSpecimenTypeConcept");
		d.addProperty("testPanel");
		d.addProperty("testRoleConcept");
		d.addProperty("supplyItem");
		d.addProperty("prepTech");
		d.addProperty("prepTime");
		d.addProperty("rackPosition");
		d.addProperty("testTech");
		d.addProperty("testTime");
		d.addProperty("testRun");
		d.addProperty("wellPosition");
		d.addProperty("testResults");
		return d;
	}
	
	@Override
	protected String getNamespacePrefix() {
		return "jsslab";
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return new NeedsPaging<LabTestSpecimen>(Context.getService(LabTestingService.class).getLabTestSpecimens("", context.getIncludeAll(),
		    null, null), context);
	}
	
	@Override
	protected AlreadyPaged<LabTestSpecimen> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTestSpecimen>(LabTestingService.class, "getLabTestSpecimens", "getCountOfLabTestSpecimen")
		        .search(query, context);
	}
	
}
