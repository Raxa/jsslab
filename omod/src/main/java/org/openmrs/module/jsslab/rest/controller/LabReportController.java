package org.openmrs.module.jsslab.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.rest.resource.LabReportResource;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value= "/rest/"+RestConstants.VERSION_1+"/jsslab/labReport")
public class LabReportController extends BaseCrudController<LabReportResource>{

	private static final Log log=LogFactory.getLog(LabReportController.class);
	
	@Override
	public LabReportResource getResource()
	{
		//
		log.info("getting LabReport resource");
		return Context.getService(RestService.class).getResource(LabReportResource.class);
	}
}