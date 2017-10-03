/*

MIT License (MIT)

Copyright (c) 2017 AT&T Intellectual Property. All other rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a 
copy of this software and associated documentation files (the "Software"), 
to deal in the Software without restriction, including without limitation 
the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the 
Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included 
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
THE SOFTWARE.

*/

package com.att.eg.common.platform.kubekleaner;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.att.ajsc.common.AbstractJerseyConfiguration;
import com.att.ajsc.common.messaging.LogRequestFilter;

import com.att.eg.common.platform.kubekleaner.service.rs.RestServiceImpl;

@Component
@ApplicationPath("/")
public class JerseyConfiguration extends AbstractJerseyConfiguration {
	private static final Logger log = Logger.getLogger(JerseyConfiguration.class.getName());
	
	@Autowired
 	public JerseyConfiguration(LogRequestFilter lrf,
    		@Value("${ajsc.jersey.loggingfilter.enabled:false}") boolean jerseyLoggingFilterEnabled, 
    		@Value("${ajsc.jersey.loggingfilter.printentity.enabled:false}") boolean jerseyLoggingFilterPrintEntityEnabled) {
		
		super(log, lrf, jerseyLoggingFilterEnabled, jerseyLoggingFilterPrintEntityEnabled);    	
        	register(RestServiceImpl.class);
    }
}
