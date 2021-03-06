/*
 * #%L
 * ACS AEM Commons Bundle
 * %%
 * Copyright (C) 2016 Adobe
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.adobe.acs.commons.exporters.impl.users;

import static com.adobe.acs.commons.exporters.impl.users.Constants.CUSTOM_PROPERTIES;
import static com.adobe.acs.commons.exporters.impl.users.Constants.GROUPS;
import static com.adobe.acs.commons.exporters.impl.users.Constants.GROUP_FILTER;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_EXTENSIONS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_SELECTORS;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;


@Component(service=Servlet.class,
property= {
SLING_SERVLET_METHODS+"=POST",
SLING_SERVLET_SELECTORS+"=save",
SLING_SERVLET_EXTENSIONS+"=json",
SLING_SERVLET_RESOURCE_TYPES+"=acs-commons/components/utilities/exporters/users-to-csv"
})
public class UsersSaveServlet extends SlingAllMethodsServlet {

    /**
     * Persists the Users to CSV form data to the underlying jcr:content node.
     * @param request the Sling HTTP Request object
     * @param response the Sling HTTP Response object
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        final ValueMap properties = request.getResource().adaptTo(ModifiableValueMap.class);

        final Parameters parameters = new Parameters(request);
        properties.put(GROUP_FILTER, parameters.getGroupFilter());
        properties.put(GROUPS, parameters.getGroups());
        properties.put(CUSTOM_PROPERTIES, parameters.getCustomProperties());
        request.getResourceResolver().commit();

    }
}